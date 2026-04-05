package com.example.spacecolony;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolony.data.CrewDatabase;
import com.example.spacecolony.model.Colony;
import com.example.spacecolony.model.CrewMember;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CrewDatabase crewDatabase = new CrewDatabase();
        Colony colony = new Colony("Space Colony");

        Log.d("RecruitTest", "Starting resources: " + colony.getResources());
        Log.d("RecruitTest", "Starting crew count: " + crewDatabase.getCrewSize());

        CrewMember recruitedMember = colony.recruitCrewMember(crewDatabase, "Medic");

        if (recruitedMember != null) {
            Log.d("RecruitTest", "Recruited: " + recruitedMember.getName());
            Log.d("RecruitTest", "Resources left: " + colony.getResources());
            Log.d("RecruitTest", "Crew count now: " + crewDatabase.getCrewSize());
        } else {
            Log.d("RecruitTest", "Not enough resources to recruit.");
        }

        for (int i = 0; i < crewDatabase.getCrewSize(); i++) {
            CrewMember member = crewDatabase.getCrewMemberByIndex(i);
            if (member != null) {
                Log.d("RecruitTest", member.toString());
            }
        }
    }
}