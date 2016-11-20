package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "servlets.ServletEditProfile")
public class ServletEditProfile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        edit() проверяет все ошибки, пытается добавить в БД.
        При успешном добавлении в БД edit() вернет true,
        в противном случае - false.
         */
        UserService.edit(request);
        /*
        Если edit() вернет true, то редирект на профиль с сообщением "Профиль отредактирован",
        иначе - редирект на страницу редактирования профиля с сообщением об ошибке.
         */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        // отображение формы редактирования страницы + вывод информации из БД
        // достаем models.User из сессии
        Object user = request.getSession().getAttribute("current_user");
        Map<String, Object> root = new HashMap<>();
        root.put("root", user);
        Helpers.render(request, response, "edit.ftl", root);
    }
}