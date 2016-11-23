package ru.kpfu.itis.group501.popov.servlets;

import freemarker.ext.beans.HashAdapter;
import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.PersonArticle;
import ru.kpfu.itis.group501.popov.repository.CustomRepository;
import ru.kpfu.itis.group501.popov.repository.CustomStatement;
import ru.kpfu.itis.group501.popov.services.ModelSearchService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletGetAllPersonArticle")
public class ServletGetAllPersonArticle extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Map<String, List<Object>> map = ModelSearchService.searchPersonArticle(request);
        Map<String, Object> root = new HashMap<>();
        if (map != null) {
            List list = map.get("PersonArticle");
            root.put("articles", list);
        }
        Helpers.render(request, response, "all_person_articles.ftl", root);
    }
}
