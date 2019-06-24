package dev.mvvasilev.taskmanager.ui.main.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.Set;

import dev.mvvasilev.taskmanager.R;
import dev.mvvasilev.taskmanager.entity.Task;
import dev.mvvasilev.taskmanager.service.TaskService;

public class ListTasksFragment extends Fragment {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd:MM:YYYY HH:mm");

    private OnFragmentInteractionListener mListener;

    public ListTasksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshTasks(view);
    }

    public void refreshTasks(View view) {
        LinearLayout taskList = view.findViewById(R.id.taskListView);

        taskList.removeAllViews();

        Set<Task> tasks = TaskService.getInstance().getTasks(getContext());
        for (Task task : tasks) {
            CardView card = new CardView(getContext());

            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView name = new TextView(getContext());
            name.setText(task.getName());
            name.setTextSize(24);

            TextView description = new TextView(getContext());
            description.setText(task.getDescription());

            TextView startDatetime = new TextView(getContext());
            startDatetime.setText("Starts: " + DATE_TIME_FORMATTER.format(task.getStartDateTime()));

            TextView dueDatetime = new TextView(getContext());
            dueDatetime.setText("Ends: " + DATE_TIME_FORMATTER.format(task.getEndDateTime()));

            Button deleteButton = new Button(getContext());
            deleteButton.setText("Delete");
            deleteButton.setOnClickListener((v) -> {
                TaskService.getInstance().deleteTask(getContext(), task);
                taskList.removeView(card);
            });

            layout.addView(name);
            layout.addView(description);
            layout.addView(startDatetime);
            layout.addView(dueDatetime);
            layout.addView(deleteButton);

            card.addView(layout);
            taskList.addView(card);

            Space space = new Space(getContext());
            space.setMinimumHeight(15);
            taskList.addView(space);
        }
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
