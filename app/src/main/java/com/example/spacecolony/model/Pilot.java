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
    @Override
    public String useSkill(Threat threat, CrewMember ally) {
        int skillDamage = attackPower + 5;
        threat.takeDamage(skillDamage);
        return name + " uses bombardment!!" + threat.getName() + " takes " + skillDamage + " damage.";
    }
}
