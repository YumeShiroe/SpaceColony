package com.example.spacecolony.model;

import com.example.spacecolony.data.CrewDatabase;
public class Colony {
    private String name;
    private int recruitCost;
    // placeholder cuz I haven't decided what type of resources yet
    private int resource;

    public Colony(String name) {
        this.name = name;
        this.resource = 100;
        this.recruitCost = 50;
    }

    public void addResource(int amount) {
        resource += amount;
    }

    public void spendResource(int amount) {
        resource -= amount;
        if (resource < 0) {
            resource = 0;
        }
    }

    public boolean ableToRecruit() {
        return resource >= recruitCost;
    }

    public CrewMember recruitCrewMember(CrewDatabase crewDatabase, String role) {
        if (!ableToRecruit()) {
            System.out.println("You don't have enough resources to recruit.");
            return null;
        }

        spendResource(recruitCost);
        return crewDatabase.recruitCrewMember(role);
    }

    public String getName() {
        return name;
    }
    public int getRecruitCost() {
        return recruitCost;
    }
    public int getResources() {
        return resource;
    }
}
