package com.example.todolistmvvm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.todolistmvvm.Database.TodoItem;
import com.example.todolistmvvm.Database.TodoRepository;
import com.example.todolistmvvm.ViewModels.AddTodoViewModeFactory;
import com.example.todolistmvvm.ViewModels.AddTodoViewModel;

import java.util.Date;

public class AddTodoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TODO_ID = "extraTaskId";
    public static final String INSTANCE_TODO_ID = "instanceTaskId";
    private static final String TAG = AddTodoActivity.class.getSimpleName();

    private EditText etDescription;
    private RadioGroup radioGroup;
    private TodoRepository mRepo;

    private static final int DEFAULT_TODO_ID = -1;
    private int mTodoId = DEFAULT_TODO_ID;
    private final int HIGH_PRIORITY = 1;
    private final int MEDIUM_PRIORITY = 2;
    private final int LOW_PRIORITY = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        etDescription = findViewById(R.id.etTodoDescription);
        radioGroup = findViewById(R.id.radioGroup);
        Button btnAdd = findViewById(R.id.btnAdd);
        mRepo = new TodoRepository(this);
        btnAdd.setOnClickListener(this);

        if(savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TODO_ID)){
            mTodoId = savedInstanceState.getInt(INSTANCE_TODO_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TODO_ID)){
            btnAdd.setText(getString(R.string.update));
            if(mTodoId == DEFAULT_TODO_ID){
                mTodoId = intent.getIntExtra(EXTRA_TODO_ID, DEFAULT_TODO_ID);
                AddTodoViewModeFactory factory = new AddTodoViewModeFactory(this, mTodoId);
                AddTodoViewModel addTodoViewModel = ViewModelProviders.of(this, factory).get(AddTodoViewModel.class);
                LiveData<TodoItem> todoItemLiveData = addTodoViewModel.getTodoItemById();
                todoItemLiveData.observe(this, new Observer<TodoItem>() {
                    @Override
                    public void onChanged(TodoItem todoItem) {
                        etDescription.setText(todoItem.getDescription());
                        setPriorityRadioButton(todoItem.getPriority());
                    }
                });
            }
        }
    }

    private void setPriorityRadioButton(int priority) {
        switch (priority){
            case HIGH_PRIORITY :
                radioGroup.check(R.id.rbHigh);
                break;
            case MEDIUM_PRIORITY :
                radioGroup.check(R.id.rbMedium);
                break;
            case LOW_PRIORITY :
                radioGroup.check(R.id.rbLow);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(INSTANCE_TODO_ID, mTodoId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        String description = etDescription.getText().toString();
        int priority = getPriority();
        Date date = new Date();
        if(!description.equals("")){
            TodoItem todoItem = new TodoItem(description, priority, date);
            if (mTodoId == DEFAULT_TODO_ID) {
                mRepo.addTodo(todoItem);
            }else{
                todoItem.setId(mTodoId);
                mRepo.updateTodo(todoItem);
            }
            Log.d(TAG, "onClick: " + mTodoId + " " + todoItem.toString());
            finish();
        }else{
            Toast.makeText(this, "Please enter a description!", Toast.LENGTH_SHORT).show();
        }
    }

    private int getPriority() {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        switch (checkedId){
            case R.id.rbHigh:
                return HIGH_PRIORITY;
            case R.id.rbMedium:
                return MEDIUM_PRIORITY;
            case R.id.rbLow:
                return LOW_PRIORITY;
        }
        return 1;
    }
}
