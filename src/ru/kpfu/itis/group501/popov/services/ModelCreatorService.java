package ru.kpfu.itis.group501.popov.services;

import ru.kpfu.itis.group501.popov.models.*;
import ru.kpfu.itis.group501.popov.repository.custom.CustomRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelCreatorService {
    private static Pattern text = Pattern.compile("[A-Za-z#@$%^&*()_+=0-9/?!А-Яа-я.,]*");

    public static boolean createTopic(HttpServletRequest request) {
        String string = request.getParameter("id");
        int id;
        try {
            id = Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return false;
        }
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        Matcher matcher1 = text.matcher(name);
        Matcher matcher2 = text.matcher(content);
        if (!matcher1.matches() || !matcher2.matches()) {
            return false;
        }
        User current_user = (User) request.getSession().getAttribute("current_user");
        Date date = new Date();
        java.sql.Date publish_date = new java.sql.Date(date.getTime());
        Time publish_time = new Time(publish_date.getTime());
        publish_time.toLocalTime();
        Topic new_topic = new Topic(content, (int) current_user.get("id"), name, publish_date, publish_time, id);
        CustomRepository.add(new_topic);
        return true;
    }

    public static boolean createTopicComment(HttpServletRequest request) {
        String string = request.getParameter("id");
        Integer topic_id;
        try {
            topic_id = Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return false;
        }
        String content = request.getParameter("content");
        Matcher matcher = text.matcher(content);
        if (!matcher.matches()) {
            return false;
        }
        User current_user = (User) request.getSession().getAttribute("current_user");
        Date date = new Date();
        java.sql.Date sending_date = new java.sql.Date(date.getTime());
        Time sending_time = new Time(sending_date.getTime());
        sending_time.toLocalTime();
        TopicComment new_topic_comment = new TopicComment(content, (int) current_user.get("id"), sending_date, sending_time, topic_id);
        CustomRepository.add(new_topic_comment);
        return true;
    }

    public static boolean createEventComment(HttpServletRequest request) {
        String string = request.getParameter("id");
        Integer events_article;
        try {
            events_article = Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return false;
        }
        String content = request.getParameter("content");
        Matcher matcher = text.matcher(content);
        if (!matcher.matches()) {
            return false;
        }
        User current_user = (User) request.getSession().getAttribute("current_user");
        Date date = new Date();
        java.sql.Date send_date = new java.sql.Date(date.getTime());
        Time send_time = new Time(send_date.getTime());
        send_time.toLocalTime();
        EventComment new_event_comment = new EventComment(content, events_article, 0, send_date, send_time, (int) current_user.get("id"));
        CustomRepository.add(new_event_comment);
        return true;
    }

    public static boolean createPersonComment(HttpServletRequest request) {
        String string = request.getParameter("id");
        Integer persons_article;
        try {
            persons_article = Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return false;
        }
        String content = request.getParameter("content");
        Matcher matcher = text.matcher(content);
        if (!matcher.matches()) {
            return false;
        }
        User current_user = (User) request.getSession().getAttribute("current_user");
        Date date = new Date();
        java.sql.Date send_date = new java.sql.Date(date.getTime());
        Time send_time = new Time(send_date.getTime());
        send_time.toLocalTime();
        PersonComment new_person_comment = new PersonComment(content, persons_article, 0, send_date, send_time, (int) current_user.get("id"));
        CustomRepository.add(new_person_comment);
        return true;
    }
}
