package com.example.spacecolony.ui;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.data.CrewDatabase;

public class EndGameMenu extends AppCompatActivity{
    private Button buttonBackToMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_menu);

        buttonBackToMainMenu = findViewById(R.id.buttonBackToMainMenu);

        buttonBackToMainMenu.setOnClickListener(v -> {
            BattleMenu.currentMission = null;

            Intent intent = new Intent(EndGameMenu.this, MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
