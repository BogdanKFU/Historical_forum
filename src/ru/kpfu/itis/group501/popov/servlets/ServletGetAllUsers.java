package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Role;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ServletGetAllUsers")
public class ServletGetAllUsers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        CustomStatement cs = new CustomStatement();
        Repository repository = RepositorySingleton.getRepository();
        Map map = repository.do_select(cs.select(User.class).orderBy("User.username"));
        Map<String, Object> root = new HashMap<>();
        if (map != null) {
            root.put("users", map.get("User"));
        }
        User current_user = (User) request.getSession().getAttribute("current_user");
        root.put("current_user", current_user);
        Role role = (Role) request.getSession().getAttribute("role");
        root.put("role", role);
        Helpers.render(request, response, "all_users.ftl", root);

    }
}
