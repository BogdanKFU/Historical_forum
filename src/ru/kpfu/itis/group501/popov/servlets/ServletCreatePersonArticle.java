package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.PersonArticle;
import ru.kpfu.itis.group501.popov.models.Role;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ServletCreatePersonArticle")
public class ServletCreatePersonArticle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Repository repository = RepositorySingleton.getRepository();
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        User current_user = (User) request.getSession().getAttribute("current_user");
        Date date = new Date();
        java.sql.Date write_date = new java.sql.Date(date.getTime());
        Time write_time = new Time(write_date.getTime());
        write_time.toLocalTime();
        String string = request.getParameter("birth_date");
        String string2 = request.getParameter("dead_date");
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        date = null;
        Date date2 = null;
        try {
            date = format.parse(string);
            date2 = format.parse(string2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date birth_date;
        java.sql.Date dead_date;
        if (date != null && date2 != null) {
            birth_date = new java.sql.Date(date.getTime());
            dead_date = new java.sql.Date(date2.getTime());
            PersonArticle new_article = new PersonArticle(birth_date, content, (int) current_user.get("id"), dead_date, name, write_date, write_time);
            repository.add(new_article);
            response.sendRedirect("/articles/person?id="+new_article.get("id"));        }
        else {
            response.sendRedirect("/profile");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, Object> root = new HashMap<>();
        User current_user = (User) request.getSession().getAttribute("current_user");
        root.put("current_user", current_user);
        Role role = (Role) request.getSession().getAttribute("role");
        root.put("role", role);
        Helpers.render(request, response, "create_person_article.ftl", root);
    }
}
