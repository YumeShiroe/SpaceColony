package com.example.spacecolony.model;

import java.util.ArrayList;

public class Team {
    private ArrayList<CrewMember> members;

    public Team() {
        members = new ArrayList<>();
    }

    public void addMember(CrewMember member) {
        if (member != null) {
            members.add(member);
        }
    }

    public void removeMember(CrewMember member) {
        members.remove(member);
    }

    public ArrayList<CrewMember> getMembers() {
        return members;
    }

    public void showTeam() {
        if (members.isEmpty()) {
            System.out.println("Team is empty.");
            return;
        }
        System.out.println("Team members:");
        for (CrewMember member : members) {
            System.out.println(member);
        }
    }

    public int getTeamSize() {
        return members.size();
    }
}
