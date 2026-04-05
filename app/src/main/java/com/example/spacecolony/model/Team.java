package com.example.spacecolony.model;

import java.util.ArrayList;

public class Team {
    private ArrayList<CrewMember> team;
    private int maxMembers;

    public Team() {
        team = new ArrayList<>();
        maxMembers = 2;
    }

    public void addMember(CrewMember member) {
        if (team.size() > maxMembers) {
            System.out.println("Team is full.");
        } else if (team.contains(member)) {
            System.out.println("Member is already in the team.");
        } else {
            team.add(member);
        }
    }

    public void removeMember(CrewMember member) {
        if (team.contains(member)) {
            team.remove(member);
        } else {
            System.out.println("This member is not in the team.");
        }
    }

    public void displayTeam() {
        System.out.println("Team Members:");
        for (CrewMember member : team) {
            System.out.println(member.getName());
        }
    }
}
