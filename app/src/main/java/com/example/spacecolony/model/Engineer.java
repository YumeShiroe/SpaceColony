package com.example.spacecolony.model;

public class Engineer extends CrewMember {
    public Engineer(String name) {
        super(name, 22, 22, 5, 5);
    }

    @Override
    public int getHealthGrowth() {
        return 1;
    }
    @Override
    public int getEnergyGrowth() {
        return 6;
    }
    @Override
    public int getDefenseGrowth() { return 1; }
    @Override
    public int getAttackGrowth() { return 1; }

    @Override
    public String getRoleName() {
        return "Engineer";
    }
}
