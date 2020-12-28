package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;



import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_TASK_TEXT = "task_text";
    public static final String KEY_NOTES_TEXT = "notes_text";
    public static final String KEY_DATE_TEXT = "date_text";
    public static final String KEY_TIME_TEXT = "time_text";
    public static final String KEY_PRIORITY_TEXT = "priority_text";
    public static final String KEY_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;


    FloatingActionButton btnAdd;
    RecyclerView rvItems;
    ItemAdapter itemAdapter;

    List<String> itemData;
    List<Item> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        rvItems = findViewById(R.id.itemRecycler);

        loadItems();

        ItemClickListener onClickListener = new ItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                Item item = items.get(position);
                i.putExtra(KEY_TASK_TEXT, item.getTask());
                i.putExtra(KEY_NOTES_TEXT, item.getNotes());
                i.putExtra(KEY_DATE_TEXT, item.getDate());
                i.putExtra(KEY_TIME_TEXT, item.getTime());
                i.putExtra(KEY_PRIORITY_TEXT, item.getPriority());
                i.putExtra(KEY_POSITION, position);
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };


        ItemLongClickListener onLongClickListener = new ItemLongClickListener(){

            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                for(int i = 0; i <  5; i++)
                    itemData.remove(position*5);
                itemAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "item was removed.", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };


        itemAdapter = new ItemAdapter(items, onClickListener, onLongClickListener);
        rvItems.setAdapter(itemAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        setItem(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            String task = data.getStringExtra(KEY_TASK_TEXT);
            String notes = data.getStringExtra(KEY_NOTES_TEXT);
            String date = data.getStringExtra(KEY_DATE_TEXT);
            String time = data.getStringExtra(KEY_TIME_TEXT);
            String priority = data.getStringExtra(KEY_PRIORITY_TEXT);
            int position = data.getIntExtra(KEY_POSITION, -1);
            items.remove(position);
            Item item = new Item(task, notes, date, time, priority);
            items.add(position, item);

            for(int i = 0; i < 5; i++){
                itemData.remove(position*5);
            }

            itemData.add(position*5, priority);
            itemData.add(position*5, time);
            itemData.add(position*5, date);
            itemData.add(position*5, notes);
            itemData.add(position*5, task);

            itemAdapter.notifyItemChanged(position);
            saveItems();
            Toast.makeText(getApplicationContext(), "Item edited", Toast.LENGTH_SHORT);



        } else {
            Log.w("MainActivity", "Unknown call on activity result");
        }

    }



    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    private void loadItems(){
        try {
            itemData = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            items = new ArrayList<>();

            for(int i = 0; i < itemData.size(); i+=5){
                String todo = itemData.get(i);
                String notes = itemData.get(i+1);
                String date = itemData.get(i+2);
                String time = itemData.get(i+3);
                String priority = itemData.get(i+4);
                Item item = new Item(todo, notes, date, time, priority);
                items.add(item);
            }


        } catch (IOException e) {
            Log.d("MainActivity", "Error reading items", e);
            itemData = new ArrayList<>();
            items = new ArrayList<>();
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), itemData);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MainActivity", "Error writing items", e);
        }
    }

    private void setItem(Intent intent){
        String task = intent.getStringExtra(KEY_TASK_TEXT);
        if(task != null) {
            String notes = intent.getStringExtra(KEY_NOTES_TEXT);
            String date = intent.getStringExtra(KEY_DATE_TEXT);
            String time = intent.getStringExtra(KEY_TIME_TEXT);
            String priority = intent.getStringExtra(KEY_PRIORITY_TEXT);
            itemData.add(task);
            itemData.add(notes);
            itemData.add(date);
            itemData.add(time);
            itemData.add(priority);
            Item item = new Item(task, notes, date, time, priority);
            items.add(item);
            itemAdapter.notifyItemInserted(items.size()-1);

            saveItems();
        }

    }
}