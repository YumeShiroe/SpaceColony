package com.example.spacecolony;

import com.example.spacecolony.model.Engineer;
import com.example.spacecolony.model.Medic;
import com.example.spacecolony.model.Pilot;
import com.example.spacecolony.model.Scientist;
import com.example.spacecolony.model.Soldier;
import com.example.spacecolony.model.Team;
import com.example.spacecolony.model.Mission;

public class MainActivity {
    public static void main(String[] args) {
        Team team = new Team();

        team.addMember(new Pilot("Alice"));
        team.addMember(new Engineer("Bob"));
        team.addMember(new Medic("Clara"));
        team.addMember(new Scientist("David"));
        team.addMember(new Soldier("Ethan"));

        team.showTeam();

        Mission mission = new Mission("Repair Satellite", 2, 5, 10);
        mission.executeMission(team);

        System.out.println("\nAfter mission:\n");
        team.showTeam();
    }
}