package com.sb;

/**
 * Created by student on 2015-09-11.
 */
public class TodoItem {
    private int _id;
    private String time;
    private String weather;
    private String hour;
    private String min;
    private String todo;

    // Constructors
    public TodoItem() {}
    public TodoItem(int id, String time,
                    String hour, String min, String todo, String weather){
        this._id= id;
        this.time= time;
        this.hour= hour;
        this.min= min;
        this.todo= todo;
        this.weather= weather;

    }

    // Getters and Setters
    public int getId() {return _id;}
    public String getTime() {return time;}
    public String getWeather() {return weather;}
    public String getHour() {return hour;}
    public String getMin() {return min;}
    public String getTodo() {return todo;}

    public void setId(int id) {this._id= id;}
    public void setTime(String time){this.time= time;}
    public void setWeather(String weather) {this.weather= weather;}
    public void setHour(String hour) {this.hour= hour;}
    public void setMin(String min) {this.min= min;}
    public void setTodo(String todo) {this.todo= todo;}


}
