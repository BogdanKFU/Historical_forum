package ru.kpfu.itis.group501.popov.services;
import ru.kpfu.itis.group501.popov.helpers.Helpers;
import ru.kpfu.itis.group501.popov.models.EventArticle;
import ru.kpfu.itis.group501.popov.models.PersonArticle;
import ru.kpfu.itis.group501.popov.models.Section;
import ru.kpfu.itis.group501.popov.models.Topic;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.repository.custom.CustomRepository;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class ModelSearchService {
    private static Repository repository = RepositorySingleton.getRepository();

    public static Map<String, List<Object>> searchPersonArticle(HttpServletRequest request) {
        String name = request.getParameter("name");
        String str1 = request.getParameter("firstdate");
        String str2 = request.getParameter("lastdate");
        CustomStatement cs = new CustomStatement();
        cs = cs.select(PersonArticle.class);
        if (name != null) {
            cs = cs.like("PersonArticle.name", name);
        }
        Date birth_date = null;
        if (str1 != null) {
            birth_date = Helpers.toDate(str1);
        }
        Date dead_date = null;
        if (str2 != null) {
            dead_date = Helpers.toDate(str2);
        }
        if (birth_date != null && cs.getValues().size() == 0) {
            cs = cs.ge("PersonArticle.birth_date", birth_date);
        }
        if (birth_date != null && cs.getValues().size() > 0) {
            cs = cs.and().ge("PersonArticle.birth_date", birth_date);
        }
        if (dead_date != null && cs.getValues().size() == 0) {
            cs = cs.le("PersonArticle.dead_date", dead_date);
        }
        if (dead_date != null && cs.getValues().size() > 0) {
            cs = cs.and().le("PersonArticle.dead_date", dead_date);
        }
        return repository.do_select(cs);
    }

    public static Map<String, List<Object>> searchEventArticle(HttpServletRequest request) {
        String name = request.getParameter("name");
        String str1 = request.getParameter("firstdate");
        String str2 = request.getParameter("lastdate");
        CustomStatement cs = new CustomStatement();
        cs = cs.select(EventArticle.class);
        if (name != null) {
            cs = cs.like("EventArticle.name", name);
        }
        Date birth_date = null;
        if (str1 != null) {
            birth_date = Helpers.toDate(str1);
        }
        Date dead_date = null;
        if (str2 != null) {
            dead_date = Helpers.toDate(str2);
        }
        if (birth_date != null && cs.getValues().size() == 0) {
            cs = cs.ge("EventArticle.begin_date", birth_date);
        }
        if (birth_date != null && cs.getValues().size() > 0) {
            cs = cs.and().ge("EventArticle.begin_date", birth_date);
        }
        if (dead_date != null && cs.getValues().size() == 0) {
            cs = cs.le("EventArticle.end_date", dead_date);
        }
        if (dead_date != null && cs.getValues().size() > 0) {
            cs = cs.and().le("EventArticle.end_date", dead_date);
        }
        return repository.do_select(cs);
    }

    public static Map<String, List<Object>> searchSection(HttpServletRequest request) {
        String name = request.getParameter("name");
        CustomStatement cs = new CustomStatement();
        cs = cs.select(Section.class);
        if (name != null) {
            cs = cs.like("Section.name", name);
        }
        return repository.do_select(cs);
    }

    public static Map<String, List<Object>> searchTopic(HttpServletRequest request) {
        String name = request.getParameter("name");
        CustomStatement cs = new CustomStatement();
        cs = cs.select(Topic.class);
        if (name != null) {
            cs = cs.like("Topic.name", name);
        }
        return repository.do_select(cs);
    }
}
