package ru.kpfu.itis.group501.popov.repository.custom;

import ru.kpfu.itis.group501.popov.models.Model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/*
Декоратор для PreparedStatement
Usefull only for selects
Проблема:
Не распознает поля с одинаковыми именами. Например, id.
*/
public class CustomStatement implements ru.kpfu.itis.group501.popov.repository.Statement {
    private String sql;
    private int amount = 0;
    private Map<String, Object> values = null;
    private Class model;
    private Map<String, Class<Model>> joinedBy = new HashMap<>();

    public CustomStatement() {
        this.sql = "";
    }

    /*
    Прибавляет к тексту из PreparedStatement "AND field_name=?"
    и выполняет setSomething((Something) value)
     */
    public CustomStatement and(String field_name, Object value) {
        if (hasValues()) {
            String sql = getSql();
            String [] args = field_name.split("\\.");
            try {
                Class model = Class.forName("ru.kpfu.itis.group501.popov.models." + args[0]);
                Field field = model.getDeclaredField("table_name");
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                sql = sql + " AND " + field.get(model) + "." + args[1] + "=?";
                CustomStatement new_statement = new CustomStatement(sql, getAmount(), getValues(), getModel(), getJoinedBy());
                new_statement.increase(1);
                new_statement.add(field_name, value);
                return new_statement;
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                return null;
            }
        }
        else {
            throw new NullPointerException("Sorry, but this statement hasn't values. So, you can't use AND");
        }
    }

    public CustomStatement and() {
        if (hasValues()) {
            return new CustomStatement(sql + " AND ", getModel(), getValues(), getAmount());
        }
        else {
            throw new NullPointerException("Sorry, but this statement hasn't values. So, you can't use AND");
        }
    }

    public CustomStatement by(String field_name, Object value) {
        try {
            if (!hasValues()) {
                String sql = getSql();
                String [] args = field_name.split("\\.");
                Class model = Class.forName("ru.kpfu.itis.group501.popov.models." + args[0]);
                Field field = model.getDeclaredField("table_name");
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                sql = sql + " WHERE " + field.get(model) + "." + args[1] + "=?";
                CustomStatement new_statement = new CustomStatement(sql, getAmount(), getValues(), getModel(), getJoinedBy());
                new_statement.increase(1);
                new_statement.add(field_name, value);
                return new_statement;
            }
            else {
                throw new NullPointerException("Sorry, but this statement has values. So, you can't use WHERE");
            }
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            return null;
        }
    }

    public CustomStatement ge(String field_name, Object value) {
        try {
            String sql = getSql();
            String [] args = field_name.split("\\.");
            Class model = Class.forName("ru.kpfu.itis.group501.popov.models." + args[0]);
            Field field = model.getDeclaredField("table_name");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            sql = sql + field.get(model) + "." + args[1] + ">=?";
            CustomStatement new_statement = new CustomStatement(sql, getAmount(), getValues(), getModel(), getJoinedBy());
            new_statement.increase(1);
            new_statement.add(field_name, value);
            return new_statement;
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            return null;
        }
    }

    public CustomStatement le(String field_name, Object value) {
        try {
            String sql = getSql();
            String [] args = field_name.split("\\.");
            Class model = Class.forName("ru.kpfu.itis.group501.popov.models." + args[0]);
            Field field = model.getDeclaredField("table_name");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            sql = sql + field.get(model) + "." + args[1] + "<=?";
            CustomStatement new_statement = new CustomStatement(sql, getAmount(), getValues(), getModel(), getJoinedBy());
            new_statement.increase(1);
            new_statement.add(field_name, value);
            return new_statement;
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            return null;
        }
    }

    public CustomStatement like(String field_name, Object value) {
        try {
            String sql = getSql();
            String [] args = field_name.split("\\.");
            Class model = Class.forName("ru.kpfu.itis.group501.popov.models." + args[0]);
            Field field = model.getDeclaredField("table_name");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            CustomStatement new_statement;
            if (hasValues()) {
                sql = sql + " AND " + field.get(model) + "." + args[1] + " LIKE ?";
                new_statement = new CustomStatement(sql, getAmount(), getValues(), getModel(), getJoinedBy());
            }
            else {
                sql = sql + " WHERE " + field.get(model) + "." + args[1] + " LIKE ?";
                new_statement = new CustomStatement(sql, getAmount(), new LinkedHashMap<>(), getModel(), getJoinedBy());
            }
            new_statement.add(field_name, "%" + value + "%");
            new_statement.increase(1);
            return new_statement;
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    Прибавляет к тексту из PreparedStatement "OR field_name=?"
    и выполняет setSomething((Something) value)
     */
    public CustomStatement or(String field_name, Object value) {
        try {
            if (hasValues()) {
                String sql = getSql();
                String [] args = field_name.split("\\.");
                Class model = Class.forName("ru.kpfu.itis.group501.popov.models." + args[0]);
                Field field = model.getDeclaredField("table_name");
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                sql = sql + " OR " + field.get(model) + "." + args[1] + "=?";
                CustomStatement new_statement = new CustomStatement(sql, getAmount(), getValues(), getModel(), getJoinedBy());
                new_statement.increase(1);
                new_statement.add(field_name, value);
                return new_statement;
            } else {
                throw new NullPointerException("Sorry, but this statement hasn't values. So, you can't use OR");
            }
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            return null;
        }
    }

    /*
    Прибавляет к тексту из PreparedStatement "ORDER BY field_name"
     */
    public CustomStatement orderBy(String field_name) {
        try {
            String sql = getSql();
            String [] args = field_name.split("\\.");
            Class model = Class.forName("ru.kpfu.itis.group501.popov.models." + args[0]);
            Field field = model.getDeclaredField("table_name");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            sql = sql + " ORDER BY " + field.get(model) + "." + args[1];
            return new CustomStatement(sql, getAmount(), getValues(), getModel(), getJoinedBy());
        } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CustomStatement select(Class model) {
        String select = "SELECT * FROM ";
        try {
            Field field = model.getDeclaredField("table_name");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                select = select + field.get(model);
                return new CustomStatement(select, model);
            } catch (IllegalAccessException e) {
                return null;
            }
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public CustomStatement selectBy(Class model, String field_name, Object value) {
        String select = "SELECT * FROM ";
        try {
            Field field = model.getDeclaredField("table_name");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            try {
                select = select + field.get(model) + " WHERE " + field.get(model) + "." + field_name + "=?";
                Map<String, Object> map = new LinkedHashMap<>();
                map.put(model.getSimpleName() + "." + field_name, value);
                CustomStatement new_statement = new CustomStatement(select, model, map, 0);
                new_statement.increase(1);
                return new_statement;
            } catch (IllegalAccessException e) {
                return null;
            }
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private void increase(int i) {
        this.amount = this.amount + i;
    }

    private CustomStatement(String sql, Class model) {
        this.sql = sql;
        this.model = model;
    }

    private CustomStatement(String sql, Class model, Map<String, Object> values, int i) {
        this.sql = sql;
        this.values = values;
        this.model = model;
        this.amount = i;
    }

    private CustomStatement(String sql, int amount, Map<String, Object> values, Class model, Map<String, Class<Model>> joinedBy) {
        this.sql = sql;
        this.amount = amount;
        this.values = values;
        this.model = model;
        this.joinedBy = joinedBy;
    }

    public String getSql() {
        return sql;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    private void add(String field_name, Object value) {
        this.values.put(field_name, value);
    }

    private boolean hasValues() {
        return values != null;
    }

    public Class getModel() {
        return model;
    }

    public int getAmount() {
        return amount;
    }

    // Для запроса без ограничений
    public CustomStatement join(Class model) {
        /*
        Проверка на отсутствие ограничений
        Если нет ограничений:
        Достаем у model поле foreign_key
        Проверка на наличие relation у model с this.model
        Проверка на наличие relation у this.model
        Если первое верно, то присоединяем к sql строку
        " JOIN " + достаем название таблицы у model +
        " ON " + достаем название поля из foreign_key у model
        (Это будет поле для this.model) + "=t.id", где t - название таблицы для model
        Если верно второе, то тоже самое, только все поля достаются у this.model
        Кроме того, все приджойненные сущности сохраняются в List joined
         */
        if (!hasValues()) {
            try {
                Method method = model.getMethod("getForeign_key");
                Map map = (Map) method.invoke(model);
                Method method1 = model.getMethod("get", String.class);
                if (!method1.isAccessible()) {
                    method1.setAccessible(true);
                }
                String table_name = (String) method1.invoke(model.newInstance(), "table_name");
                String this_table_name = (String) this.model.getMethod("get", String.class).invoke(this.model.newInstance(), "table_name");
                String sql = this.getSql();
                for(Object o: map.keySet()) {
                    if(map.get(o).equals(this.model.getSimpleName())) {
                        sql = sql + " JOIN " + table_name + " ON " + this_table_name + ".id=" + table_name + "." + o;
                        Class model1 = this.model;
                        this.model = model;
                        model = model1;
                        joinedBy.put(this.model.getSimpleName() + "." + o, model);
                    }
                }
                Method method2 = this.model.getMethod("getForeign_key");
                Map this_map = (Map) method2.invoke(this.model);
                for(Object o: this_map.keySet()) {
                    if (joinedBy.containsKey(this.model.getSimpleName() + "." + o) || model.equals(this.model)) {
                        break;
                    }
                    if(this_map.get(o).equals(model.getSimpleName())) {
                        sql = sql + " JOIN " + table_name + " ON " + table_name + ".id=" + this_table_name + "." + o;
                        joinedBy.put(this.model.getSimpleName() + "." + o, model);
                    }
                }
                return new CustomStatement(sql, 0, this.getValues(), this.getModel(), this.joinedBy);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // Для запроса с ограничениями, вопрос в том, как отличить к какой именно таблице относится field_name в случае одинаковых полей
    // Предлагается в field_name указывать к какой сущности должно относится поле затем заменять название сущности на название таблицы
    public CustomStatement joinBy(Class model, String field_name, Object value) {
        CustomStatement cs = this.join(model);
        String [] args = field_name.split("\\.");
        Class new_model;
        Field field;
        try {
            new_model = Class.forName("ru.kpfu.itis.group501.popov.models." + args[0]);
            field = new_model.getDeclaredField("table_name");
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            String sql = cs.getSql() + " WHERE " + field.get(new_model) + "." + args[1] + "=?";
            Map<String, Object> values = cs.getValues();
            if (values == null) {
                values = new LinkedHashMap<>();
            }
            values.put(field_name, value);
        return new CustomStatement(sql, this.getAmount() + 1, values, cs.getModel(), cs.joinedBy);
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            return null;
        }
    }

    public Map<String, Class<Model>> getJoinedBy() {
        return joinedBy;
    }

    public CustomStatement rename_fields() {
        try {
            Class model = this.getModel();
            Map<String, Class<Model>> joinedBy = this.getJoinedBy();
            String rename = "";
            Field [] fields_model = model.getDeclaredFields();
            Field table = model.getDeclaredField("table_name");
            if (!table.isAccessible()) {
                table.setAccessible(true);
            }
            String table_name = (String) table.get(model);
            for(Field field: fields_model) {
                String field_name = field.getName();
                if (!field_name.equals("table_name") && !field_name.equals("relations") && !field_name.equals("foreign_key")) {
                    rename = rename + table_name + "." + field_name + " AS \"" + table_name + "." + field_name + "\", ";
                }
            }
            if (joinedBy.size() != 0) {
                for (String key: joinedBy.keySet()) {
                    Class joined_model = joinedBy.get(key);
                    Field [] fields_join = joined_model.getDeclaredFields();
                    Field joined_table = joined_model.getDeclaredField("table_name");
                    if (!joined_table.isAccessible()) {
                        joined_table.setAccessible(true);
                    }
                    String joined_table_name = (String) joined_table.get(joined_model);
                    for(Field field: fields_join) {
                        String field_name = field.getName();
                        if (!field_name.equals("table_name") && !field_name.equals("relations") && !field_name.equals("foreign_key")) {
                            rename = rename + joined_table_name + "." + field_name + " AS \"" + joined_table_name + "." + field_name + "\", ";
                        }
                    }
                }
            }
            rename = rename.substring(0, rename.length() - 2);
            String sql = this.getSql();
            sql = sql.replaceFirst("\\*", rename);
            return new CustomStatement(sql, this.getAmount(), this.getValues(), this.getModel(), joinedBy);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
