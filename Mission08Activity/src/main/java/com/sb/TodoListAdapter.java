package com.sb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2015-09-06.
 */
public class TodoListAdapter extends BaseAdapter {
    private final Context mContext;
    private List<Todo> mData;


    public TodoListAdapter(Context context, List<Todo> mData) {
        mContext= context;
        this.mData = mData;
    }
    @Override
    public int getCount() { return mData.size(); }
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Item's layout
     * @param position
     * @param convertView
     * @param parent
     * @return
     */

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // Layout compose
        // convertView first loaded
        if(convertView== null){
            convertView= LayoutInflater.from(
                    mContext).inflate(R.layout.todo_listview, parent, false);

            TextView yearmonth= (TextView)convertView.findViewById(R.id.yearmonth_text_view);
            TextView time= (TextView)convertView.findViewById(R.id.time_text_view);
            TextView todo= (TextView)convertView.findViewById(R.id.todo_text_view);

            Todo todothings= (Todo)getItem(position);
            viewHolder= new ViewHolder();
            viewHolder.yearmonth= yearmonth;
            viewHolder.time= time;
            viewHolder.todo= todo;

            convertView.setTag(viewHolder);

        } else {// reuse convertView
            viewHolder= (ViewHolder)convertView.getTag();
        }

        // Bind data to Layout
        Todo todothings= (Todo)getItem(position);
        viewHolder.yearmonth.setText(todothings.getYearMonth());
        viewHolder.time.setText(todothings.getHour()+ ":"+ todothings.getMin());
        viewHolder.todo.setText(todothings.getTodo());

        // Return view
        return convertView;
    }
    // Inner class by static
    static class ViewHolder {
        TextView yearmonth;
        TextView time;
        TextView todo;
    }

}
