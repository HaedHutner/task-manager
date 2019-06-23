package dev.mvvasilev.taskmanager.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import dev.mvvasilev.taskmanager.entity.Task;
import dev.mvvasilev.taskmanager.enums.TaskPriority;

public class TaskRepository extends SQLiteOpenHelper {

    private static final DateFormat DATE_FORMAT = SimpleDateFormat.getDateTimeInstance();

    private static final String SAVE_TASK = "" +
            "INSERT INTO tasks (name, description, startDate, dueDate, priority, notifications)" +
            "VALUES (?, ?, ?, ?, ?, ?);";

    private static final String UPDATE_TASK = "" +
            "UPDATE tasks SET" +
            "   name = ?," +
            "   description = ?," +
            "   startDate = ?," +
            "   dueDate = ?," +
            "   priority = ?," +
            "   notifications = ?" +
            "WHERE id = ?;";

    private static final String DELETE_TASK = "" +
            "DELETE FROM tasks WHERE id = ?;";

    private static final String FIND_TASK = "" +
            "SELECT * FROM tasks WHERE id = ?;";

    private static final String FIND_ALL_TASKS = "" +
            "SELECT * FROM tasks;";

    public TaskRepository(Context context) {
        super(context, "TaskDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("" +
                "CREATE TABLE IF NOT EXISTS tasks (" +
                "   id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "   name TEXT," +
                "   description TEXT," +
                "   startDate TEXT," +
                "   dueDate TEXT," +
                "   priority TEXT," +
                "   notifications INTEGER" +
                ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveTask(Task task) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            try (SQLiteStatement statement = db.compileStatement(SAVE_TASK)) {
                statement.bindString(1, task.getName());
                statement.bindString(2, task.getDescription());
                statement.bindString(3, DATE_FORMAT.format(task.getStartDateTime()));
                statement.bindString(4, DATE_FORMAT.format(task.getEndDateTime()));
                statement.bindString(5, task.getTaskPriority().toString());
                statement.bindLong(6, task.areNotificationsEnabled() ? 1 : 0);

                task.setId(statement.executeInsert());
            }
        }
    }

    public void updateTask(Task task) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            try (SQLiteStatement statement = db.compileStatement(UPDATE_TASK)) {
                statement.bindString(1, task.getName());
                statement.bindString(2, task.getDescription());
                statement.bindString(3, DATE_FORMAT.format(task.getStartDateTime()));
                statement.bindString(4, DATE_FORMAT.format(task.getEndDateTime()));
                statement.bindString(5, task.getTaskPriority().toString());
                statement.bindLong(6, task.areNotificationsEnabled() ? 1 : 0);
                statement.bindLong(7, task.getId());

                statement.executeUpdateDelete();
            }
        }
    }

    public void deleteTaskById(Long id) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            try (SQLiteStatement statement = db.compileStatement(DELETE_TASK)) {
                statement.bindLong(1, id);
                statement.executeUpdateDelete();
            }
        }
    }

    public Task findTaskById(Long id) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            Cursor cursor = db.rawQuery(FIND_TASK, new String[]{id.toString()});

            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToNext();

            Task task = createTaskFromCursor(cursor);

            cursor.close();

            return task;
        }
    }

    public Set<Task> getAllTasks() {
        try (SQLiteDatabase db = getWritableDatabase()) {
            Cursor cursor = db.rawQuery(FIND_ALL_TASKS, null);

            if (cursor.getCount() == 0) {
                return new HashSet<>();
            }

            Set<Task> results = new HashSet<>();

            while (cursor.moveToNext()) {
                results.add(createTaskFromCursor(cursor));
            }

            cursor.close();

            return results;
        }
    }

    private Task createTaskFromCursor(Cursor cursor) {
        Task task = new Task();

        task.setId(cursor.getLong(0));
        task.setName(cursor.getString(1));
        task.setDescription(cursor.getString(2));

        try {
            task.setStartDateTime(DATE_FORMAT.parse(cursor.getString(3)));
            task.setEndDateTime(DATE_FORMAT.parse(cursor.getString(4)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        task.setTaskPriority(TaskPriority.valueOf(cursor.getString(5)));
        task.setNotificationsEnabled(cursor.getLong(6) == 1);

        return task;
    }
}
