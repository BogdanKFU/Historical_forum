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
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletForum")
public class ServletForum extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, List<Object>> map = ModelSearchService.searchSection(request);
        Map<String, Object> root = new HashMap<>();
        if (map != null) {
            List list = map.get("Section");
            root.put("sections", list);
        }
        Role role = (Role) request.getSession().getAttribute("role");
        root.put("role", role);
        User current_user = (User) request.getSession().getAttribute("current_user");
        root.put("current_user", current_user);
        Helpers.render(request, response, "forum.ftl", root);
    }
}
