package com.example.spacecolony.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spacecolony.R;
import com.example.spacecolony.data.CrewDatabase;
import com.example.spacecolony.model.CrewMember;
import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CrewMenu extends AppCompatActivity {
    private Button backButton;
    private ArrayList<CrewMember> crewList;
    private RecyclerView crewRecyclerView;
    private CrewAdapter crewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_menu);

        backButton = findViewById(R.id.backButton);
        crewRecyclerView = findViewById(R.id.crewRecyclerView);
        crewList = CrewDatabase.getInstance().getCrewList();
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
            }
        }, false, false);
        crewRecyclerView.setAdapter(crewAdapter);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CrewMenu.this, MainMenu.class);
            startActivity(intent);
        });
    }

}
