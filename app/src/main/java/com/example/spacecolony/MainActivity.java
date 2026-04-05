package com.example.spacecolony;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Medic;
import com.example.spacecolony.model.Mission;
import com.example.spacecolony.model.Pilot;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SpaceColonyTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CrewMember member1 = new Pilot("Alice");
        CrewMember member2 = new Medic("Bob");

        Mission mission = new Mission("Repair Satellite", 1, 5, 10);
        mission.assignThreat();

        Log.d(TAG, "=== Mission Test Start ===");
        Log.d(TAG, "Mission: " + mission.getName());
        Log.d(TAG, "Difficulty: " + mission.getDifficulty());
        Log.d(TAG, "Energy Cost: " + mission.getEnergyCost());
        Log.d(TAG, "Reward XP: " + mission.getRewardXP());

        Log.d(TAG, " ");
        Log.d(TAG, "=== Initial Status ===");
        logStatus(member1, member2, mission);

        for (int round = 1; round <= 100; round++) {
            Log.d(TAG, " ");
            Log.d(TAG, "=== Round " + round + " ===");

            if (!member1.isDefeated()) {
                member1.act(mission.getThreat());
            }

            if (!mission.isThreatDefeated() && !member2.isDefeated()) {
                member2.defend();
            }

            if (!mission.isThreatDefeated()) {
                mission.threatTurn(member1, member2);
            }

            Log.d(TAG, " ");
            Log.d(TAG, "=== Status After Round " + round + " ===");
            logStatus(member1, member2, mission);

            member1.resetShield();
            member2.resetShield();

            if (mission.isThreatDefeated()) {
                Log.d(TAG, "Threat defeated!");
                if (!member1.isDefeated()) {
                    member1.gainExperience(mission.getRewardXP());
                }
                if (!member2.isDefeated()) {
                    member2.gainExperience(mission.getRewardXP());
                }
                break;
            }

            if (member1.isDefeated() && member2.isDefeated()) {
                Log.d(TAG, "Both crew members were defeated. Mission failed.");
                break;
            }
        }

        Log.d(TAG, " ");
        Log.d(TAG, "=== Final Status ===");
        logStatus(member1, member2, mission);

        Log.d(TAG, " ");
        Log.d(TAG, "=== Crew Info ===");
        Log.d(TAG, member1.toString());
        Log.d(TAG, member2.toString());

        Log.d(TAG, "=== Mission Test End ===");
    }

    private void logStatus(CrewMember member1, CrewMember member2, Mission mission) {
        Log.d(TAG, member1.getName() + " HP: " + member1.getHealth() + "/" + member1.getMaxHealth()
                + " | Energy: " + member1.getEnergy() + "/" + member1.getMaxEnergy()
                + " | Shield: " + member1.getShield());

        Log.d(TAG, member2.getName() + " HP: " + member2.getHealth() + "/" + member2.getMaxHealth()
                + " | Energy: " + member2.getEnergy() + "/" + member2.getMaxEnergy()
                + " | Shield: " + member2.getShield());

        if (mission.getThreat() != null) {
            Log.d(TAG, mission.getThreat().getName() + " HP: " + mission.getThreat().getHealth());
        }
    }
}