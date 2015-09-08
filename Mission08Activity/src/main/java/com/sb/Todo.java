package com.sb;

/**
 * Created by Administrator on 2015-09-06.
 */
public class Todo {
    String yearmonth;
    String hour;
    String min;
    String todo;

    public Todo() {}
    public Todo(String yearmonth, String hour, String min, String todo){
        this.yearmonth= yearmonth;
        this.hour= hour;
        this.min= min;
        this.todo= todo;
    }

    public String getYearMonth() {return yearmonth;}
    public String getHour() {return hour;}
    public String getMin() {return min;}
    public String getTodo() {return todo;}

    public void setYearmonth(String yearmonth){this.yearmonth= yearmonth;}
    public void setHour(String hour) {this.hour= hour;}
    public void setMin(String min) {this.min= min;}
    public void setTodo(String todo) {this.todo= todo;}
}
