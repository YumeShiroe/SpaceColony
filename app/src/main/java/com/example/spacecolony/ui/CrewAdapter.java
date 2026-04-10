package com.example.spacecolony.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.R;
import java.util.ArrayList;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {
    private ArrayList<CrewMember> crewList;
    private ArrayList<CrewMember> selectedCrew;
    private OnCrewSelectListener listener;

    public interface OnCrewSelectListener {
        void onCrewSelectionChanged();
        void onLimitReached();
    }

    public CrewAdapter(ArrayList<CrewMember> crewList, ArrayList<CrewMember> selectedCrew, OnCrewSelectListener listener) {
        this.crewList = crewList;
        this.selectedCrew = selectedCrew;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crew, parent, false);
        return new CrewViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CrewViewHolder holder, int position) {
        CrewMember crewMember = crewList.get(position);
        holder.textCrewName.setText(crewMember.getName());
        holder.textCrewRole.setText(crewMember.getClass().getSimpleName());

        if (selectedCrew.contains(crewMember)) {
            holder.buttonSelect.setText("Deselect");
        } else {
            holder.buttonSelect.setText("Select");
        }

        holder.buttonSelect.setOnClickListener(v -> {
            if (selectedCrew.contains(crewMember)) {
                selectedCrew.remove(crewMember);
                listener.onCrewSelectionChanged();
                notifyItemChanged(position);
            } else {
                if (selectedCrew.size() < 2) {
                    selectedCrew.add(crewMember);
                    listener.onCrewSelectionChanged();
                    notifyItemChanged(position);
                } else {
                    listener.onLimitReached();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    public static class CrewViewHolder extends RecyclerView.ViewHolder {
        TextView textCrewName;
        TextView textCrewRole;
        Button buttonSelect;

        public CrewViewHolder(@NonNull View itemView) {
            super(itemView);
            textCrewName = itemView.findViewById(R.id.textCrewName);
            textCrewRole = itemView.findViewById(R.id.textCrewRole);
            buttonSelect = itemView.findViewById(R.id.buttonSelectCrew);
        }
    }
}
