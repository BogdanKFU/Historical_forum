package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.repository.CustomRepository;
import ru.kpfu.itis.group501.popov.repository.CustomStatement;
import ru.kpfu.itis.group501.popov.models.User;

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
        Map map = CustomRepository.do_sql(cs.select(User.class).orderBy("username"));
        Map<String, Object> root = new HashMap<>();
        if (map != null) {
            root.put("users", map.get("User"));
        }
        Helpers.render(request, response, "all_users.ftl", root);

    }
}
