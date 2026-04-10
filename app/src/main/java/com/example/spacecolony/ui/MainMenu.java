package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;

public class MainMenu extends AppCompatActivity{
    private Button missionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        missionButton = findViewById(R.id.buttonMission);
        missionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenu.this, MissionMenu.class);
            startActivity(intent);
        });
    }
}
