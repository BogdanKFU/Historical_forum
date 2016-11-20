package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.models.User;
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

@WebServlet(name = "ServletBan")
public class ServletBan extends HttpServlet {

    /*
    Блокирует пользователя. Т.е. меняет флаг blocked на true.
    Достается пользователь методом CustomRepository.getBy()
    Меняется флаг у пользователя.
    Делается update для пользователя.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String string = request.getParameter("id");
        Integer id = 0;
        try {
            id = Integer.valueOf(string);
        }
        catch (NumberFormatException ex) {
            response.sendRedirect("/profile");
        }
        List list = CustomRepository.getBy(User.class, "id", id);
        if (list != null && !list.isEmpty()) {
            User user = (User) list.get(0);
            Map<String, Object> root = new HashMap<>();
            user.set("blocked", true);
            CustomRepository.update(user);
            root.put("user", user);
            response.sendRedirect("/profile/?id=" + id);
        }
        else {
            response.sendRedirect("/profile");
        }
    }

    // редирект
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
