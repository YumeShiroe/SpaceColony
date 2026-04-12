package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.data.CrewDatabase;
import java.util.ArrayList;
import java.util.Random;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


public class SimulatorMenu extends AppCompatActivity {
    private ArrayList<CrewMember> crewList;
    private Random random;
    private Button backButton;
    private CrewDatabase crewDatabase;
    private TextView textCredits;
    private CrewAdapter crewAdapter;
    private RecyclerView crewRecyclerView;
    private int trainingCost = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator_menu);

        backButton = findViewById(R.id.backButton);
        textCredits = findViewById(R.id.textCredits);

        crewRecyclerView = findViewById(R.id.crewRecyclerView);
        crewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        crewDatabase = CrewDatabase.getInstance();
        crewList = crewDatabase.getCrewList();

        crewAdapter = new CrewAdapter(crewList, new ArrayList<>(), new CrewAdapter.OnCrewSelectListener() {
            @Override
            public void onCrewSelectionChanged() {
            }
            @Override
            public void onLimitReached() {
            }
            @Override
            public void onItemClick(CrewMember crewMember) {
                if (crewMember.isDefeated()) {
                    Toast.makeText(SimulatorMenu.this, "Crew member defeated!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (crewDatabase.spendCredits(trainingCost)) {
                    crewMember.gainExperience(10);
                    Toast.makeText(SimulatorMenu.this, "Crew member trained!", Toast.LENGTH_SHORT).show();
                    updateCreditDisplay();
                    crewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SimulatorMenu.this, "Not enough credits!", Toast.LENGTH_SHORT).show();
                }
            }
        }, false, true);

        crewRecyclerView.setAdapter(crewAdapter);
        updateCreditDisplay();

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SimulatorMenu.this, MainMenu.class);
            startActivity(intent);
        });
    }

    private void updateCreditDisplay() {
        int credits = crewDatabase.getCredits();
        textCredits.setText("Credits: " + credits + " (Training costs 100 Credits per time)");
    }
}
