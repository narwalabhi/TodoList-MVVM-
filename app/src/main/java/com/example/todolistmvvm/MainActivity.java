package com.example.todolistmvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todolistmvvm.Database.TodoItem;
import com.example.todolistmvvm.ViewModels.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvTodoList;
    private FloatingActionButton fabAdd;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTodoList = findViewById(R.id.rvTodoList);
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter(this);
        rvTodoList.setAdapter(todoAdapter);
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });
        setUpViewModel();
    }

    private void setUpViewModel() {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<List<TodoItem>> todosLiveData = mainViewModel.getAllTodos();
        todosLiveData.observe(this, new Observer<List<TodoItem>>() {
            @Override
            public void onChanged(List<TodoItem> todoItems) {
                todoAdapter.setTodos(todoItems);
            }
        });
    }


}