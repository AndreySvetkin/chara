package com.example.chara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chara.R;
import com.example.chara.model.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Context context;
    private List<Employee> employees;
    private OnEmployeeClickListener onEmployeeClickListener;

    public EmployeeAdapter(Context context, List<Employee> employees, OnEmployeeClickListener onEmployeeClickListener) {
        this.context = context;
        this.employees = employees;
        this.onEmployeeClickListener = onEmployeeClickListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_human_layout, parent, false);
        return new EmployeeViewHolder(view, onEmployeeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.bind(employee);
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameTextView;
        private TextView positionTextView;
        private OnEmployeeClickListener onEmployeeClickListener;

        public EmployeeViewHolder(@NonNull View itemView, OnEmployeeClickListener onEmployeeClickListener) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textFIO);
            positionTextView = itemView.findViewById(R.id.textPosition);
            this.onEmployeeClickListener = onEmployeeClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Employee employee) {
            nameTextView.setText(employee.getName() + " " + employee.getSurname());
            if (employee.getPost() != null)
                positionTextView.setText(employee.getPost().getName());
        }

        @Override
        public void onClick(View v) {
            onEmployeeClickListener.onEmployeeClick(getAdapterPosition());
        }
    }

    public interface OnEmployeeClickListener {
        void onEmployeeClick(int position);
    }
}