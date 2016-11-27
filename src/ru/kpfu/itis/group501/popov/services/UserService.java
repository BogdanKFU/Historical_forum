package ru.kpfu.itis.group501.popov.services;

import ru.kpfu.itis.group501.popov.models.CustomCookie;
import ru.kpfu.itis.group501.popov.models.Role;
import ru.kpfu.itis.group501.popov.models.UsersRoles;
import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.repository.custom.CustomStatement;
import ru.kpfu.itis.group501.popov.models.User;
import ru.kpfu.itis.group501.popov.repository.custom.CustomRepository;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private static Pattern password_p = Pattern.compile("[A-Za-z0-9_]{4,}[A-Za-z0-9-_]*");
    private static Pattern email_p = Pattern.compile("[a-z0-9A-Z]?[a-z0-9A-Z]*@[a-z0-9A-Z]?[a-z0-9A-Z]*\\.[a-z0-9A-Z]?[a-z0-9A-Z]*");
    private static Pattern text = Pattern.compile("[A-Za-z#@$%^&*()_+=0-9/?!а-яА-Я., ]*");
    private static Pattern for_names = Pattern.compile("[A-Za-z0-9а-яА-Я]?[A-Za-z0-9а-яА-Я/?!() ]*");
    private static Repository repository = RepositorySingleton.getRepository();

    public static boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
        Matcher matcher = password_p.matcher(request.getParameter("password"));
        Matcher matcher1 = password_p.matcher(request.getParameter("username"));
        if (!matcher.matches() || !matcher1.matches()) {
            return false;
        }
        CustomStatement cs = new CustomStatement();
        Map map = repository.do_select(cs.select(User.class).join(UsersRoles.class).joinBy(Role.class, "User.username", request.getParameter("username")));
        List list = (List) map.get("UsersRoles");
        User auth_user;
        if (list != null && !list.isEmpty()) {
            auth_user = (User) ((Map)((UsersRoles)list.get(0)).get("relations")).get("id_user");
            Role role = (Role) ((Map)((UsersRoles)list.get(0)).get("relations")).get("id_role");
            String pass = request.getParameter("password");
            String hash = hash(pass);
            if (auth_user.get("password").equals(hash)) {
                request.getSession().setAttribute("current_user", auth_user);
                request.getSession().setAttribute("role", role);
                String token = create_token(auth_user);
                Cookie cookie = new Cookie("current_user", token);
                java.util.Date date1 = new java.util.Date();
                Time assign_time = new Time(date1.getTime());
                if (request.getParameter("remember") != null) {
                    CustomCookie customCookie = new CustomCookie((int) auth_user.get("id"), token, assign_time);
                    repository.add(customCookie);
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);
                }
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
    public static boolean edit(HttpServletRequest request) {
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
        Matcher matcher1 = password_p.matcher(password);
        Matcher matcher2 = for_names.matcher(first_name);
        Matcher matcher3 = for_names.matcher(last_name);
        Matcher matcher4 = text.matcher(interest);
        if (!matcher1.matches() || !matcher2.matches() || !matcher3.matches() || !matcher4.matches()) {
            return false;
        }
        if (!password.equals(password2)) {
            return false;
        }
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
        repository.update(auth_user);
        return true;
    }

    public static String hash(String password) {
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
        Matcher matcher1 = password_p.matcher(login);
        Matcher matcher2 = password_p.matcher(password);
        Matcher matcher3 = email_p.matcher(email);
        Matcher matcher4 = for_names.matcher(name);
        Matcher matcher5 = for_names.matcher(surname);
        if (!matcher1.matches() || !matcher2.matches() || !matcher3.matches() || !matcher4.matches() || !matcher5.matches()) {
            throw new NullPointerException("Данные не верны");
        }
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = format.parse(birth_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CustomStatement cs = new CustomStatement();
        Map map = repository.do_select(cs.selectBy(User.class, "username", login).or("User.email", email));
        if (map == null) {
            return false;
        }
        else {
            String hash = hash(password);
            User new_user;
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
            repository.add(new_user);
            UsersRoles usersRoles = new UsersRoles((int)new_user.get("id"), 2);
            repository.add(usersRoles);
            request.getSession().setAttribute("current_user", new_user);
            request.getSession().setAttribute("role", usersRoles);
            String token = create_token(new_user);
            Cookie cookie = new Cookie("current_user", token);
            java.util.Date date1 = new java.util.Date();
            Time assign_time = new Time(date1.getTime());
            CustomCookie customCookie = new CustomCookie((int) new_user.get("id"), token,assign_time);
            repository.add(customCookie);
            cookie.setMaxAge(60);
            response.addCookie(cookie);
            return true;
        }
    }
}
