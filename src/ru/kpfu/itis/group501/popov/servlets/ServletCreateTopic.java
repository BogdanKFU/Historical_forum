package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Role;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.services.ModelCreatorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ServletCreateTopic")
public class ServletCreateTopic extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        if (ModelCreatorService.createTopic(request)) {
            response.sendRedirect("/forum/section?id=" + request.getParameter("id"));
        }
        else {
            response.sendRedirect("/forum/");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String string = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            response.sendRedirect("/forum");
        }
        Map<String, Object> root = new HashMap<>();
        root.put("id_section", id);
        Role role = (Role) request.getSession().getAttribute("role");
        root.put("role", role);
        User current_user = (User) request.getSession().getAttribute("current_user");
        root.put("current_user", current_user);
        response.setContentType("text/html;charset=utf-8");
        Helpers.render(request, response, "create_topic.ftl", root);
    }
}
