package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.*;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.services.ModelCreatorService;
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

@WebServlet(name = "ServletPersonArticle")
public class ServletPersonArticle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        if (ModelCreatorService.createPersonComment(request)) {
            response.sendRedirect("/articles/person?id=" + request.getParameter("id"));
        }
        else {
            response.sendRedirect("/profile");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Repository repository = RepositorySingleton.getRepository();
        String string = request.getParameter("id");
        Integer id = 0;
        try {
            id = Integer.valueOf(string);
        }
        catch (NumberFormatException ex) {
            response.sendRedirect("/profile");
        }
        CustomStatement cs1 = new CustomStatement();
        Map map1 = repository.do_select(cs1.selectBy(PersonArticle.class, "id", id));
        List list = (List) map1.get("PersonArticle");
        CustomStatement cs = new CustomStatement();
        Map map = repository.do_select(cs.select(PersonComment.class).joinBy(User.class, "PersonComment.persons_article", id));
        if (!list.isEmpty()) {
            PersonArticle article = (PersonArticle) list.get(0);
            Map<String, Object> root = new HashMap<>();
            root.put("article", article);
            root.put("comments", map.get("PersonComment"));
            Role role = (Role) request.getSession().getAttribute("role");
            root.put("role", role);
            User current_user = (User) request.getSession().getAttribute("current_user");
            root.put("current_user", current_user);
            Helpers.render(request, response, "person_article.ftl", root);
        }
        else {
            response.sendRedirect("/profile");
        }
    }
}
