package com.example.todolistmvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.todolistmvvm.Database.TodoItem;
import com.example.todolistmvvm.Database.TodoRepository;
import com.example.todolistmvvm.ViewModels.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoAdapter.OnTodoItemClickListener {

    private RecyclerView rvTodoList;
    private FloatingActionButton fabAdd;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvTodoList = findViewById(R.id.rvTodoList);
        rvTodoList.setLayoutManager(new LinearLayoutManager(this));

        todoAdapter = new TodoAdapter(this, this);
        rvTodoList.setAdapter(todoAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                TodoRepository todoRepository = new TodoRepository(MainActivity.this);
                int position = viewHolder.getAdapterPosition();
                TodoItem todoItem = todoAdapter.getTodoItem(position);
                todoRepository.deleteTodo(todoItem);
            }
        }).attachToRecyclerView(rvTodoList);

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


    @Override
    public void onClick(int i) {
        Intent intent = new Intent(this, AddTodoActivity.class);
        intent.putExtra(AddTodoActivity.EXTRA_TODO_ID, i);
        startActivity(intent);
    }
}