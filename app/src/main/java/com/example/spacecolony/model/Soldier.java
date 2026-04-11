package com.example.spacecolony.model;

public class Soldier extends CrewMember {
    public Soldier(String name) {
        super(name, 25, 25,6, 6);
    }

    @Override
    public int getHealthGrowth() {
        return 3;
    }
    @Override
    public int getEnergyGrowth() {
        return 4;
    }

    @Override
    public int getDefenseGrowth() { return 3; }
    @Override
    public int getAttackGrowth() { return 3; }

    @Override
    public String getRoleName() {
        return "Soldier";
    }
    @Override
    public String useSkill(Threat threat, CrewMember ally) {
        shield += defensePower;
        threat.takeDamage(attackPower + shield - 3);
        return name + " utilized close quarter combat. Soldier gains " + defensePower + " shield and deals " + attackPower + " damage";
    }
}
