package com.sb;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015-09-06.
 */
public class TodoDialogFragment extends DialogFragment implements View.OnClickListener {

    // "update" or "add"
    private String mMode;
    // functional view except mId
    private int mId;
    private EditText mTodo;
    private EditText mHour;
    private EditText mMin;
    private RadioGroup mWeather;

    // "save" "update"
    private Button mSaveButton;
    private Button mUpdateButton;

    public TodoDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.todoinput_dialog, container);

        Bundle bundle = getArguments();

        List<Object> objArray = (ArrayList<Object>) bundle.get("parm");
        mMode = (String) objArray.get(0);

        mTodo = (EditText) view.findViewById(R.id.todo_edit_text);
        mHour = (EditText) view.findViewById(R.id.hour_edit_text);
        mMin = (EditText) view.findViewById(R.id.min_edit_text);

        mWeather = (RadioGroup) view.findViewById(R.id.weather_RG);

        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(this);
        mUpdateButton = (Button) view.findViewById(R.id.update_button);
        mUpdateButton.setOnClickListener(this);

        if (mMode.equals("updateTodo")) {
            mId = ((TodoItem) objArray.get(2)).getId();
            mTodo.setText(((TodoItem) objArray.get(2)).getTodo());
            mHour.setText(((TodoItem) objArray.get(2)).getHour());
            mMin.setText(((TodoItem) objArray.get(2)).getMin());
            mWeather.check(Integer.parseInt(((TodoItem) objArray.get(2)).getWeather()));
            mSaveButton.setEnabled(false);
        } else if (mMode.equals("addTodo")) {
            mId = -1;
            mWeather.check(R.id.clear_RB);
            mUpdateButton.setEnabled(false);
        }

        // Remove dialog title and background
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Set Title as selected date
        //getDialog().getWindow().setTitle("");

        // Set listener for Radiogroup
        mWeather.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                group.check(checkedId);
            }
        });

        // ready for input
        mTodo.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                doAction(R.id.save_button);
                break;
            case R.id.update_button:
                doAction(R.id.update_button);
                break;
            default:
                dismiss();
                break;

        }
    }

    public interface TodoDialogFragmentListener {
        void onTodoDialogClick(DialogFragment dialog, String result);
    }

    TodoDialogFragmentListener todoDialogFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            todoDialogFragmentListener = (TodoDialogFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement TodoDialgoFragmentListener");
        }

    }

    public void doAction(int mode) {
        todoDialogFragmentListener.onTodoDialogClick(
                TodoDialogFragment.this,
                String.valueOf(mode) + "," +
                        String.valueOf(mId) + "," +
                        mHour.getText().toString() + "," +
                        mMin.getText().toString() + "," +
                        mTodo.getText().toString() + "," +
                        String.valueOf(mWeather.getCheckedRadioButtonId()
                        )

        );
    }
}
