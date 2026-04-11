package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.data.CrewDatabase;
import com.example.spacecolony.model.CrewMember;
import java.util.ArrayList;

public class MedbayMenu extends AppCompatActivity {
    private int revive_credits = 500;

    private Button backButton;
    private ListView crewListView;
    private TextView textCredits;
    private ArrayList<CrewMember> crewList;
    private ArrayList<String> displayList;
    private ArrayAdapter<String> adapter;
    private CrewDatabase crewDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medbay_menu);

        backButton = findViewById(R.id.backButton);
        crewListView = findViewById(R.id.crewListView);
        textCredits = findViewById(R.id.textCredits);
        crewDatabase = CrewDatabase.getInstance();
        crewList = crewDatabase.getCrewList();
        displayList = new ArrayList<>();
        updateCrewDisplay();
        updateCreditDisplay();

        crewListView.setOnItemClickListener((parent, view, position, id) -> {
            CrewMember selectedCrewMember = crewList.get(position);

            if (selectedCrewMember.isDefeated()) {
                if (crewDatabase.spendCredits(revive_credits)) {
                    selectedCrewMember.revive();
                    Toast.makeText(MedbayMenu.this, selectedCrewMember.getName() + " has been revived!", Toast.LENGTH_SHORT).show();
                    updateCrewDisplay();
                    updateCreditDisplay();
                } else {
                    Toast.makeText(MedbayMenu.this, "Not enough credits!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MedbayMenu.this, selectedCrewMember.getName() + " is not defeated!", Toast.LENGTH_SHORT).show();
            }
        });
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MedbayMenu.this, MainMenu.class);
            startActivity(intent);
        });
    }
    public void updateCrewDisplay() {
        displayList.clear();
        for (CrewMember member : crewList) {
            String status = member.isDefeated() ? "Defeated" : "Alive";
            String text =
                    member.getName() + " (" + member.getClass().getSimpleName() + ")\n" +
                    "Level: " + member.getLevel() + "\n" +
                    "Health: " + member.getHealth() + "/" + member.getMaxHealth() + "\n" +
                    "Energy: " + member.getEnergy() + "/" + member.getMaxEnergy() + "\n" +
                    "Status: " + status;
            displayList.add(text);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        crewListView.setAdapter(adapter);
    }
    public void updateCreditDisplay() {
        int credits = crewDatabase.getCredits();
        textCredits.setText("Credits: " + credits);
    }
}
