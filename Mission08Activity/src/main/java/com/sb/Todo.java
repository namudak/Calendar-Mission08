package com.sb;

import java.util.Date;

/**
 * Created by Administrator on 2015-09-06.
 */
public class Todo {
    Date date;
    String hour;
    String min;
    String todo;

    public Todo() {}
    public Todo(Date date, String hour, String min, String todo){
        this.date= date;
        this.hour= hour;
        this.min= min;
        this.todo= todo;
    }

    public Date getDate() {return date;}
    public String getHour() {return hour;}
    public String getMin() {return min;}
    public String getTodo() {return todo;}

    public void setDate(String yearmonth){this.date= date;}
    public void setHour(String hour) {this.hour= hour;}
    public void setMin(String min) {this.min= min;}
    public void setTodo(String todo) {this.todo= todo;}
}
