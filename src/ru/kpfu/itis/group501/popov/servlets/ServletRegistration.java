package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Внимание! Здесь есть форма! Нужен csrf_token!
// Внимание! Эта страница связана с паролями! HALT! Нужно хеширование перед сохранением
// Внимание! Присутствуют даты! Необходимо убедиться в реальности введенных данных!
@WebServlet(name = "servlets.ServletRegistration")
public class ServletRegistration extends HttpServlet {
    // Регистрация пользователя
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Заменить весь этот код на UserService.reg()
        // Получение параметров
        request.setCharacterEncoding("utf-8");
        String login = request.getParameter("login");
        if (UserService.reg(request, response)) {
            String error = "already_exist";
            response.sendRedirect("/auth/sign_up?error=" + error);
        }
        else {
            response.sendRedirect("/profile");
        }
    }

    // Отображение формы
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Helpers.render(request, response, "sign_up.ftl");
        // Отображает форму(обязательные: e-mail, login, pass, pass_2; необязательные: имя, фамилия, дата рождения
        // Предпроверка: введены все поля, e-mail введен правильно (регулярка), пароль повторен правильно
        // Все введено правильно - POST-запрос на /sign_up
        // Допущены ошибки - отображение ошибок (обновление страницы с новыми аргументами)
    }
}
