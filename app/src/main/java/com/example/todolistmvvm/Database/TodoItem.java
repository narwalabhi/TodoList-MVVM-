package com.example.todolistmvvm.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "todo")
@TypeConverters({DateConverter.class})
public class TodoItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "priority")
    private int priority;

    @ColumnInfo(name = "updated_at")

    private Date updateAt;

    @Ignore
    public TodoItem(String description, int priority, Date updateAt) {
        this.description = description;
        this.priority = priority;
        this.updateAt = updateAt;
    }

    public TodoItem(int id, String description, int priority, Date updateAt) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", updateAt=" + updateAt +
                '}';
    }
}
