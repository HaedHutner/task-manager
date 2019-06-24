package dev.mvvasilev.taskmanager.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import dev.mvvasilev.taskmanager.enums.TaskPriority;

public class Task {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private TaskPriority taskPriority;

    private boolean notificationsEnabled;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public boolean areNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return notificationsEnabled == task.notificationsEnabled &&
                Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(startDateTime, task.startDateTime) &&
                Objects.equals(endDateTime, task.endDateTime) &&
                taskPriority == task.taskPriority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, startDateTime, endDateTime, taskPriority, notificationsEnabled);
    }
}
