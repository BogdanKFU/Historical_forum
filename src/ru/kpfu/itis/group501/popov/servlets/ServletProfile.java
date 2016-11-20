package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServletProfile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        User user = (User) request.getSession().getAttribute("current_user");
        Map<String, Object> root = new HashMap<>();
        if (user == null) {
            root.put("user", "");
        }
        else {
            root.put("user", user);
        }
        Helpers.render(request, response, "profile.ftl", root);
    }
}
