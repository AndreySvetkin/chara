package com.example.chara.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chara.R;
import com.example.chara.model.Vacancy;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VacancyAdapter extends RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder> {

    private List<Vacancy> vacancyList;
    private Context context;
    private OnVacancyClickListener onVacancyClickListener;

    public VacancyAdapter(Context context, List<Vacancy> vacancyList, OnVacancyClickListener onVacancyClickListener) {
        this.context = context;
        this.vacancyList = vacancyList;
        this.onVacancyClickListener = onVacancyClickListener;
    }

    @Override
    public VacancyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vacancy_layout, parent, false);
        return new VacancyViewHolder(view, onVacancyClickListener);
    }

    @Override
    public void onBindViewHolder(VacancyViewHolder holder, int position) {
        Vacancy vacancy = vacancyList.get(position);
        holder.bind(vacancy);
    }

    @Override
    public int getItemCount() {
        return vacancyList.size();
    }

    public static class VacancyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vacancyTitle, vacancyDescription;
        OnVacancyClickListener onVacancyClickListener;

        public VacancyViewHolder(View itemView, OnVacancyClickListener onVacancyClickListener) {
            super(itemView);
            vacancyTitle = itemView.findViewById(R.id.vacancy_title);
            vacancyDescription = itemView.findViewById(R.id.vacancy_description);
            this.onVacancyClickListener = onVacancyClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Vacancy vacancy) {
            vacancyTitle.setText(vacancy.getName());
            vacancyDescription.setText(vacancy.getCircs());
        }

        @Override
        public void onClick(View v) {
            onVacancyClickListener.onVacancyClick(getAdapterPosition());
        }
    }

    public interface OnVacancyClickListener {
        void onVacancyClick(int position);
    }
}
