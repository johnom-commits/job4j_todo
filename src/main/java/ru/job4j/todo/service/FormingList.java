package ru.job4j.todo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.todo.domain.Item;
import ru.job4j.todo.persistence.HbmStore;

import java.util.List;

public class FormingList {

    public String getList(boolean allTasks) {
        List<Item> items = HbmStore.instanceOf().getList(allTasks);
        var builder = new StringBuilder();
        items.forEach(item -> {
            builder.append("<li>")
                    .append("<input type=\"checkbox\" id=")
                    .append("item_")
                    .append(item.getId());
            if (item.isDone()) {
                builder.append(" checked");
            }
            builder.append("><label for=")
                    .append("item_")
                    .append(item.getId())
                    .append(">")
                    .append(item.getTask())
                    .append("</label></li>");
        });
        return builder.toString();
    }
}
