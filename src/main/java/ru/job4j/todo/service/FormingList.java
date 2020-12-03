package ru.job4j.todo.service;

import org.json.simple.JSONObject;
import ru.job4j.todo.domain.Category;
import ru.job4j.todo.domain.Item;
import ru.job4j.todo.domain.User;
import ru.job4j.todo.persistence.HbmStore;

import java.util.List;
import java.util.Map;

public class FormingList {
    public String getList(boolean allTasks, User user) {
        List<Item> items = HbmStore.instanceOf().getList(allTasks, user);
        List<Category> category = HbmStore.instanceOf().getCategory();
        var json = new JSONObject();
        json.put("items", getItems(user, items));
        json.put("login", getAuth(user));
        json.put("category", getSelect(category));
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

    private String getSelect(List<Category> categories) {
        StringBuilder select = new StringBuilder("<label for=\"cIds\">Категории:</label><div><select name=\"cIds\" id=\"cIds\" multiple>");
        for (Category c : categories) {
            select.append("<option value=")
                    .append("item_")
                    .append(c.getId())
                    .append(">")
                    .append(c.getName())
                    .append("</option>");
        }
                select.append("</select></div>");
        return select.toString();
    }
}
