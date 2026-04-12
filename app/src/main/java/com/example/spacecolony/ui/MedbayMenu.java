package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.data.CrewDatabase;
import com.example.spacecolony.model.CrewMember;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MedbayMenu extends AppCompatActivity {
    private int revive_credits = 500;

    private Button backButton;
    private TextView textCredits;
    private ArrayList<CrewMember> crewList;
    private CrewDatabase crewDatabase;
    private RecyclerView crewRecyclerView;
    private CrewAdapter crewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medbay_menu);

        backButton = findViewById(R.id.backButton);
        textCredits = findViewById(R.id.textCredits);
        crewDatabase = CrewDatabase.getInstance();
        crewList = crewDatabase.getCrewList();
        crewRecyclerView = findViewById(R.id.crewRecyclerView);
        crewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        crewAdapter = new CrewAdapter(crewList, new ArrayList<>(), new CrewAdapter.OnCrewSelectListener() {
            @Override
            public void onCrewSelectionChanged() {
            }
            @Override
            public void onLimitReached() {
            }
            @Override
            public void onItemClick(CrewMember crewMember) {
                if (crewMember.isDefeated()) {
                    if (CrewDatabase.getInstance().spendCredits(revive_credits)) {
                        crewMember.revive();
                        Toast.makeText(MedbayMenu.this, "Crew member revived!", Toast.LENGTH_SHORT).show();
                        updateCreditDisplay();
                        crewAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MedbayMenu.this, "Not enough credits!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, false, true);

        crewRecyclerView.setAdapter(crewAdapter);
        updateCreditDisplay();

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MedbayMenu.this, MainMenu.class);
            startActivity(intent);
        });
    }
    public void updateCreditDisplay() {
        int credits = crewDatabase.getCredits();
        textCredits.setText("Credits: " + credits);
    }
}
