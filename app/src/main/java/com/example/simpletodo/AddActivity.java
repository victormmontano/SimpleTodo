package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = etTodo.getText().toString();
                if(task.isEmpty()){
                    etTodo.setError("Field is required");
                } else {
                    String notes = etNotes.getText().toString();
                    if(notes.isEmpty())
                        notes = "";
                    String date = etDate.getText().toString();
                    if(date.isEmpty())
                        date = "";
                    String time = etTime.getText().toString();
                    if(time.isEmpty())
                        time = "";
                    String priority = prioritySpinner.getSelectedItem().toString();
                    if(priority.isEmpty())
                        priority = "None";

                    Intent i = new Intent(AddActivity.this, MainActivity.class);
                    i.putExtra("task", task);
                    i.putExtra("notes", notes);
                    i.putExtra("date", date);
                    i.putExtra("time", time);
                    i.putExtra("priority", priority);
                   // i.putExtra("position", position);

                    startActivity(i);


                }
            }
        });

    }


}