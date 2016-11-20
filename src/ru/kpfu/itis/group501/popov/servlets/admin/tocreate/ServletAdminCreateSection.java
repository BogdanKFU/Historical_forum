package ru.kpfu.itis.group501.popov.servlets.admin.tocreate;

import ru.kpfu.itis.group501.popov.models.Section;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.CustomRepository;
import ru.kpfu.itis.group501.popov.helpers.Helpers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
Необходимо изменить логику!
Админ может менять создателя раздела!
Следовательно, нам нужен SELECT запрос для нахождения всех админов!
 */
@WebServlet(name = "ServletAdminCreateSection")
public class ServletAdminCreateSection extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        User creator = (User) request.getSession().getAttribute("current_user");
        Section new_section = new Section((int)creator.get("id"), name);
        CustomRepository.add(new_section);
        response.sendRedirect("/forum");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Helpers.render(request, response, "create_section.ftl");
    }
}
