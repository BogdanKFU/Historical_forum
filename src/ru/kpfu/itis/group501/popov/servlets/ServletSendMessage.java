package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Message;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.CustomRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ServletSendMessage")
public class ServletSendMessage extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String topic = request.getParameter("topic");
        String content = request.getParameter("content");
        User current_user = (User) request.getSession().getAttribute("current_user");
        Date date = new Date();
        java.sql.Date write_date = new java.sql.Date(date.getTime());
        Time write_time = new Time(write_date.getTime());
        write_time.toLocalTime();
        List list = CustomRepository.getBy(User.class, "username", username);
        User recipient;
        if (list.isEmpty()) {
            response.sendRedirect("/profile");
        }
        recipient = (User)list.get(0);
        Message new_message = new Message(content, (int)recipient.get("id"), (int)current_user.get("id"), topic, write_date, write_time);
        CustomRepository.add(new_message);
        response.sendRedirect("/messages/input");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        В строчке кому - AJAX запрос
         */
        response.setContentType("text/html;charset=utf-8");
        Helpers.render(request, response, "sending_message.ftl");
    }
}
