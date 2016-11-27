package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.custom.CustomRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlets.ServletLogout")
public class ServletLogout extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("current_user");
        request.getSession().removeAttribute("current_user");
        request.getSession().removeAttribute("role");
        CustomRepository.remove_cookie((int)user.get("id"));
        Cookie cookie = new Cookie("current_user", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect("/auth/login");
    }
}
