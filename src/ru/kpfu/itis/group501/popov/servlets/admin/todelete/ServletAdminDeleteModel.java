package ru.kpfu.itis.group501.popov.servlets.admin.todelete;

import ru.kpfu.itis.group501.popov.repository.Repository;
import ru.kpfu.itis.group501.popov.singletons.RepositorySingleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletAdminDeleteModel")
public class ServletAdminDeleteModel extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String str = request.getParameter("id");
        String model = request.getParameter("model");
        Repository repository = RepositorySingleton.getRepository();
        int id = Integer.valueOf(str);
        try {
            Class aClass = Class.forName("ru.kpfu.itis.group501.popov.models." + model);
            repository.delete(aClass, id);
            response.sendRedirect("/admin/entities/?model=" + model);
        } catch (ClassNotFoundException e) {
            response.sendRedirect("/admin/entities/?model=" + model);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
