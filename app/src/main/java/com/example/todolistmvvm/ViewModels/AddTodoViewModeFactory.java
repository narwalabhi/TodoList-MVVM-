package com.example.todolistmvvm.ViewModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolistmvvm.Database.TodoDatabase;
import com.example.todolistmvvm.Database.TodoRepository;

public class AddTodoViewModeFactory extends ViewModelProvider.NewInstanceFactory {

    private TodoRepository mRepo;
    private int todoID;

    public AddTodoViewModeFactory(Context context, int todoID) {
        mRepo = new TodoRepository(context);
        this.todoID = todoID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddTodoViewModel(mRepo, todoID);
    }
}
