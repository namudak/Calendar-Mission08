package com.sb;

import java.util.Date;

/**
 * Created by student on 2015-09-11.
 */
public class TodoItem {

    private Date date;
    private String weather;
    private String hour;
    private String min;
    private String todo;

    // Constructors
    public TodoItem() {}
    public TodoItem(Date date,
                    String hour, String min, String todo, String weather){
        this.date= date;
        this.hour= hour;
        this.min= min;
        this.todo= todo;
    }

    // Getters and Setters
    public Date getDate() {return date;}
    public String getWeather() {return weather;}
    public String getHour() {return hour;}
    public String getMin() {return min;}
    public String getTodo() {return todo;}

    public void setDate(Date date){this.date= date;}
    public void setWeather(String weather) {this.weather= weather;}
    public void setHour(String hour) {this.hour= hour;}
    public void setMin(String min) {this.min= min;}
    public void setTodo(String todo) {this.todo= todo;}


}
