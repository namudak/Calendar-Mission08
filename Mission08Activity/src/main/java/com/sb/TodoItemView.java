package com.sb;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by student on 2015-09-11.
 */
public class TodoItemView extends LinearLayout {

    private TextView mTimeTextView;
    private TextView mTodoTextView;

    public TodoItemView(Context context) {
        super(context);
    }

    public TodoItemView(Context context, TodoItem aItem) {
        super(context);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.todoitem_listview, this, true);

        mTimeTextView = (TextView) findViewById(R.id.time_text_view);
        mTimeTextView.setText(aItem.getHour() + ":" + aItem.getMin());
        mTodoTextView = (TextView) findViewById(R.id.todo_text_view);
        mTodoTextView.setText(aItem.getTodo());
    }

    // Set time editview as customed
    public void setTimeTextView(String time) {
        mTimeTextView.setText(time);
    }

    // set todo editview as customed
    public void setTodoTextView(String todo) {
        mTodoTextView.setText(todo);
    }

}
