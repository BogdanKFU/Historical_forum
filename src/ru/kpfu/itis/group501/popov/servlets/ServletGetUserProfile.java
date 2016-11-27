package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Role;
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

@WebServlet(name = "ServletGetUserProfile")
public class ServletGetUserProfile extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String string = request.getParameter("id");
        Integer id = 0;
        try {
            id = Integer.valueOf(string);
        }
        catch (NumberFormatException ex) {
            response.sendRedirect("/profile");
        }
        Repository repository = RepositorySingleton.getRepository();
        CustomStatement cs = new CustomStatement();
        Map map = repository.do_select(cs.selectBy(User.class, "id", id));
        List list = (List) map.get("User");
        if (list != null && !list.isEmpty()) {
            User user = (User) list.get(0);
            Map<String, Object> root = new HashMap<>();
            root.put("user", user);
            response.setCharacterEncoding("utf-8");
            Role role = (Role) request.getSession().getAttribute("role");
            root.put("role", role);
            User current_user = (User) request.getSession().getAttribute("current_user");
            root.put("current_user", current_user);
            Helpers.render(request, response, "another_profile.ftl", root);
        }
        else {
            response.sendRedirect("/profile");
        }
    }
}
