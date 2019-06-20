package dev.mvvasilev.taskmanager.service;

public class TaskService {

    private static TaskService instance;

    private TaskService() {}

    public static TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }

        return instance;
    }
}
