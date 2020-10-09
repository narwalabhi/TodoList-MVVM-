package com.example.todolistmvvm.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolistmvvm.Database.TodoItem;
import com.example.todolistmvvm.Database.TodoRepository;

public class AddTodoViewModel extends ViewModel {

    LiveData<TodoItem> todoItem;

    public AddTodoViewModel(TodoRepository mRepo, int todoID) {
        todoItem = mRepo.getTodoByID(todoID);
    }

    public LiveData<TodoItem> getTodoItemById(){
        return todoItem;
    }

}
