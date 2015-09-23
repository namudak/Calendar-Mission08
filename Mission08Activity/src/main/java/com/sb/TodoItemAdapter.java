package com.sb;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2015-09-06.
 */
public class TodoItemAdapter extends BaseAdapter {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");
    private final Context mContext;
    private List<TodoItem> mTodos;

    public TodoItemAdapter(Context context, List<TodoItem> data) {
        this.mContext= context;
        this.mTodos= data;
    }
    @Override
    public int getCount() { return mTodos.size(); }
    @Override
    public Object getItem (int position) { return mTodos.get(position); }
    @Override
    public long getItemId(int position) { return position; }
    /**
     * Item's layout
     * @param position
     * @param convertView
     * @param parent
     * @return View
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        String TIMEFORMAT= "%s시:%s분";
        String TODOFORMST= "[%s]";

        TodoItemView itemView;

        TodoItem aItem= mTodos.get(position);

        if(convertView== null) {
            itemView= new TodoItemView(mContext, aItem);

        } else {
            itemView= (TodoItemView)convertView;
        }

        itemView.setTimeTextView(String.format(TIMEFORMAT, aItem.getHour(), aItem.getMin()));
        itemView.setTodoTextView(String.format(TODOFORMST, aItem.getTodo()));

        // Return view
        return itemView;
    }

}
