package com.sb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015-09-06.
 */
public class TodoListAdapter extends BaseAdapter {
    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");
    private final Context mContext;
    private Map<String, List<Todo>> mData= new HashMap<>();
    private Date mDate;

    public TodoListAdapter(Context context, HashMap<String, List<Todo>> data) {
        this.mContext= context;
        this.mData= data;
    }
    @Override
    public int getCount() {
        return mData.size(); }
    @Override
    public Object getItem(int position) { return position; }
    @Override
    public long getItemId(int position) { return position; }

    public void setDate(Date date) {
        mDate= date;
    }
    /**
     * Item's layout
     * @param position
     * @param convertView
     * @param parent
     * @return View
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        String TIMEFORMAT= "%5s시:%5s분";
        String TODOFORMST= "[%s]";

        ViewHolder viewHolder= null;



        // Layout compose
        // convertView first loaded
        if(convertView== null){
            convertView= LayoutInflater.from(
                    mContext).inflate(R.layout.todo_listview, parent, false);

            TextView time= (TextView)convertView.findViewById(R.id.time_text_view);
            TextView todo= (TextView)convertView.findViewById(R.id.todo_text_view);

            viewHolder= new ViewHolder();
            viewHolder.time= time;
            viewHolder.todo= todo;

            convertView.setTag(viewHolder);

        } else {// reuse convertView
            if(convertView.getTag()!= null)
                viewHolder= (ViewHolder)convertView.getTag();
        }

        // Bind data to Layout
        List<Todo> todothings= mData.get(formatter.format(mDate));
        if(todothings!= null) {
            for (int i = 0; i < todothings.size(); i++) {
                viewHolder.time.setText(
                        String.format(TIMEFORMAT,
                                todothings.get(i).getHour(), todothings.get(i).getMin()));
                viewHolder.todo.setText(
                        String.format(TODOFORMST, todothings.get(i).getTodo()));
            }
        } else {
            viewHolder.time.setText("");
            viewHolder.todo.setText("");
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
