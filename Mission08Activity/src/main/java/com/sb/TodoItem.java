package com.sb;

import java.util.Date;

/**
 * Created by student on 2015-09-11.
 */
public class TodoItem {

    private String time;
    private String weather;
    private String hour;
    private String min;
    private String todo;

    // Constructors
    public TodoItem() {}
    public TodoItem(String time,
                    String hour, String min, String todo, String weather){
        this.time= time;
        this.hour= hour;
        this.min= min;
        this.todo= todo;
        this.weather= weather;

    }

    // Getters and Setters
    public String getTime() {return time;}
    public String getWeather() {return weather;}
    public String getHour() {return hour;}
    public String getMin() {return min;}
    public String getTodo() {return todo;}

    public void setTime(Date date){this.time= time;}
    public void setWeather(String weather) {this.weather= weather;}
    public void setHour(String hour) {this.hour= hour;}
    public void setMin(String min) {this.min= min;}
    public void setTodo(String todo) {this.todo= todo;}


}
