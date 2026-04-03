package com.example.spacecolony.model;

public class Medic extends CrewMember {
    public Medic(String name) {
        super(name, 22, 22, 2, 5);
    }

    @Override
    public int getHealthGrowth() {
        return 4;
    }
    @Override
    public int getEnergyGrowth() {
        return 2;
    }
    @Override
    public int getDefenseGrowth() { return 2; }
    @Override
    public int getAttackGrowth() { return 0; }

    @Override
    public String getRoleName() {
        return "Medic";
    }
}
