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

public class RecruitMenu extends AppCompatActivity {
    private Button medicButton;
    private Button soldierButton;
    private Button pilotButton;
    private Button ScientistButton;
    private Button engineerButton;
    private Button backButton;

    private ListView crewListView;
    private ArrayList<CrewMember> crewList;
    private ArrayList<String> displayList;
    private ArrayAdapter<String> adapter;
    private TextView textCredits;
    private TextView textRecruitedCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit_menu);

        medicButton = findViewById(R.id.buttonMedic);
        soldierButton = findViewById(R.id.buttonSoldier);
        pilotButton = findViewById(R.id.buttonPilot);
        ScientistButton = findViewById(R.id.buttonScientist);
        engineerButton = findViewById(R.id.buttonEngineer);
        backButton = findViewById(R.id.buttonBack);

        crewListView = findViewById(R.id.crewListView);

        textCredits = findViewById(R.id.textCredits);
        textRecruitedCost = findViewById(R.id.textRecruitedCost);

        crewList = CrewDatabase.getInstance().getCrewList();
        displayList = new ArrayList<>();
        updateCrewDisplay();
        updateRecruitPrice();

        medicButton.setOnClickListener(v -> {
            recruitCrewMemberByRole("Medic");
        });
        soldierButton.setOnClickListener(v -> {
            recruitCrewMemberByRole("Soldier");
        });
        pilotButton.setOnClickListener(v -> {
            recruitCrewMemberByRole("Pilot");
        });
        ScientistButton.setOnClickListener(v -> {
            recruitCrewMemberByRole("Scientist");
        });
        engineerButton.setOnClickListener(v -> {
            recruitCrewMemberByRole("Engineer");
        });
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(RecruitMenu.this, MainMenu.class);
            startActivity(intent);
        });
    }

    private void recruitCrewMemberByRole(String role) {
        CrewDatabase crewDatabase = CrewDatabase.getInstance();
        int cost = crewDatabase.getRecruitedCost();
        CrewMember newMember = crewDatabase.recruitCrewMember(role);

        if (newMember != null) {
            Toast.makeText(RecruitMenu.this, newMember.getName() + " has been recruited!", Toast.LENGTH_SHORT).show();
            updateCrewDisplay();
            updateRecruitPrice();
        } else {
            if (crewDatabase.isRecruitSoftCapReached()) {
                Toast.makeText(RecruitMenu.this, "Recruiting soft cap reached for this version!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RecruitMenu.this, "Not enough credits!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateCrewDisplay() {
        displayList.clear();

        for (CrewMember member : crewList) {
            String text = member.getName() + " - " +
                    member.getClass().getSimpleName() + " - Level: " +
                    member.getLevel();
            displayList.add(text);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);

        crewListView.setAdapter(adapter);
    }
    private void updateRecruitPrice() {
        CrewDatabase crewDatabase = CrewDatabase.getInstance();

        textCredits.setText("Credits: " + crewDatabase.getCredits());
        textRecruitedCost.setText("Recruited Cost: " + crewDatabase.getRecruitedCost());

        if (crewDatabase.isRecruitSoftCapReached()) {
            textRecruitedCost.setText("You have reached the version soft cap!");
        }
    }
}
