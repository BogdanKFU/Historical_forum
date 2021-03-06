package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

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
        Repository repository = RepositorySingleton.getRepository();
        Integer id = 0;
        try {
            id = Integer.valueOf(string);
        }
        catch (NumberFormatException ex) {
            response.sendRedirect("/profile");
        }
        CustomStatement cs = new CustomStatement();
        Map map = repository.do_select(cs.selectBy(User.class, "id", id));
        List list = (List) map.get("User");
        if (list != null && !list.isEmpty()) {
            User user = (User) list.get(0);
            user.set("blocked", true);
            repository.update(user);
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
