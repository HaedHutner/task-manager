package dev.mvvasilev.taskmanager.service;

import android.content.Context;

import java.util.Date;

import dev.mvvasilev.taskmanager.entity.Task;
import dev.mvvasilev.taskmanager.enums.TaskPriority;
import dev.mvvasilev.taskmanager.repository.TaskRepository;

public class TaskService {

    private static TaskService instance;

    private TaskService() {
    }

    public void createTask(
            Context context,
            String name,
            String description,
            Date startDate,
            Date dueDate,
            TaskPriority priority,
            Boolean enableNotifications
    ) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setStartDateTime(startDate);
        task.setEndDateTime(dueDate);
        task.setTaskPriority(priority);
        task.setNotificationsEnabled(enableNotifications);

        new TaskRepository(context).saveTask(task);
    }

    public static TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }

        return instance;
    }
}
