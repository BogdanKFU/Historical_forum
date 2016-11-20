package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Topic;
import ru.kpfu.itis.group501.popov.repository.CustomRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletTopics")
public class ServletSection extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String string = request.getParameter("id");
        Integer id = 0;
        try {
            id = Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            response.sendRedirect("/forum");
        }
        List list = CustomRepository.getBy(Topic.class, "section", id);
        Map<String, Object> root = new HashMap<>();
        root.put("id", id);
        root.put("topics", list);
        Helpers.render(request, response, "section.ftl", root);
    }
}
