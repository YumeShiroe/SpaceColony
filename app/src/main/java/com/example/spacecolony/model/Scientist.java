package com.example.spacecolony.model;

public class Scientist extends CrewMember {
    public Scientist(String name) {
        super(name, 20, 20, 4, 4);
    }

    @Override
    public int getHealthGrowth() {
        return 2;
    }
    @Override
    public int getEnergyGrowth() {
        return 2;
    }
    @Override
    public int getDefenseGrowth() { return 1; }
    @Override
    public int getAttackGrowth() { return 1; }

    @Override
    public String getRoleName() {
        return "Scientist";
    }
}
