package com.example.spacecolony.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.R;
import com.example.spacecolony.model.CrewMember;
import java.util.ArrayList;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.StatisticsViewHolder> {
    private ArrayList<CrewMember> crewList;
    public StatisticsAdapter(ArrayList<CrewMember> crewList) {
        this.crewList = crewList;
    }

    @NonNull
    @Override
    public StatisticsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statistics, parent, false);
        return new StatisticsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticsViewHolder holder, int position) {
        CrewMember crewMember = crewList.get(position);

        holder.textMemberName.setText(crewMember.getName());
        holder.textMemberRole.setText(crewMember.getClass().getSimpleName());
        holder.textMemberStatistics.setText(crewMember.getStatistics());
    }
    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public static class StatisticsViewHolder extends RecyclerView.ViewHolder {
        TextView textMemberName;
        TextView textMemberRole;
        TextView textMemberStatistics;

        public StatisticsViewHolder(@NonNull View itemView) {
            super(itemView);
            textMemberName = itemView.findViewById(R.id.textMemberName);
            textMemberRole = itemView.findViewById(R.id.textMemberRole);
            textMemberStatistics = itemView.findViewById(R.id.textMemberStats);
        }
    }
}
