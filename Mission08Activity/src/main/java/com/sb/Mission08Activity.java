package com.sb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.sb.database.contract.DbContract;
import com.sb.database.helper.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressLint("SimpleDateFormat")
public class Mission08Activity extends AppCompatActivity
                                implements TodoDialogFragment.TodoDialogFragmentListener {

    private CaldroidFragment caldroidFragment;

    private DbHelper mDbHelper;

    private Map<String, List<TodoItem>> mData;
    private List<TodoItem> mTodos;

    private ListView mTodoListView;
    private TodoItemAdapter mTodoAdapter;

    private Date mDate;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();

        // Setup arguments
        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();

            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));

            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this line to use dark theme
            //args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);//

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener= new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                // Set member fileds as selected date
                mDate= date;
                String keyDate= formatter.format(mDate);

                // Get a record from database on date
                ContentValues values= new ContentValues();
                values.put("Mode", "Todo");
                values.put(DbContract.UserEntry.COLUMN_NAME_TIME, keyDate);

                List parm= new ArrayList();
                Cursor cursor= mDbHelper.query(values);
                if(cursor!= null) {
                    parm.add("update");
                    cursor.moveToFirst();
                    parm.add(cursor.getString(cursor.getColumnIndex(DbContract.UserEntry.COLUMN_NAME_TODO)));
                    parm.add(cursor.getString(cursor.getColumnIndex(DbContract.UserEntry.COLUMN_NAME_HOUR)));
                    parm.add(cursor.getString(cursor.getColumnIndex(DbContract.UserEntry.COLUMN_NAME_MIN)));
                    parm.add(cursor.getString(cursor.getColumnIndex(DbContract.UserEntry.COLUMN_NAME_WEATHER)));
                    showDialog(parm);
                    Log.d("onSelectDate", "Query Ok.");
                } else {
                    parm.add("add");
                    showDialog(parm);
                    Log.d("onSelectDate", "Query Not Ok.");
                }

                // Refresh listview data
                if(mData.size()> 0) {
                    mTodos = mData.get(formatter.format(mDate));

                    if(mTodos== null) mTodos= new ArrayList<>();

                    mTodoAdapter = new TodoItemAdapter(getApplicationContext(), mTodos);
                    mTodoListView.setAdapter(mTodoAdapter);
                }

            }

            @Override
            public void onChangeMonth(int month, int year) {
                setWeekEndColor(month, year);
            }

            @Override
            public void onLongClickDate(Date date, View view) { }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
                //setWeekEndColor(0, 0);
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        // Set listview and adapter as linked
        mData= new HashMap<>();
        mTodos= new ArrayList<>();

        mTodoListView= (ListView)findViewById(R.id.todo_list_view);
        mTodoAdapter= new TodoItemAdapter(getApplicationContext(), mTodos);
        mTodoListView.setAdapter(mTodoAdapter);

        mDbHelper= new DbHelper(getApplicationContext());

    }

    /**
     * Set color for weekend
     * @param month
     * @param year
     */
    private void setWeekEndColor(int month, int year) {
        // Set color as blue and red for saturday, sunday respectively
        Calendar cal= Calendar.getInstance();

        if((month+ year)== 0) {
            month = cal.get(Calendar.MONTH);
            year = cal.get(Calendar.YEAR);
        }
        cal.set(year, month- 1, 1);


        while (cal.get(Calendar.MONTH)== (month- 1)) {
            if( cal.get(Calendar.DAY_OF_WEEK)% 7== 1 ) {// Sunday
                Date redDate= cal.getTime();
                caldroidFragment.setTextColorForDate(R.color.caldroid_light_red, redDate);
            } else if ( cal.get(Calendar.DAY_OF_WEEK)% 7== 0 ) {// Saturday
                Date blueDate= cal.getTime();
                caldroidFragment.setTextColorForDate(R.color.caldroid_sky_blue, blueDate);
            }
            cal.add(Calendar.DATE, 1);
        }
    }

    /**
     *  Initialize operation
     */
    private void setCustomResourceForDates() {

        if (caldroidFragment != null) {
            // Initialize calendar
            caldroidFragment.clearDisableDates();
            caldroidFragment.clearSelectedDates();
            caldroidFragment.setMinDate(null);
            caldroidFragment.setMaxDate(null);
            caldroidFragment.refreshView();
        }

//        Calendar cal = Calendar.getInstance();
//        // Default as today
//        mDate= cal.getTime();
//
//        // Display current month
//        setWeekEndColor(Calendar.MONTH, Calendar.YEAR);

    }

    /**
     * Show dialog for data input
     */
    private void showDialog(List mode) {
        Bundle bundle= new Bundle();
        bundle.putStringArrayList("parm", (ArrayList<String>) mode);

        // Show dialog for todo data input
        FragmentManager fm= getSupportFragmentManager();
        DialogFragment todoDialogFragment = new TodoDialogFragment();
        todoDialogFragment.setArguments(bundle);
        todoDialogFragment.show(fm, "Todo Dialog");
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id== R.id.action_add_todo){
            // Show input dialog
            List parm= new ArrayList();
            parm.add("add");
            showDialog(parm);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTodoDialogClick(DialogFragment dialog, String result) {
        String keyDate= formatter.format(mDate);
        String[] str= result.split(",");

        ContentValues values = new ContentValues();
        values.put(DbContract.UserEntry.COLUMN_NAME_TIME, keyDate);
        values.put(DbContract.UserEntry.COLUMN_NAME_WEATHER, str[1]);
        values.put(DbContract.UserEntry.COLUMN_NAME_HOUR, str[2]);
        values.put(DbContract.UserEntry.COLUMN_NAME_MIN, str[3]);
        values.put(DbContract.UserEntry.COLUMN_NAME_TODO, str[4]);
        if(Integer.parseInt(str[0])== R.id.save_button) {
            // Insert record data from dialog input;
            if (mDbHelper.insert(values) != -1) {
                Log.d("onTodoDialogClick", "Insert Ok.");
            } else {
                Log.d("onTodoDialogClick", "Insert Not Ok.");
            }
        } else {
            if(mDbHelper.update(values)!= 0){
                Log.d("onTodoDialogClick", "Update Ok.");
            } else {
                Log.d("onTodoDialogClick", "Update Not Ok.");
            }
        }

        // Todo hour, min, todo content
        TodoItem todo= new TodoItem(mDate, str[0], str[1], str[2], str[3]);
        // No todo at this date
        mTodos= new ArrayList<>();
        if(mData.get(keyDate)!= null){
            mTodos= mData.get(keyDate);
        }
        mTodos.add(todo);
        mData.put(keyDate, mTodos);

        // List view operation
        mTodoAdapter= new TodoItemAdapter(getApplicationContext(), mTodos);
        mTodoListView.setAdapter(mTodoAdapter);

        // Refresh listview data
        mTodoAdapter.notifyDataSetChanged();
        // Set cell's color as selected
        caldroidFragment.setBackgroundResourceForDate(R.color.caldroid_light_red, mDate);
        caldroidFragment.refreshView();
    }
}
