package com.example.chara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chara.R;
import com.example.chara.model.Interview;

import java.util.List;

public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder> {

    private List<Interview> interviewList;
    private Context context;
    private OnInterviewClickListener onInterviewClickListener;

    public InterviewAdapter(Context context, List<Interview> interviewList, OnInterviewClickListener onInterviewClickListener) {
        this.context = context;
        this.interviewList = interviewList;
        this.onInterviewClickListener = onInterviewClickListener;
    }

    @Override
    public InterviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_interview_layout, parent, false);
        return new InterviewViewHolder(view, onInterviewClickListener);
    }

    @Override
    public void onBindViewHolder(InterviewViewHolder holder, int position) {
        Interview interview = interviewList.get(position);
        holder.bind(interview);
    }

    @Override
    public int getItemCount() {
        return interviewList.size();
    }

    public static class InterviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView candidateName, position, interviewDateTime, interviewerName;
        OnInterviewClickListener onInterviewClickListener;

        public InterviewViewHolder(View itemView, OnInterviewClickListener onInterviewClickListener) {
            super(itemView);
            candidateName = itemView.findViewById(R.id.candidate_name);
            position = itemView.findViewById(R.id.position);
            interviewDateTime = itemView.findViewById(R.id.interview_date_time);
            this.onInterviewClickListener = onInterviewClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Interview interview) {
            candidateName.setText(interview.getResume().getFio());
            position.setText(interview.getId());
            interviewDateTime.setText(interview.getDate() + "");
        }

        @Override
        public void onClick(View v) {
            onInterviewClickListener.onInterviewClick(getAdapterPosition());
        }
    }

    public interface OnInterviewClickListener {
        void onInterviewClick(int position);
    }
}