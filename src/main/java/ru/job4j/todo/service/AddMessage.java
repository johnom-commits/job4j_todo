package ru.job4j.todo.service;

import ru.job4j.todo.domain.Item;
import ru.job4j.todo.persistence.HbmStore;

import java.sql.Timestamp;
import java.util.Optional;

public class AddMessage {
    public void add(String desc) {
        Item item = new Item(desc, new Timestamp(System.currentTimeMillis()));
        HbmStore.instanceOf().create(item);
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