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
    @Override
    public String useSkill(Threat threat, CrewMember ally) {
        threat.attackPower -= 2;

        if (threat.attackPower < 2) {
            threat.attackPower = 2;
        }
        return name + " has analyzed the threat's attack pattern and lower it attack power to " + threat.attackPower + "!";
    }
}
