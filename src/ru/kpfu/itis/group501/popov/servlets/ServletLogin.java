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
/*
Дешифровка пароля
Проверить на наличие флага "Запомнить меня" для создания куки
Создать интерфейсы для каждого класса.
Пояснение: Реализация может меняться как угодно. Например, может измениться база данных.
Но суть остается одна и та же. Т.е. add() у models.Model означает то же самое. Но можно использовать MySQL, либо PostgreSQL.
add() служит для применения изменений в БД.
init() служит для вставки в БД новой записи.
update() служит для обновления записи в БД.

services служат для инкапсуляции бизнес-логики.
В servlets не должно быть обращений к БД.
 */
@WebServlet(name = "servlets.ServletLogin")
public class ServletLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("username");
        response.setContentType("text/html;charset=utf-8");
        // аутентификация
        if (UserService.authenticate(request, response)) {
            response.sendRedirect("/profile");
        }
        else {
            response.sendRedirect("/auth/login?error=not_found&login_user=" + login);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> root = new HashMap<>();
        response.setContentType("text/html;charset=utf-8");
        String login_user = request.getParameter("login_user");
        if (login_user != null) {
            root.put("login_user", login_user);
        }
        else {
            root.put("login_user", "");
        }
        String error = request.getParameter("error");
        if (error != null) {
            root.put("error", "Логин или пароль неправильный");
        }
        else {
            root.put("error", "");
        }
        Helpers.render(request, response, "login.ftl", root);
    }
}