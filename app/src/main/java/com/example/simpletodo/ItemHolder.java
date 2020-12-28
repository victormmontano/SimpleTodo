package com.example.simpletodo;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;


public class ItemHolder extends RecyclerView.ViewHolder {

    TextView iPriority, iTask, iDate, iTime, iNotes;


    public ItemHolder(@NonNull final View itemView, final ItemClickListener clickListener,
                      final ItemLongClickListener longClickListener) {
        super(itemView);

        this.iPriority = itemView.findViewById(R.id.tvPriority);
        this.iTask = itemView.findViewById(R.id.tvTodo);
        this.iDate = itemView.findViewById(R.id.tvDate);
        this.iTime = itemView.findViewById(R.id.tvTime);
        this.iNotes = itemView.findViewById(R.id.tvNotes);

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClickListener.onItemLongClicked(getAdapterPosition());
                return true;
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClicked(getAdapterPosition());
            }
        });

    }

    public void bind(Item item){
        String priority = item.getPriority();
        if(priority.equals("Low"))
            iPriority.setText("! ");
        else if(priority.equals("Medium"))
            iPriority.setText("!! ");
        else if(priority.equals("High"))
            iPriority.setText("!!! ");
        else
            iPriority.setText("");

        String task = item.getTask();
        iTask.setText(task);

        String date = item.getDate();

        if(date.isEmpty()) {
            iDate.setVisibility(View.GONE);
        }
        else {
            iDate.setVisibility(View.VISIBLE);
            iDate.setText(date);
        }

        String time = item.getTime();
        iTime.setText(time);

        String notes = item.getNotes();
        iNotes.setText(notes);


    }

}
