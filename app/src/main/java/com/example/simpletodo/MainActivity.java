package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etItem;
    FloatingActionButton btnAdd;
    RecyclerView rvItems;
    ItemAdapter itemAdapter;

    List<String> itemData;
    List<Item> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etItem = findViewById(R.id.editItem);
        btnAdd = findViewById(R.id.btnAdd);
        rvItems = findViewById(R.id.itemRecycler);

        loadItems();//items = new ArrayList<>();

        ItemClickListener onClickListener = new ItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                Item item = items.get(position);
                i.putExtra("task", item.getTask());
                i.putExtra("notes", item.getNotes());
                i.putExtra("date", item.getDate());
                i.putExtra("time", item.getTime());
                i.putExtra("priority", item.getPriority());
                i.putExtra("position", position);
                /*items.remove(position);
                for(int j = 0 ; j < 5; j++)
                    itemData.remove(position*5);
                saveItems();*/
                startActivityForResult(i, 20);
            }
        };


        ItemLongClickListener onLongClickListener = new ItemLongClickListener(){

            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                for(int i = 0; i <  5; i++)
                    itemData.remove(position);
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
                /*String item = etItem.getText().toString();
                items.add(item);
                itemAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "item was added.", Toast.LENGTH_SHORT).show();
                saveItems();*/
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
        if (resultCode == RESULT_OK && requestCode == 20) {
            String task = data.getStringExtra("task");
            String notes = data.getStringExtra("notes");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            String priority = data.getStringExtra("priority");
            int position = data.getIntExtra("position", -1);

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

            for(int i = 0; i < itemData.size(); i++){
                Log.d("!!Print", itemData.get(i));
            }


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
      /*  i.putExtra("task", task);
        i.putExtra("notes", notes);
        i.putExtra("date", date);
        i.putExtra("time", time);
        i.putExtra("priority", priority);*/
        String task = intent.getStringExtra("task");
        if(task != null) {
            String notes = intent.getStringExtra("notes");
            String date = intent.getStringExtra("date");
            String time = intent.getStringExtra("time");
            String priority = intent.getStringExtra("priority");
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

    /*@Override
    public void onStop() {
        super.onStop();
        try {
            FileUtils.writeLines(getDataFile(), null);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("MainActivity", "Error writing items", e);
        }
    }*/


}