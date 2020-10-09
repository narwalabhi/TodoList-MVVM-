package com.example.todolistmvvm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class AddTodoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etDescription;
    private RadioGroup radioGroup;

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
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String description = etDescription.getText().toString();
        int priority = getPriority();
        Date date = new Date();
        if(!description.equals("")){

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
