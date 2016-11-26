package ru.kpfu.itis.group501.popov.repository.custom;

import ru.kpfu.itis.group501.popov.models.Model;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.singletons.ConnectionSingleton;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomRepository implements Repository {

    private static boolean initialized = false;
    // В conn хранится Connection, который получается в результате init()
    private static Connection conn = null;
    // В map хранится пара ключ-значение. Ключ - тип данных. Значение - метод PreparedStatement.
    private static Map<String, Method> map = new HashMap<>();
    // В RSGetMethods хранится пара ключ-значение. Ключ - тип данных. Значение - метод ResultSet.
    private static Map<String, Method> RSGetMethods = new HashMap<>();

    static {
        init();
    }
    /*
    Создает UPDATE запрос без WHERE и т.п.
     */
    private static String create_update(Model model) {
        Field[] fields = model.getClass().getDeclaredFields();
        String table_name = (String) model.get("table_name");
        String update = "UPDATE " + table_name + " SET ";
        for (Field field: fields) {
            String name = field.getName();
            if (!name.equals("table_name") && !name.equals("id") && !name.equals("relations") && !name.equals("foreign_key")) {
                update = update + name + "=?, ";
            }
        }
        update = update.substring(0, update.length() - 2);
        return update;
    }

    private static void init() {
        try {
            conn = ConnectionSingleton.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*
            Проход по всем методам.
            Взять тип второго элемента, если размер массива не равен 2 - пропускаем.
            Первый элемент - целочисленный. Записываем второй элемент как ключ.
            Записываем сам метод в Map как значение.
        */
        Method [] methods = PreparedStatement.class.getMethods();
        Pattern pattern_setter = Pattern.compile("setN[A-Za-z0-9]*");
        Pattern pattern_getter = Pattern.compile("getN[A-Za-z0-9]*");
        Pattern except_find = Pattern.compile("find[A-Za-z0-9]*");
        for (Method method: methods) {
            if (method.getParameterTypes().length == 2) {
                String method_name = method.getName();
                Matcher matcher = pattern_setter.matcher(method_name);
                if (method.getParameterTypes()[0].equals(int.class) && !matcher.matches()) {
                    map.put(method.getParameterTypes()[1].getName(), method);
                }
            }
        }
        Method [] methods2 = ResultSet.class.getMethods();
        for (Method method: methods2) {
            if (method.getParameterTypes().length == 1) {
                String method_name = method.getName();
                Matcher matcher = pattern_getter.matcher(method_name);
                Matcher matcher1 = except_find.matcher(method_name);
                if(method.getParameterTypes()[0].equals(String.class) && !matcher.matches() && !matcher1.matches()) {
                    RSGetMethods.put(method.getReturnType().getName(), method);
                }
            }
        }
        try {
            map.put(Integer.class.getName(), PreparedStatement.class.getMethod("setInt", int.class, int.class));
            map.put(Byte.class.getName(), PreparedStatement.class.getMethod("setByte", int.class, byte.class));
            map.put(Short.class.getName(), PreparedStatement.class.getMethod("setShort", int.class, short.class));
            map.put(Long.class.getName(), PreparedStatement.class.getMethod("setLong", int.class, long.class));
            map.put(Float.class.getName(), PreparedStatement.class.getMethod("setFloat", int.class, float.class));
            map.put(Double.class.getName(), PreparedStatement.class.getMethod("setDouble", int.class, double.class));
            map.put(Boolean.class.getName(), PreparedStatement.class.getMethod("setBoolean", int.class, boolean.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        initialized = true;
    }

    /*
    Создает INSERT запрос
      */
    private static String create_insert(Model model) {
        Field[] fields = model.getClass().getDeclaredFields();
        int i = 0;
        String insert = "INSERT INTO " + model.get("table_name") + "(";
        for (Field field: fields) {
            String name = field.getName();
            // field не static
            if (!name.equals("table_name") && !name.equals("id") && !name.equals("relations") && !name.equals("foreign_key")) {
                insert = insert + name + ", ";
                i += 1;
            }
        }
        insert = insert.substring(0, insert.length() - 2);
        insert = insert + ") values(";
        for (int j = 0; j <= i; j++) {
            insert = insert + "?, ";
        }
        insert = insert.substring(0, insert.length() - 5);
        try {
            if (model.getClass().getDeclaredField("id") != null) {
                insert = insert + ") RETURNING id;";
            }
        } catch (NoSuchFieldException e) {
            insert = insert + ");";
        }
        return insert;
    }

    /*
    Создает DELETE запрос без WHERE и т.п.
     */
    private static String create_delete(Class model) {
        try {
            Field field = model.getDeclaredField("table_name");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return "DELETE FROM " + field.get(model) + " WHERE id=?";
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    Создает SELECT запрос без WHERE и т.п.
     */
    private static String create_select(Class model) {
        String select = "SELECT * FROM ";
        try {
            Field field = model.getDeclaredField("table_name");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                select = select + field.get(model);
                return select;
            } catch (IllegalAccessException e) {
                return null;
            }
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /*
    Вставка новой записи в БД, не работает с custom types, т.е. логика этого метода
    не предполагает использование метода setObject(int i, Object arg) у statement
     */
    public void add(Model model) {
        try {
            String insert = create_insert(model);
            PreparedStatement statement = create_request(insert, model);
            ResultSet rs = null;
            if (statement != null) {
                try {
                    if (model.getClass().getDeclaredField("id") != null) {
                        rs = statement.executeQuery();
                    }
                } catch (NoSuchFieldException e) {
                    statement.executeUpdate();
                }

            }
            int i = 0;
            if (rs != null) {
                while (rs.next()) {
                    i = rs.getInt("id");
                }
            }
            model.set("id", i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Обновляет информацию в БД по id.
    public void update(Model model) {
        try {
            String update = create_update(model) + " WHERE id=?";
            PreparedStatement statement = create_request(update, model);
            if (statement != null) {
                statement.setInt(model.getClass().getDeclaredFields().length - 3, (int) model.get("id"));
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Создает PreparedStatement для данного запроса и модели
    private static PreparedStatement create_request(String sql, Model model) {
        try {
            Field[] fields = model.getClass().getDeclaredFields();
            PreparedStatement statement;
            statement = conn.prepareStatement(sql);
            int i = 0;
            // Должна храниться информация о том, какого типа нужно присваивание. Т.е. setТИП(). Хранится ключ - тип данных, значение - метод.
            // Достается тип атрибута и его значение. По его типу берется из Map его значение. Выполняется метод с нужными параметрами.
            for(Field field: fields) {
                String name = field.getName();
                if (!name.equals("table_name") && !name.equals("id") && !name.equals("foreign_key") && !name.equals("relations")) {
                    Method method = map.get(field.getType().getName());
                    Object[] method_args;
                    try {
                        i = i + 1;
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        method_args = new Object[]{i, field.get(model)};
                        if (!method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(statement, method_args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    Возвращает List<Object> по одному полю. Используется WHERE
     */
    public List getBy(Class model, String field_name, Object value) {
        String select = create_select(model) + " WHERE " + field_name + "=?";
            try {
                PreparedStatement statement;
                statement = conn.prepareStatement(select);
                try {
                    Method method = map.get(value.getClass().getName());
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    method.invoke(statement, 1, value);
                    ResultSet rs = statement.executeQuery();
                    return toList(rs, model);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
            } catch (SQLException e) {
                return new ArrayList();
            }
        return new ArrayList();
    }

    /*
    Возвращает все записи как List<Object>
     */
    public List get(Class model) {
        String select = create_select(model);
        try {
            PreparedStatement statement;
            statement = conn.prepareStatement(select);
            ResultSet rs = statement.executeQuery();
            return toList(rs, model);
        } catch (SQLException e) {
            return new ArrayList();
        }
    }

    /*
    Возвращает список объектов, соответствующих данному ResultSet
     */
    private static List<Object> toList(ResultSet rs, Class<Model> model) {;
        List<Object> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Model new_model = create_model(rs, model);
                list.add(new_model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Map<String, List<Object>> toManyList(ResultSet rs, Map<String, Class> relation_map) {
        Map<String, List<Object>> map = new HashMap<>();
        for(String c: relation_map.keySet()) {
            String [] args = c.split("\\.");
            map.put(args[0], new ArrayList<>());
        }
        try {
            while (rs.next()) {
                for(String c: relation_map.keySet()) {
                    String [] args = c.split("\\.");
                    Model new_model = create_model(rs, Class.forName("ru.kpfu.itis.group501.popov.models." + args[0]));
                    Model sub_model = create_model(rs, relation_map.get(c));
                    Method method = new_model.getClass().getMethod("get", String.class);
                    Map relations = (Map) method.invoke(new_model, "relations");
                    relations.put(args[1], sub_model);
                    map.get(args[0]).add(new_model);
                }
            }
            return map;
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Model create_model(ResultSet rs, Class model) {
        try {
            Constructor constructor = model.getConstructor();
            Model new_model = (Model) constructor.newInstance();
            Field [] fields = model.getDeclaredFields();
            for (Field f : fields) {
                if (!f.getName().equals("table_name") && !f.getName().equals("foreign_key") && !f.getName().equals("relations")) {
                    f.getType();
                    Method method = RSGetMethods.get(f.getType().getName());
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    Object table_field = method.invoke(rs, f.getName());
                    new_model.set(f.getName(), table_field);
                }
            }
            return new_model;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Class model, int id) {
        String delete = create_delete(model);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(delete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<Object>> do_sql(CustomStatement statement) {
        try {
            String sql = statement.getSql();
            PreparedStatement ps;
            ps = conn.prepareStatement(sql);
            Map<String, Object> values = statement.getValues();
            Class model = statement.getModel();
            int i = 1;
            if (values != null) {
                for (String s : values.keySet()) {
                    Field field = model.getDeclaredField(s);
                    Class classes = field.getType();
                    Method method = map.get(classes.getName());
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    Object[] method_args = new Object[]{i, values.get(s)};
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    method.invoke(ps, method_args);
                    i++;
                }
            }
            ResultSet rs = ps.executeQuery();
            Map joinedBy = statement.getJoinedBy();
            Map<String, List<Object>> map;
            if (joinedBy.size() != 0) {
                map = toManyList(rs, joinedBy);
            }
            else {
                map = new HashMap<>();
                List list = toList(rs, model);
                map.put(model.getSimpleName(), list);
            }
            return map;
        } catch (SQLException | InvocationTargetException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void remove_cookie(int user_id) {
        try {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM cookies WHERE id_user=?");
            statement.setInt(1, user_id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}