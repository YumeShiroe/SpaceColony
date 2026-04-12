package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spacecolony.R;
import com.example.spacecolony.data.CrewDatabase;
import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.StatisticsAdapter;

import java.util.ArrayList;

public class StatisticMenu extends AppCompatActivity{
    private TextView textTotalStatistic;
    private RecyclerView statRecyclerView;
    private Button backButton;
    private CrewDatabase crewDatabase;
    private ArrayList<CrewMember> crewList;
    private StatisticsAdapter statisticAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_menu);

        textTotalStatistic = findViewById(R.id.textTotalStats);
        backButton = findViewById(R.id.backButton);
        statRecyclerView = findViewById(R.id.statisticsRecyclerView);
        crewDatabase = CrewDatabase.getInstance();
        crewList = crewDatabase.getCrewList();
        textTotalStatistic.setText(crewDatabase.getAllStatistic());
        statRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        statisticAdapter = new StatisticsAdapter(crewList);
        statRecyclerView.setAdapter(statisticAdapter);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(StatisticMenu.this, MainMenu.class);
            startActivity(intent);
        });
    }
}
