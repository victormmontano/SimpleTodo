package com.example.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;


public class AddActivity extends AppCompatActivity {

    EditText etTodo, etNotes, etDate, etTime;
    Button btnSave;
    Spinner prioritySpinner;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnSave = findViewById(R.id.newSaveButton);
        etTodo = findViewById(R.id.etTodo);
        etNotes = findViewById(R.id.etNotes);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        prioritySpinner = findViewById(R.id.prioritySpinner);

        position = -1;

        getSupportActionBar().setTitle("Add item");


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = etTodo.getText().toString();
                if(task.isEmpty()){
                    etTodo.setError("Field is required");
                } else {
                    String notes = etNotes.getText().toString();
                    String date = etDate.getText().toString();
                    String time = etTime.getText().toString();
                    String priority = prioritySpinner.getSelectedItem().toString();

                    Intent i = new Intent(AddActivity.this, MainActivity.class);
                    i.putExtra(MainActivity.KEY_TASK_TEXT, task);
                    i.putExtra(MainActivity.KEY_NOTES_TEXT, notes);
                    i.putExtra(MainActivity.KEY_DATE_TEXT, date);
                    i.putExtra(MainActivity.KEY_TIME_TEXT, time);
                    i.putExtra(MainActivity.KEY_PRIORITY_TEXT, priority);

                    startActivity(i);


                }
            }
        });

    }


}