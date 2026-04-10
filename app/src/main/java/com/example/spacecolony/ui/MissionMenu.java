package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;

public class MissionMenu extends AppCompatActivity{
    private Button buttonAdjustTeam;
    private Button buttonStartMission;

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
            if (crewName1.isEmpty() || crewName2.isEmpty()) {
                Toast.makeText(this, "Please select your team first.", Toast.LENGTH_SHORT).show();
            } else {
                // Intent to start the mission
                Toast.makeText(this, "Starting mission with " + crewName1 + " and " + crewName2, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            crewName1 = data.getStringExtra("crew1");
            crewName2 = data.getStringExtra("crew2");
        }
    }
}
