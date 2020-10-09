package com.example.todolistmvvm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistmvvm.Database.TodoItem;
import com.example.todolistmvvm.Database.TodoRepository;

import java.util.Date;

public class AddTodoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etDescription;
    private RadioGroup radioGroup;
    private TodoRepository mRepo;
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
    }

    @Override
    public void onClick(View view) {
        String description = etDescription.getText().toString();
        int priority = getPriority();
        Date date = new Date();
        if(!description.equals("")){
            TodoItem todoItem = new TodoItem(description, priority, date);
            mRepo.addTodo(todoItem);
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
