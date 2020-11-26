package ru.job4j.todo.service;

import ru.job4j.todo.domain.Item;
import ru.job4j.todo.persistence.HbmStore;

import java.sql.Timestamp;

public class AddMessage {
    public void add(String desc) {
        Item item = new Item(desc, new Timestamp(System.currentTimeMillis()));
        HbmStore.instanceOf().create(item);
    }

    public void changeDone(int idTask) {
        HbmStore task = HbmStore.instanceOf();
        Item item = task.findById(idTask);
        if (item != null) {
            item.setDone(!item.isDone());
            task.update(idTask, item);
        }
    }
}
