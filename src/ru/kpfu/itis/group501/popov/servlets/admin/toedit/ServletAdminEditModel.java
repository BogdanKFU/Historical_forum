package ru.kpfu.itis.group501.popov.servlets.admin.toedit;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Model;
import ru.kpfu.itis.group501.popov.models.Role;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;
import ru.kpfu.itis.group501.popov.services.UserService;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletAdminEditModel")
public class ServletAdminEditModel extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String model_name = request.getParameter("model");
        String str = request.getParameter("id");
        Repository repository = RepositorySingleton.getRepository();
        Integer id = Integer.valueOf(str);
        try {
            Class aClass = Class.forName("ru.kpfu.itis.group501.popov.models." + model_name);
            Constructor constructor = aClass.getConstructor();
            Model new_model = (Model) constructor.newInstance();
            Field [] fields = aClass.getDeclaredFields();
            for (Field f : fields) {
                if (!f.getName().equals("table_name") && !f.getName().equals("foreign_key") && !f.getName().equals("relations") && !f.getName().equals("id")) {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    if (f.getType().getSimpleName().equals("Date")) {
                        new_model.set(f.getName(), Helpers.toDate(request.getParameter(f.getName())));
                    }
                    else if (f.getType().getSimpleName().equals("boolean")) {
                        new_model.set(f.getName(), request.getParameter(f.getName()) != null);
                    }
                    else if (f.getName().equals("password")) {
                        if (request.getParameter("password") != null && !request.getParameter("password").equals("")) {
                            new_model.set("password", UserService.hash(request.getParameter("password")));
                        }
                        else {
                            CustomStatement cs = new CustomStatement();
                            Map map = repository.do_select(cs.selectBy(User.class, "id", id));
                            User user = (User)((List) map.get("User")).get(0);
                            new_model.set("password", user.get("password"));
                        }
                    }
                    else if (f.getType().getSimpleName().equals("int")){
                        new_model.set(f.getName(), Integer.valueOf(request.getParameter(f.getName())));
                    }
                    else if (f.getType().getSimpleName().equals("Time")){
                        new_model.set(f.getName(), Time.valueOf(request.getParameter(f.getName())));
                    }
                    else {
                        new_model.set(f.getName(), request.getParameter(f.getName()));
                    }
                }
            }
            new_model.set("id", id);
            repository.update(new_model);
            response.sendRedirect("/admin/entities/edit?model=" + model_name + "&id=" + id);
        } catch (ClassNotFoundException|NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            response.sendRedirect("/admin/entities");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        Repository repository = RepositorySingleton.getRepository();
        String model = request.getParameter("model");
        try {
            Class aClass = Class.forName("ru.kpfu.itis.group501.popov.models." + model);
            String str = request.getParameter("id");
            Integer id = Integer.valueOf(str);
            CustomStatement cs = new CustomStatement();
            Map map = repository.do_select(cs.selectBy(aClass, "id", id));
            Object o = ((List)map.get(aClass.getSimpleName())).get(0);
            Map<String, Object> root = new HashMap<>();
            root.put("model", model);
            Field[] fields = aClass.getDeclaredFields();
            List<Field> field_list = new ArrayList<>();
            for(Field f: fields) {
                if (!f.getName().equals("table_name") && !f.getName().equals("foreign_key") && !f.getName().equals("relations")) {
                    field_list.add(f);
                }
            }
            Model new_model = (Model) o;
            Map<String, String> foreign_key = (Map<String, String>) new_model.get("foreign_key");
            Map<String, List> fk_map = new HashMap<>();
            if (foreign_key != null && foreign_key.size() != 0) {
                for(String s: foreign_key.keySet()) {
                    Class forName = Class.forName("ru.kpfu.itis.group501.popov.models." + foreign_key.get(s));
                    CustomStatement cs1 = new CustomStatement();
                    Map map1 = repository.do_select(cs.select(forName));
                    fk_map.put(s, (List) map1.get(forName.getSimpleName()));
                }
                root.put("fk", fk_map);
            }
            root.put("object", o);
            root.put("fields", field_list);
            User current_user = (User) request.getSession().getAttribute("current_user");
            root.put("current_user", current_user);
            Role role = (Role) request.getSession().getAttribute("role");
            root.put("role", role);
            Helpers.render(request, response, "admin_edit_model.ftl", root);
        } catch (ClassNotFoundException e) {
            response.sendRedirect("/admin/entities/?model=" + model);
        }
    }
}
