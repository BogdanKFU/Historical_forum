package ru.kpfu.itis.group501.popov.servlets.admin.toget;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Role;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletAdminGetModels")
public class ServletAdminGetModels extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String model = request.getParameter("model");
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> root = new HashMap<>();
        Repository repository = RepositorySingleton.getRepository();
        User current_user = (User) request.getSession().getAttribute("current_user");
        root.put("current_user", current_user);
        Role role = (Role) request.getSession().getAttribute("role");
        root.put("role", role);
        try {
            Class aClass = Class.forName("ru.kpfu.itis.group501.popov.models." + model);
            CustomStatement cs = new CustomStatement();
            Map map = repository.do_select(cs.select(aClass));
            Field [] fields = aClass.getDeclaredFields();
            List<Field> field_list = new ArrayList<>();
            for(Field f: fields) {
                if (!f.getName().equals("table_name") && !f.getName().equals("foreign_key") && !f.getName().equals("relations")) {
                    field_list.add(f);
                }
            }
            root.put("model", model);
            root.put("fields", field_list);
            if (map != null) {
                root.put("models", map.get(aClass.getSimpleName()));
            }
            Helpers.render(request, response, "admin_get_models.ftl", root);
        } catch (ClassNotFoundException e) {
            root.put("error", "Error: model not found!");
            Helpers.render(request, response, "admin_get_models.ftl", root);
        }
    }
}
