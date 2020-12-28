package com.example.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;



public class EditActivity extends AppCompatActivity {

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


        getSupportActionBar().setTitle("Edit item");

        Intent intent = getIntent();
        setItem(intent);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent();
                i.putExtra(MainActivity.KEY_TASK_TEXT, etTodo.getText().toString());
                i.putExtra(MainActivity.KEY_NOTES_TEXT, etNotes.getText().toString());
                i.putExtra(MainActivity.KEY_DATE_TEXT, etDate.getText().toString());
                i.putExtra(MainActivity.KEY_TIME_TEXT, etTime.getText().toString());
                i.putExtra(MainActivity.KEY_PRIORITY_TEXT, prioritySpinner.getSelectedItem().toString());
                i.putExtra(MainActivity.KEY_POSITION, position);

                setResult(RESULT_OK, i);
                finish();

            }
        });



    }


    private void setItem(Intent intent) {
        String task = intent.getStringExtra(MainActivity.KEY_TASK_TEXT);
        if(task != null) {
            String notes = intent.getStringExtra(MainActivity.KEY_NOTES_TEXT);
            String date = intent.getStringExtra(MainActivity.KEY_DATE_TEXT);
            String time = intent.getStringExtra(MainActivity.KEY_TIME_TEXT);
            String priority = intent.getStringExtra(MainActivity.KEY_PRIORITY_TEXT);
            etTodo.setText(task);
            etNotes.setText(notes);
            etDate.setText(date);
            etTime.setText(time);
            int index = 0;
            if(priority.equals("Low"))
                index = 1;
            else if(priority.equals("Medium"))
                index = 2;
            else if(priority.equals("High"))
                index = 3;
            prioritySpinner.setSelection(index);
            position = intent.getIntExtra(MainActivity.KEY_POSITION, -1);
        }
    }

}