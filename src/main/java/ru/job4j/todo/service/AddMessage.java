package ru.job4j.todo.service;

import ru.job4j.todo.domain.Item;
import ru.job4j.todo.domain.User;
import ru.job4j.todo.persistence.HbmStore;

import java.sql.Timestamp;
import java.util.Optional;

public class AddMessage {
    public void add(String desc, User user, String[] categories) {
        Item item = Item.of(desc, user);
        HbmStore.instanceOf().create(item, categories);
    }

    public void changeDone(int idTask) {
        HbmStore task = HbmStore.instanceOf();
        Optional<Item> itemOpt = task.findById(idTask);
        itemOpt.ifPresent(item -> {
            item.setDone(!item.isDone());
            task.update(idTask, item);
        });
    }
}
