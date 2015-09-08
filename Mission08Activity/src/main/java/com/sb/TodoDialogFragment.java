package com.sb;

//import android.app.DialogFragment;

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

/**
 * Created by Administrator on 2015-09-06.
 */
public class TodoDialogFragment extends DialogFragment implements View.OnClickListener {

    private Button mSaveButton;
    private Button mCloseButton;
    private EditText mTodo;
    private EditText mHour;
    private EditText mMin;

    public TodoDialogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.todo_dialog, container);

        mTodo = (EditText)view.findViewById(R.id.todo_edit_text);
        mHour = (EditText)view.findViewById(R.id.hour_edit_text);
        mMin = (EditText)view.findViewById(R.id.min_edit_text);


        mSaveButton = (Button)view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(this);
        mCloseButton = (Button)view.findViewById(R.id.close_button);
        mCloseButton.setOnClickListener(this);

        // Remove dialog title and background
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // ready for input
        mTodo.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_button:
                doAction();
                break;
            case R.id.close_button:
                dismiss();
                break;
        }
    }

    public interface TodoDialogFragmentListener {
        public void onTodoDialogClick(DialogFragment dialog, String result);
    }

    TodoDialogFragmentListener todoDialogFragmentListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

        try {
            todoDialogFragmentListener= (TodoDialogFragmentListener)activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() +
                    " must implement TodoDialgoFragmentListener");
        }

    }

    public void doAction() {
        todoDialogFragmentListener.onTodoDialogClick(
                TodoDialogFragment.this,
                mHour.getText().toString()+ ","+
                        mMin.getText().toString()+ ","+
                        mTodo.getText().toString()
        );
    }
}
