package ru.job4j.todo.persistence;

import ru.job4j.todo.domain.Item;

import java.util.List;

public interface TodoDAO {
    void create(Item item);

    List<Item> getList(boolean allTasks);

    Item findById(int id);

    void update(int id, Item item);
}
