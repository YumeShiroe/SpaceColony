package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;

public class StartMenu extends AppCompatActivity{
    private Button startButton;
    private Button creditButton;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        startButton = findViewById(R.id.buttonStart);
        creditButton = findViewById(R.id.buttonCredit);
        exitButton = findViewById(R.id.buttonQuit);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(StartMenu.this, MainMenu.class);
            startActivity(intent);
        });
        creditButton.setOnClickListener(v -> {Toast.makeText(this, "Credit button clicked", Toast.LENGTH_SHORT).show();});
        exitButton.setOnClickListener(v -> {Toast.makeText(this, "Exit button clicked", Toast.LENGTH_SHORT).show();});
    }
}
