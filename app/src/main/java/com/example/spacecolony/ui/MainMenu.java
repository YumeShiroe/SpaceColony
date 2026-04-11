package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;

public class MainMenu extends AppCompatActivity{
    private Button missionButton;
    private Button simulatorButton;
    private Button recruitButton;
    private Button crewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        missionButton = findViewById(R.id.buttonMission);
        simulatorButton = findViewById(R.id.buttonSimulator);
        recruitButton = findViewById(R.id.buttonRecruit);
        crewButton = findViewById(R.id.buttonCrew);

        missionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, MissionMenu.class);
            startActivity(intent);
        });
        simulatorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, SimulatorMenu.class);
            startActivity(intent);
        });
        recruitButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, RecruitMenu.class);
            startActivity(intent);
        });
        crewButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, CrewMenu.class);
            startActivity(intent);
        });
    }
}
