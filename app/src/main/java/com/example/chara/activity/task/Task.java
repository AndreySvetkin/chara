package com.example.chara.activity.task;
import org.json.JSONException;
import org.json.JSONObject;

public class Task {

    private String text;
    private boolean completed;

    public Task(String text, boolean completed) {
        this.text = text;
        this.completed = completed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("text", text);
            jsonObject.put("completed", completed);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
