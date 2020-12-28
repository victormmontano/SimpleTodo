package com.example.simpletodo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

   /* public interface OnClickListener{
        void onItemClicked(int position);
    }


    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }*/


    List<Item> items;
    ItemLongClickListener longClickListener;
    ItemClickListener clickListener;

    public ItemAdapter(List<Item> items, ItemClickListener clickListener, ItemLongClickListener longClickListener){
        this.items = items;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemHolder(todoView, clickListener, longClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        //String item = items.get(position);
        Log.d("POSITION", Integer.toString(position));
       // System.out.println(items.get(position));
        holder.bind(items.get(position));
        /*String notes = items.get(position+1);
        item.setNotes(notes);
        String date = items.get(position+2);
        item.setDate(date);
        String time = items.get(position+3);
        item.setTime(time);
        String priority = items.get(position+4);
        item.setPriority(priority);
        holder.bind(item);*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /*

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvItem  = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item){
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }*/
}
