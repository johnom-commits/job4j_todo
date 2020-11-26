package ru.job4j.todo.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String task;
    private Timestamp created;
    private boolean done;

    public Item() {
    }

    public Item(String desc, Timestamp created) {
        this.task = desc;
        this.created = created;
        this.done = false;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Timestamp getCreated() {
        return created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                done == item.done &&
                task.equals(item.task) &&
                created.equals(item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task, created, done);
    }
}
