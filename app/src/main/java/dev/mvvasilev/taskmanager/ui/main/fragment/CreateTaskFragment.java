package dev.mvvasilev.taskmanager.ui.main.fragment;

import android.content.Context;
import android.icu.text.RelativeDateTimeFormatter;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.chip.Chip;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import dev.mvvasilev.taskmanager.R;
import dev.mvvasilev.taskmanager.enums.TaskPriority;
import dev.mvvasilev.taskmanager.service.TaskService;

public class CreateTaskFragment extends Fragment {

    DateFormat sdf = SimpleDateFormat.getDateTimeInstance();

    private OnFragmentInteractionListener mListener;

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button createTaskButton = view.findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameInput = view.findViewById(R.id.taskNameInput);
                EditText descriptionInput = view.findViewById(R.id.descriptionInput);
                EditText startDateInput = view.findViewById(R.id.startDateInput);
                EditText dueDateInput = view.findViewById(R.id.dueDateInput);
                RadioButton highPriorityInput = view.findViewById(R.id.highPriorityRadioButton);
                RadioButton normalPriorityInput = view.findViewById(R.id.normalPriorityRadioButton);
                RadioButton lowPriorityInput = view.findViewById(R.id.lowPriorityRadioButton);
                Chip notificationInput = view.findViewById(R.id.notificationInput);

                try {
                    String name = nameInput.getText().toString();
                    String description = descriptionInput.getText().toString();
                    Date startDate = sdf.parse(startDateInput.getText().toString());
                    Date dueDate = sdf.parse(dueDateInput.getText().toString());
                    Boolean notifications = notificationInput.isChecked();

                    TaskPriority priority = TaskPriority.NO_PRIORITY;

                    if (highPriorityInput.isChecked()) {
                        priority = TaskPriority.HIGH;
                    } else if (normalPriorityInput.isChecked()) {
                        priority = TaskPriority.MEDIUM;
                    } else if (lowPriorityInput.isChecked()) {
                        priority = TaskPriority.LOW;
                    }

                    TaskService.getInstance().createTask(
                            getContext(),
                            name,
                            description,
                            startDate,
                            dueDate,
                            priority,
                            notifications
                    );
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
