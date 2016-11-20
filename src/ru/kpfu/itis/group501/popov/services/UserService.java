package ru.kpfu.itis.group501.popov.services;

import ru.kpfu.itis.group501.popov.repository.CustomStatement;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.CustomRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

// Здесь не должно быть обращений к БД
public class UserService {
    /*
    Получает все параметры.
    Проверяет их.
    Создает экземпляр класса models.User, добавляет его в сессию.
    Создает куки с уникальным токеном и добавляет его в response.
    Возвращает true, если удалось аутентифицировать пользователя,
    false, если - нет.
    */
    public static boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
        List list = CustomRepository.getBy(User.class, "username", request.getParameter("username"));
        User auth_user;
        if (list != null && !list.isEmpty()) {
            auth_user = (User) list.get(0);
            String pass = request.getParameter("password");
            String hash = hash(pass);
            if (auth_user.get("password").equals(hash)) {
                request.getSession().setAttribute("current_user", auth_user);
                String token = create_token(auth_user);
                Cookie cookie = new Cookie("current_user", token);
                java.util.Date date1 = new java.util.Date();
                Time assign_time = new Time(date1.getTime());
                CustomRepository.add_user_cookie(token, (int) auth_user.get("id"), assign_time);
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);
                return true;
            }
        }
        return false;
    }

    /*
    Проверяет параметры, переданные в request.
    Меняет значения полей.
    В конце сохраняет изменения в БД.
    Исправить!!!
     */
    public static void edit(HttpServletRequest request) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        User auth_user = (User) request.getSession().getAttribute("current_user");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String birth_date = request.getParameter("birth_date");
        String interest = request.getParameter("interest");
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = format.parse(birth_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sql_date = null;
        if (date != null) {
            sql_date = new Date(date.getTime());
        }
        auth_user.set("password", password);
        auth_user.set("first_name", first_name);
        auth_user.set("last_name", last_name);
        auth_user.set("birth_date", sql_date);
        auth_user.set("interest", interest);
        CustomRepository.update(auth_user);
    }

    private static String hash(String password) {
        MessageDigest md;
        String hash = "";
        String sole = "itis";
        password = password + sole;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] mdSHA = md.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for(byte bytes: mdSHA) {
                stringBuilder.append(String.format("%02x", bytes & 0xff));
            }
            hash = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public static String create_token(User user) {
        String sole = "itis";
        String username = (String) user.get("username");
        java.util.Date date = new java.util.Date();
        return hash(username + sole + date);
    }

    /*
    Получает все параметры.
    Проверяет их.
    Создает нового пользователя.
    Ложит в сессию нового пользователя.
    Создает куки.
    Возвращает true, если удалось зарегистрировать,
    false, если - нет.
     */
    public static boolean reg(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String birth_date = request.getParameter("birth_date");
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = format.parse(birth_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CustomStatement cs = new CustomStatement();
        Map map = CustomRepository.do_sql(cs.selectBy(User.class, "username", login).or("email", email));
        if (map == null) {
            return false;
        }
        else {
            String hash = hash(password);
            User new_user = null;
            try {
                if (date != null) {
                    new_user = new User(login, hash, email, new Date(date.getTime()), name, surname);
                }
                else {
                    return false;
                }
            }
            catch (NullPointerException ex) {
                return false;
            }
            CustomRepository.add(new_user);
            request.getSession().setAttribute("current_user", new_user);
            String token = create_token(new_user);
            Cookie cookie = new Cookie("current_user", token);
            java.util.Date date1 = new java.util.Date();
            Time assign_time = new Time(date1.getTime());
            CustomRepository.add_user_cookie(token, (int) new_user.get("id"), assign_time);
            cookie.setMaxAge(60);
            response.addCookie(cookie);
            return true;
        }
    }
}
