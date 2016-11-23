package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.EventArticle;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.CustomRepository;

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

@WebServlet(name = "ServletCreateEventArticle")
public class ServletCreateEventArticle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        User current_user = (User) request.getSession().getAttribute("current_user");
        Date date = new Date();
        java.sql.Date write_date = new java.sql.Date(date.getTime());
        Time write_time = new Time(write_date.getTime());
        write_time.toLocalTime();
        String string = request.getParameter("begin_date");
        String string2 = request.getParameter("end_date");
        java.sql.Date begin_date = Helpers.toDate(string);
        java.sql.Date end_date = Helpers.toDate(string2);
        if (begin_date != null && end_date != null) {
            EventArticle new_article = new EventArticle(begin_date, content, (int)current_user.get("id"), end_date, name, write_date, write_time);
            CustomRepository.add(new_article);
            response.sendRedirect("/articles/event?id="+new_article.get("id"));
        }
        else {
            response.sendRedirect("/profile");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Helpers.render(request, response, "create_event_article.ftl");
    }
}
