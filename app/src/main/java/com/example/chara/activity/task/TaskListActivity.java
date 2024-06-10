package com.example.chara.activity.task;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chara.R;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    private ListView listViewTasks;
    private Button buttonAddTask;
    private List<Task> taskList;
    private TaskAdapter adapter;
    private SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "taskPrefs";
    public static final String TASKS_KEY = "tasks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tasks);
        listViewTasks = findViewById(R.id.listViewTasks);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        taskList = loadTasks();
        adapter = new TaskAdapter(this, taskList, sharedPreferences);
        listViewTasks.setAdapter(adapter);

        buttonAddTask.setOnClickListener(v -> showAddTaskDialog());
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить задачу");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Добавить", (dialog, which) -> {
            String taskText = input.getText().toString();
            if (!taskText.isEmpty()) {
                Task task = new Task(taskText, false);
                taskList.add(task);
                adapter.notifyDataSetChanged();
                saveTasks();
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        String tasksJson = sharedPreferences.getString(TASKS_KEY, "[]");
        try {
            JSONArray jsonArray = new JSONArray(tasksJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                String taskText = jsonArray.getJSONObject(i).getString("text");
                boolean isCompleted = jsonArray.getJSONObject(i).getBoolean("completed");
                tasks.add(new Task(taskText, isCompleted));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private void saveTasks() {
        JSONArray jsonArray = new JSONArray();
        for (Task task : taskList) {
            jsonArray.put(task.toJson());
        }
        sharedPreferences.edit().putString(TASKS_KEY, jsonArray.toString()).apply();
    }

    @Override
    public void onBackPressed() {
        List<Task> pendingTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (!task.isCompleted()) {
                pendingTasks.add(task);
            }
        }
        taskList.clear();
        taskList.addAll(pendingTasks);
        saveTasks();
        super.onBackPressed();
    }
}