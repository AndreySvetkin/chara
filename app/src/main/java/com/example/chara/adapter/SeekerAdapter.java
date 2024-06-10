package com.example.chara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chara.R;
import com.example.chara.model.Resume;

import java.util.List;

public class SeekerAdapter extends RecyclerView.Adapter<SeekerAdapter.SeekerViewHolder> {

    private Context context;
    private List<Resume> resumeList;
    private OnResumeClickListener onResumeClickListener;

    public SeekerAdapter(Context context, List<Resume> resumeList, OnResumeClickListener onResumeClickListener) {
        this.context = context;
        this.resumeList = resumeList;
        this.onResumeClickListener = onResumeClickListener;
    }

    @NonNull
    @Override
    public SeekerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_human_layout, parent, false);
        return new SeekerViewHolder(view, onResumeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SeekerViewHolder holder, int position) {
        Resume resume = resumeList.get(position);
        holder.bind(resume);
    }

    @Override
    public int getItemCount() {
        return resumeList.size();
    }

    public static class SeekerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imagePhoto;
        private TextView textFIO;
        private TextView textPosition;
        private OnResumeClickListener onResumeClickListener;

        public SeekerViewHolder(@NonNull View itemView, OnResumeClickListener onResumeClickListener) {
            super(itemView);
            imagePhoto = itemView.findViewById(R.id.imagePhoto);
            textFIO = itemView.findViewById(R.id.textFIO);
            textPosition = itemView.findViewById(R.id.textPosition);
            this.onResumeClickListener = onResumeClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Resume resume) {
            // imagePhoto.setImageResource(resume.getImageResourceId()); // Uncomment and modify if you have image resources
            textFIO.setText(resume.getFio());
            // textPosition.setText(resume.getPosition()); // Uncomment and modify if you have position data
        }

        @Override
        public void onClick(View v) {
            onResumeClickListener.onResumeClick(getAdapterPosition());
        }
    }

    public interface OnResumeClickListener {
        void onResumeClick(int position);
    }
}