package com.example.simpletodo;

public class Item {
    private String priority, task, date, time, notes;

    public Item(){

    }

    public Item(String task, String notes, String date, String time, String priority){
        this.priority = priority;
        this.task = task;
        this.date = date;
        this.time = time;
        this.notes = notes;
    }

    public void setPriority(String priority){
        this.priority = priority;
    }

    public String getPriority(){
        return priority;
    }

    public void setTodo(String task){
        this.task = task;
    }

    public String getTask(){
        return task;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public String getNotes(){
        return notes;
    }

}
