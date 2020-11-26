package ru.job4j.todo.controller;

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

        var textTask = req.getParameter("addTask");
        if (!"".equals(textTask)) {
            new AddMessage().add(textTask);
        }

        int idTask = Integer.parseInt(req.getParameter("changeTask"));
        if (idTask != -1) {
            new AddMessage().changeDone(idTask);
        }
        var list = new FormingList().getList(allTasks);
        var writer = resp.getWriter();
        writer.println(list);
        writer.flush();
    }
}