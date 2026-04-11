package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.data.CrewDatabase;
import com.example.spacecolony.model.CrewMember;
import java.util.ArrayList;

public class CrewMenu extends AppCompatActivity {
    private Button backButton;
    private ListView crewListView;

    private ArrayList<CrewMember> crewList;
    private ArrayList<String> displayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_menu);

        backButton = findViewById(R.id.backButton);
        crewListView = findViewById(R.id.crewListView);
        crewList = CrewDatabase.getInstance().getCrewList();
        displayList = new ArrayList<>();
        updateCrewDisplay();
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CrewMenu.this, MainMenu.class);
            startActivity(intent);
        });
    }

    private void updateCrewDisplay() {
        displayList.clear();

        for (CrewMember member : crewList) {
            String text = member.getName() + " (" +
                    member.getClass().getSimpleName() + ")\n" +
                    "Level: " + member.getLevel() + "\n" +
                    "Experience: " + member.getExperience() + "/" + member.getExperienceToNextLevel() + "\n" +
                    "Health: " + member.getHealth() + "/" + member.getMaxHealth() + "\n" +
                    "Energy: " + member.getEnergy() + "/" + member.getMaxEnergy();
            displayList.add(text);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        crewListView.setAdapter(adapter);
    }
}
