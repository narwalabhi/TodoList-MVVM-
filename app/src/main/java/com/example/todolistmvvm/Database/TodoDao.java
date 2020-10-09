package com.example.todolistmvvm.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY priority")
    LiveData<List<TodoItem>> getAllTodos();

    @Insert
    void insertTodo(TodoItem todoItem);

    @Delete
    void deleteTodo(TodoItem todoItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTodo(TodoItem todoItem);

    @Query("SELECT * FROM todo WHERE id = :todoId")
    LiveData<TodoItem> getTodoByID(int todoId);

}
