package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.model.Team;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.data.CrewDatabase;
import java.util.ArrayList;
import com.example.spacecolony.model.Mission;

public class MissionMenu extends AppCompatActivity{
    private Button buttonAdjustTeam;
    private Button buttonStartMission;
    private Team team;

    private String crewName1 = "";
    private String crewName2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_menu);

        buttonAdjustTeam = findViewById(R.id.buttonAdjustTeam);
        buttonStartMission = findViewById(R.id.buttonStartMission);

        buttonAdjustTeam.setOnClickListener(v -> {
            Intent intent = new Intent(MissionMenu.this, AdjustTeamMenu.class);
            startActivityForResult(intent, 1);
        });

        buttonStartMission.setOnClickListener(v -> {
            if (team == null) {
                Toast.makeText(this, "Please select your team first.", Toast.LENGTH_SHORT).show();
            } else {
                // Intent to start the mission add here later
                Mission mission = new Mission("Test Mission", 1, 10, 100);
                if (mission.startBattle(team)) {
                    BattleMenu.currentMission = mission;

                    Intent intent = new Intent(MissionMenu.this, BattleMenu.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            crewName1 = data.getStringExtra("crew1");
            crewName2 = data.getStringExtra("crew2");
        }

        CrewDatabase crewDatabase = CrewDatabase.getInstance();
        ArrayList<CrewMember> crewList = crewDatabase.getCrewList();

        CrewMember member1 = null;
        CrewMember member2 = null;

        for (CrewMember member : crewList) {
            if (member.getName().equalsIgnoreCase(crewName1)) {
                member1 = member;
            }
            if (member.getName().equalsIgnoreCase(crewName2)) {
                member2 = member;
            }
        }

        if (member1 != null && member2 != null) {
            team = new Team();
            team.addMember(member1);
            team.addMember(member2);
        }
    }
}
