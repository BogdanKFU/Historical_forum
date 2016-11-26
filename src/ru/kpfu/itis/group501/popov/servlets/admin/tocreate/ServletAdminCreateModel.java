package ru.kpfu.itis.group501.popov.servlets.admin.tocreate;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Model;
import ru.kpfu.itis.group501.popov.repository.Repository;
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

@WebServlet(name = "ServletAdminCreateModel")
public class ServletAdminCreateModel extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Repository repository = RepositorySingleton.getRepository();
        String model_name = request.getParameter("model");
        try {
            Class aClass = Class.forName("ru.kpfu.itis.group501.popov.models." + model_name);
            Constructor constructor = aClass.getConstructor();
            Model new_model = (Model) constructor.newInstance();
            Field [] fields = aClass.getDeclaredFields();
            String time = request.getParameter("send_time");
            for (Field f : fields) {
                if (!f.getName().equals("table_name") && !f.getName().equals("foreign_key") && !f.getName().equals("relations") && !f.getName().equals("id")) {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    String name = f.getName();
                    String type_name = f.getType().getSimpleName();
                    if (type_name.equals("Date")) {
                        new_model.set(name, Helpers.toDate(request.getParameter(name)));
                    }
                    else if (type_name.equals("boolean")) {
                        new_model.set(name, request.getParameter(name) != null);
                    }
                    else if (name.equals("password")) {
                        new_model.set("password", UserService.hash(request.getParameter("password")));
                    }
                    else if (type_name.equals("int")){
                        new_model.set(name, Integer.valueOf(request.getParameter(name)));
                    }
                    else if (type_name.equals("Time")){
                        new_model.set(name, Time.valueOf(request.getParameter(name)));
                    }
                    else {
                        new_model.set(name, request.getParameter(name));
                    }
                }
            }
            repository.add(new_model);
            response.sendRedirect("/admin/entities/edit?model=" + model_name + "&id=" + new_model.get("id"));
        } catch (ClassNotFoundException|NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            response.sendRedirect("/admin/entities");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        Repository repository = RepositorySingleton.getRepository();
        String string = request.getParameter("model");
        try {
            Class aClass = Class.forName("ru.kpfu.itis.group501.popov.models." + string);
            Field[] fields = aClass.getDeclaredFields();
            List<Field> field_list = new ArrayList<>();
            Map<String, Object> root = new HashMap<>();
            for (Field f : fields) {
                if (!f.getName().equals("table_name") && !f.getName().equals("foreign_key") && !f.getName().equals("relations")) {
                    field_list.add(f);
                }
            }
            root.put("model", string);
            root.put("fields", field_list);
            Constructor constructor = aClass.getConstructor();
            Model new_model = (Model) constructor.newInstance();
            Map<String, String> foreign_key = (Map<String, String>) new_model.get("foreign_key");
            Map<String, List> fk_map = new HashMap<>();
            if (foreign_key != null && foreign_key.size() != 0) {
                for(String s: foreign_key.keySet()) {
                    Class forName = Class.forName("ru.kpfu.itis.group501.popov.models." + foreign_key.get(s));
                    fk_map.put(s, repository.get(forName));
                }
                root.put("fk", fk_map);
            }
            Helpers.render(request, response, "admin_create_model.ftl", root);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            response.sendRedirect("/admin/entities");
        }
    }
}
