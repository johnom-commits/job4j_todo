package ru.job4j.todo.controller;

import ru.job4j.todo.domain.User;
import ru.job4j.todo.service.AddMessage;
import ru.job4j.todo.service.FormingList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getList")
public class GetListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        var allTasksString = req.getParameter("allTasks");
        boolean allTasks = Boolean.parseBoolean(allTasksString);

        User user = (User) req.getSession().getAttribute("user");

        String[] categories = req.getParameterValues("category[]");

        var textTask = req.getParameter("addTask");
        if (!"".equals(textTask)) {
            new AddMessage().add(textTask, user, categories);
        }

        int idTask = Integer.parseInt(req.getParameter("changeTask"));
        if (idTask != -1) {
            new AddMessage().changeDone(idTask);
        }

        var list = new FormingList().getList(allTasks, user);
        var writer = resp.getWriter();
        writer.println(list);
        writer.flush();
    }
}
