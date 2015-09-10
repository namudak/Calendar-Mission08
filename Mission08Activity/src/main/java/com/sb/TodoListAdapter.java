package com.sb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2015-09-06.
 */
public class TodoListAdapter extends BaseAdapter {
    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");
    private final Context mContext;
    private List<Todo> mTodos;

    public TodoListAdapter(Context context, List<Todo>data) {
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
        Todo todothings= (Todo) getItem(position);
        if(todothings!= null) {
            viewHolder.time.setText(
                    String.format(TIMEFORMAT,
                            todothings.getHour(), todothings.getMin()));
            viewHolder.todo.setText(
                    String.format(TODOFORMST, todothings.getTodo()));
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
