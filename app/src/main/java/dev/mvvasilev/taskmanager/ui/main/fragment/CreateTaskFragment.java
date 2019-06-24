package dev.mvvasilev.taskmanager.ui.main.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import dev.mvvasilev.taskmanager.R;
import dev.mvvasilev.taskmanager.enums.TaskPriority;
import dev.mvvasilev.taskmanager.service.TaskService;

public class CreateTaskFragment extends Fragment {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private OnFragmentInteractionListener mListener;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalTime startTime;

    private LocalDate dueDate;

    private LocalTime dueTime;

    private TaskPriority priority;

    private Boolean notifications;

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
    public void onViewCreated(@NonNull final View parentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(parentView, savedInstanceState);

        EditText nameInput = parentView.findViewById(R.id.taskNameInput);
        EditText descriptionInput = parentView.findViewById(R.id.descriptionInput);

        EditText startDateInput = parentView.findViewById(R.id.startDateInput);
        startDateInput.setFocusable(false);
        startDateInput.setOnClickListener(dateView -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    parentView.getContext(),
                    (dateSetView, year, month, dayOfMonth) -> {
                        startDate = LocalDate.of(year, month, dayOfMonth);
                        startDateInput.setText(DATE_FORMATTER.format(startDate));
                    },
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonth().ordinal(),
                    LocalDate.now().getDayOfMonth()
            );
            datePickerDialog.show();
        });

        EditText startTimeInput = parentView.findViewById(R.id.startTimeInput);
        startTimeInput.setFocusable(false);
        startTimeInput.setOnClickListener(startTimeView -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    parentView.getContext(),
                    (timeSetView, hourOfDay, minutes) -> {
                        startTime = LocalTime.of(hourOfDay, minutes);
                        startTimeInput.setText(TIME_FORMATTER.format(startTime));
                    },
                    0,
                    0,
                    true
            );
            timePickerDialog.show();
        });

        EditText dueDateInput = parentView.findViewById(R.id.dueDateInput);
        dueDateInput.setFocusable(false);
        dueDateInput.setOnClickListener(dateView -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    parentView.getContext(),
                    (dateSetView, year, month, dayOfMonth) -> {
                        dueDate = LocalDate.of(year, month, dayOfMonth);
                        dueDateInput.setText(DATE_FORMATTER.format(dueDate));
                    },
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonth().ordinal(),
                    LocalDate.now().getDayOfMonth()
            );
            datePickerDialog.show();
        });

        EditText dueTimeInput = parentView.findViewById(R.id.dueTimeInput);
        dueTimeInput.setFocusable(false);
        dueTimeInput.setOnClickListener(timeView -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    parentView.getContext(),
                    (timeSetView, hourOfDay, minutes) -> {
                        dueTime = LocalTime.of(hourOfDay, minutes);
                        dueTimeInput.setText(TIME_FORMATTER.format(dueTime));
                    },
                    0,
                    0,
                    true
            );
            timePickerDialog.show();
        });

        RadioButton highPriorityInput = parentView.findViewById(R.id.highPriorityRadioButton);
        RadioButton normalPriorityInput = parentView.findViewById(R.id.normalPriorityRadioButton);
        RadioButton lowPriorityInput = parentView.findViewById(R.id.lowPriorityRadioButton);
        RadioButton notificationInput = parentView.findViewById(R.id.notificationInput);

        final Button createTaskButton = parentView.findViewById(R.id.createTaskButton);
        createTaskButton.setOnClickListener(view -> {

            name = nameInput.getText().toString();
            description = descriptionInput.getText().toString();
            notifications = notificationInput.isChecked();

            priority = TaskPriority.NO_PRIORITY;

            if (highPriorityInput.isChecked()) {
                priority = TaskPriority.HIGH;
            } else if (normalPriorityInput.isChecked()) {
                priority = TaskPriority.MEDIUM;
            } else if (lowPriorityInput.isChecked()) {
                priority = TaskPriority.LOW;
            }

            if (name == null || name.isEmpty()) {
                createErrorDialog(parentView, "Name of task is empty").show();
                return;
            }

            if (description == null || description.isEmpty()) {
                createErrorDialog(parentView, "Description of task is empty").show();
                return;
            }

            if (startDate == null) {
                createErrorDialog(parentView, "Start date is empty").show();
                return;
            }

            if (startTime == null) {
                createErrorDialog(parentView, "Start time is empty").show();
                return;
            }

            if (dueDate == null) {
                createErrorDialog(parentView, "Due date is empty").show();
                return;
            }

            if (dueTime == null) {
                createErrorDialog(parentView, "Due time is empty").show();
                return;
            }

            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime dueDateTime = LocalDateTime.of(dueDate, dueTime);

            TaskService.getInstance().createTask(
                    getContext(),
                    name,
                    description,
                    startDateTime,
                    dueDateTime,
                    priority,
                    notifications
            );

            TabLayout tabhost = getActivity().findViewById(R.id.tabs);
            tabhost.getTabAt(0).select();
        });
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

    private Dialog createErrorDialog(View parentView, String error) {
        Dialog errorDialog = new Dialog(parentView.getContext());
        errorDialog.setTitle("Error");

        ConstraintLayout layout = new ConstraintLayout(errorDialog.getContext());
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);

        TextView errorTextView = new TextView(errorDialog.getContext());
        errorTextView.setText(error);
        errorTextView.setTextColor(Color.RED);

        layout.addView(errorTextView, layoutParams);

        errorDialog.setContentView(layout);

        return errorDialog;
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
