package com.sb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015-09-06.
 */
public class TodoListAdapter extends BaseAdapter {
    private final Context mContext;
    private Map<Date, List<Todo>> mData;
    private List<Todo> mTodos;
    private Date mSelectedDate;


    public TodoListAdapter(Context context, Map<Date, List<Todo>> data) {
        mContext= context;
        this.mData= data;
    }
    @Override
    public int getCount() { return mTodos.size(); }
    @Override
    public Object getItem(int position) {
        return mTodos.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDate(Date date){
        mSelectedDate= date;
        //if(mData!= null) mTodos= mData.get(mSelectedDate);
    }

    /**
     * Item's layout
     * @param position
     * @param convertView
     * @param parent
     * @return
     */

    public View getView(int position, View convertView, ViewGroup parent) {
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");
        ViewHolder viewHolder= null;
        String TIMEFORMAT= "%5s시:%5s분";
        String TODOFORMST= "[%s]";

        // Layout compose
        // convertView first loaded
        if(convertView== null){
            convertView= LayoutInflater.from(
                    mContext).inflate(R.layout.todo_listview, parent, false);

            TextView time= (TextView)convertView.findViewById(R.id.time_text_view);
            TextView todo= (TextView)convertView.findViewById(R.id.todo_text_view);

            List<Todo> dateTodo= mData.get(mSelectedDate);

            Todo todothings= (Todo)getItem(position);
            viewHolder= new ViewHolder();
            viewHolder.time= time;
            viewHolder.todo= todo;

            convertView.setTag(viewHolder);

        } else {// reuse convertView
            if(convertView.getTag()!= null)
                viewHolder= (ViewHolder)convertView.getTag();
        }

        // Bind data to Layout
        Todo todothings= (Todo) getItem(position);
        if(mSelectedDate.equals(todothings.getDate())) {
            viewHolder.time.setText(
                    String.format(TIMEFORMAT, todothings.getHour(), todothings.getMin()));
            viewHolder.todo.setText(
                    String.format(TODOFORMST, todothings.getTodo()));
        } else {
            viewHolder.time.setText(null);
            viewHolder.todo.setText(null);
        }

        // Return view
        return convertView;
    }
    // Inner class by static
    static class ViewHolder {
        TextView time;
        TextView todo;
    }

}
