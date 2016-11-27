package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.Message;
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

@WebServlet(name = "ServletInputMessages")
public class ServletInputMessages extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Repository repository = RepositorySingleton.getRepository();
        User current_user = (User) request.getSession().getAttribute("current_user");
        CustomStatement cs = new CustomStatement();
        Map map = repository.do_select(cs.selectBy(Message.class, "recipient", current_user.get("id")));
        List list = (List) map.get("Message");
        Map<String, Object> root = new HashMap<>();
        root.put("sections", list);
        Role role = (Role) request.getSession().getAttribute("role");
        root.put("role", role);
        root.put("current_user", current_user);
        Helpers.render(request, response, "input_messages.ftl", root);
    }
}
