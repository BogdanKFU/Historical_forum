package ru.kpfu.itis.group501.popov.servlets.admin.toget;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Role;
import ru.kpfu.itis.group501.popov.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ServletAdminGetAllModels")
public class ServletAdminGetAllModels extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        User current_user = (User) request.getSession().getAttribute("current_user");
        Map<String, Object> root = new HashMap<>();
        root.put("current_user", current_user);
        Role role = (Role) request.getSession().getAttribute("role");
        root.put("role", role);
        Helpers.render(request, response, "admin_get_all_models.ftl", root);
    }
}
