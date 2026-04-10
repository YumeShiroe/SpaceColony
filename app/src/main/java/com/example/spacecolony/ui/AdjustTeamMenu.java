package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.spacecolony.R;
import com.example.spacecolony.data.CrewDatabase;
import com.example.spacecolony.model.CrewMember;
import java.util.ArrayList;


public class AdjustTeamMenu extends AppCompatActivity{
    private RecyclerView recyclerView;
    private TextView textSelectedTeam;
    private Button buttonAdjustTeam;
    private CrewDatabase crewDatabase;
    private ArrayList<CrewMember> crewList;
    private ArrayList<CrewMember> selectedCrew;
    private CrewAdapter crewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_team);

        recyclerView = findViewById(R.id.recyclerView);
        textSelectedTeam = findViewById(R.id.textSelectedTeam);
        buttonAdjustTeam = findViewById(R.id.buttonAdjustTeam);

        crewDatabase = new CrewDatabase();
        crewList = crewDatabase.getCrewList();
        selectedCrew = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        crewAdapter = new CrewAdapter(crewList, selectedCrew, new CrewAdapter.OnCrewSelectListener() {
            @Override
            public void onCrewSelectionChanged() {
                updateSelectedTeam();
            }

            @Override
            public void onLimitReached() {
                Toast.makeText(AdjustTeamMenu.this, "You can only select 2 crew members", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(crewAdapter);
        updateSelectedTeam();
        buttonAdjustTeam.setOnClickListener(v -> {
            if (selectedCrew.size() == 2) {
                Toast.makeText(AdjustTeamMenu.this, "Team Confirmed: " + selectedCrew.get(0).getName() + " and " + selectedCrew.get(1).getName(), Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("crew1", selectedCrew.get(0).getName());
                resultIntent.putExtra("crew2", selectedCrew.get(1).getName());
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(AdjustTeamMenu.this, "Please select EXACTLY 2 crew members", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateSelectedTeam() {
        textSelectedTeam.setText("Selected: " + selectedCrew.size() + "/2");
    }
}
