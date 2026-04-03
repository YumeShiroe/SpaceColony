package com.example.spacecolony.model;

public class Pilot extends CrewMember {
    public Pilot(String name) {
        super(name, 20, 25, 6, 5);
    }

    @Override
    public int getHealthGrowth() {
        return 2;
    }
    @Override
    public int getEnergyGrowth() {
        return 5;
    }
    @Override
    public int getDefenseGrowth() { return 1; }
    @Override
    public int getAttackGrowth() { return 2; }

    @Override
    public String getRoleName() {
        return "Pilot";
    }
}
