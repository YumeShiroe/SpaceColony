package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.data.CrewDatabase;
import java.util.ArrayList;
import java.util.Random;

public class SimulatorMenu extends AppCompatActivity {
    private ListView crewListView;
    private ArrayList<CrewMember> crewList;
    private ArrayList<String> displayList;
    private ArrayAdapter<String> adapter;
    private Random random;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator_menu);

        crewListView = findViewById(R.id.crewListView);
        crewList = CrewDatabase.getInstance().getCrewList();
        displayList = new ArrayList<>();
        updateCrewDisplay();
        backButton = findViewById(R.id.backButton);

        crewListView.setOnItemClickListener((parent, view, position, id) -> {
            CrewMember selectedCrewMember = crewList.get(position);

            selectedCrewMember.gainExperience(10);

            Toast.makeText(SimulatorMenu.this, selectedCrewMember.getName() + " gained 10 XP! Yippie!", Toast.LENGTH_SHORT).show();
            updateCrewDisplay();
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SimulatorMenu.this, MainMenu.class);
            startActivity(intent);
        });
    }
    private void updateCrewDisplay() {
        displayList.clear();
        for (CrewMember crewMember : crewList) {
            String text =
                    crewMember.getName() + " - Level: " +
                    crewMember.getLevel() + " - XP: " +
                    crewMember.getExperience() + " / " +
                    crewMember.getExperienceToNextLevel();
            displayList.add(text);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        crewListView.setAdapter(adapter);
    }
}
