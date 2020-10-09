package com.example.todolistmvvm.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todolistmvvm.Database.TodoItem;
import com.example.todolistmvvm.Database.TodoRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    TodoRepository mRepo;
    LiveData<List<TodoItem>> todos;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepo = new TodoRepository(application);
        todos = mRepo.getAllTodos();
    }

    public LiveData<List<TodoItem>> getAllTodos(){
        return todos;
    }
}
