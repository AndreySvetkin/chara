package com.example.chara.activity.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.CompoundButton;

import com.example.chara.R;

import org.json.JSONArray;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private List<Task> tasks;
    private SharedPreferences sharedPreferences;

    public TaskAdapter(Context context, List<Task> tasks, SharedPreferences sharedPreferences) {
        super(context, 0, tasks);
        this.tasks = tasks;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task_layout, parent, false);
        }

        Task task = tasks.get(position);
        CheckBox checkBox = convertView.findViewById(R.id.checkBoxTask);
        TextView textView = convertView.findViewById(R.id.textViewTask);

        checkBox.setOnCheckedChangeListener(null);  // Reset the listener to prevent unwanted triggering
        checkBox.setChecked(task.isCompleted());
        updateTaskAppearance(task, textView);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            updateTaskAppearance(task, textView);
            saveTasks();
        });

        textView.setText(task.getText());

        return convertView;
    }

    private void updateTaskAppearance(Task task, TextView textView) {
        if (task.isCompleted()) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    private void saveTasks() {
        JSONArray jsonArray = new JSONArray();
        for (Task task : tasks) {
            jsonArray.put(task.toJson());
        }
        sharedPreferences.edit().putString(TaskListActivity.TASKS_KEY, jsonArray.toString()).apply();
    }
}