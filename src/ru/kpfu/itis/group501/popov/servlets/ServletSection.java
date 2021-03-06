package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Role;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.services.ModelSearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ServletTopics")
public class ServletSection extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String string = request.getParameter("id");
        String name = request.getParameter("name");
        Integer id = 0;
        try {
            id = Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            response.sendRedirect("/forum");
        }
        Map map = ModelSearchService.searchTopic(request);
        Map<String, Object> root = new HashMap<>();
        root.put("id", id);
        root.put("topics", map.get("Topic"));
        Role role = (Role) request.getSession().getAttribute("role");
        root.put("role", role);
        User current_user = (User) request.getSession().getAttribute("current_user");
        root.put("current_user", current_user);
        Helpers.render(request, response, "section.ftl", root);
    }
}
