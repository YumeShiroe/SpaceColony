package com.example.spacecolony.model;

public class Engineer extends CrewMember {
    public Engineer(String name) {
        super(name, 22, 22, 5, 7);
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
    public int getDefenseGrowth() { return 2; }
    @Override
    public int getAttackGrowth() { return 1; }

    @Override
    public String getRoleName() {
        return "Engineer";
    }
    @Override
    public String useSkill(Threat threat, CrewMember ally) {
        shield += 99;
        return name + " uses Absolute Defense!! Engineer's shield is temporary increased to 99!";
    }
}
