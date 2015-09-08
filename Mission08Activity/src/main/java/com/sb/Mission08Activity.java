package com.sb;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@SuppressLint("SimpleDateFormat")
public class Mission08Activity extends AppCompatActivity
        implements TodoDialogFragment.TodoDialogFragmentListener {

    private CaldroidFragment caldroidFragment;

    private List<Todo> mData;
    private ListView mTodoListView;
    private TodoListAdapter mTodoAdapter;

    private String mCurYear, mCurMonth, mCurDate;

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();

        if (caldroidFragment != null) {
            caldroidFragment.setBackgroundResourceForDate(R.color.blue,
                    blueDate);
            caldroidFragment.setBackgroundResourceForDate(R.color.green,
                    greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);

            // Initialize calendar
            caldroidFragment.clearDisableDates();
            caldroidFragment.clearSelectedDates();
            caldroidFragment.setMinDate(null);
            caldroidFragment.setMaxDate(null);
//            caldroidFragment.setShowNavigationArrows(true);
//            caldroidFragment.setEnableSwipe(true);

            caldroidFragment.refreshView();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();

        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();//

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

            // Uncomment this to customize startDayOfWeek
            // args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
            // CaldroidFragment.TUESDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);//

            caldroidFragment.setArguments(args);
        }

        setCustomResourceForDates();

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                String[] str= formatter.format(date).split(" ");
                Toast.makeText(getApplicationContext(), formatter.format(date),
                        Toast.LENGTH_SHORT).show();


                caldroidFragment.setBackgroundResourceForDate(R.color.caldroid_sky_blue, date);
                caldroidFragment.refreshView();

                // Set member fileds as selected date
                mCurDate= str[0]+ "일";
                mCurMonth= str[1];
                mCurYear= str[2]+ "년";
                // Show dialog for todo data input
                FragmentManager fm= getSupportFragmentManager();
                DialogFragment todoDialogFragment = new TodoDialogFragment();
                todoDialogFragment.show(fm, "Todo Dialog");
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    Toast.makeText(getApplicationContext(),
                            "Caldroid view is created", Toast.LENGTH_SHORT)
                            .show();
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);

        // Set listview and adapter as linked
        mData= new ArrayList<>();

        mTodoListView= (ListView)findViewById(R.id.todo_list_view);
        mTodoAdapter= new TodoListAdapter(getApplicationContext(), mData);

        mTodoListView.setAdapter((TodoListAdapter)mTodoAdapter);
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
            FragmentManager fm= getSupportFragmentManager();
            DialogFragment todoDialogFragment = new TodoDialogFragment();
            todoDialogFragment.show(fm, "Todo Dialog");
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTodoDialogClick(DialogFragment dialog, String result) {
        String[] str= result.split(",");
        Todo todo= new Todo(mCurYear+ mCurMonth+ mCurDate,
                str[0]+ "시", str[1]+ "분", "["+ str[2]+ "]");
        mData.add(todo);
        // Refresh listview data
        mTodoAdapter.notifyDataSetChanged();
    }
}
