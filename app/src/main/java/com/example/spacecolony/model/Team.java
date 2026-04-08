package com.example.spacecolony.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Team {
    private ArrayList<CrewMember> team;

    public Team() {
        team = new ArrayList<>();
    }

    public void addMember(CrewMember member) {
        team.add(member);
    }

    public void removeMember(CrewMember member) {
        if (team.contains(member)) {
            team.remove(member);
        } else {
            System.out.println("This member is not in the team.");
        }
    }

    public ArrayList<CrewMember> getMembers() {
        return team;
    }

    public int getTeamSize() {
        return team.size();
    }

    public boolean contains(CrewMember member) {
        return team.contains(member);
    }

    public void displayTeam() {
        System.out.println("Team Members:");
        for (CrewMember member : team) {
            System.out.println(member.getName());
        }
    }
}
