package ru.job4j.todo.service;

import org.json.simple.JSONObject;
import ru.job4j.todo.domain.Item;
import ru.job4j.todo.domain.User;
import ru.job4j.todo.persistence.HbmStore;

import java.util.List;

public class FormingList {
    public String getList(boolean allTasks, User user) {
        List<Item> items = HbmStore.instanceOf().getList(allTasks, user);
        var json = new JSONObject();
        json.put("items", getItems(user, items));
        json.put("login", getAuth(user));
        return json.toJSONString();
    }

    private String getItems(User user, List<Item> items) {
        var list = new StringBuilder();
        list.append("<p>Пользователь: ");
        if (user != null) {
            list.append(user.getName());
        } else {
            list.append("авторизуйтесь");
        }

        items.forEach(item -> {
            list.append("<li>")
                    .append("<input type=\"checkbox\" id=")
                    .append("item_")
                    .append(item.getId());
            if (item.isDone()) {
                list.append(" checked");
            }
            list.append("><label for=")
                    .append("item_")
                    .append(item.getId())
                    .append(">")
                    .append(item.getTask())
                    .append("</label></li>");
        });
        return list.toString();
    }

    private String getAuth(User user) {
        String login = "";
        if (user == null) {
            login = "<a href=\"login.html\" class=\"login\">Войти</a>\n" +
                    "<a href=\"reg.html\">Регистрация</a>";
        } else {
            login = "<a href=\"login.html\">" + user.getName() + " | Выйти</a>";
        }
        return login;
    }
}
