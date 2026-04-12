package com.example.spacecolony.model;

public class Medic extends CrewMember {
    public Medic(String name) {
        super(name, 22, 22, 3, 5);
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
    public int getAttackGrowth() { return 1; }

    @Override
    public String getRoleName() {
        return "Medic";
    }
    @Override
    public String useSkill(Threat threat, CrewMember ally) {
        int healAmount = attackPower + defensePower;

        this.health += healAmount;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }

        if (ally != null && !ally.isDefeated()) {
            ally.health += healAmount - 2;
            if (ally.health > ally.maxHealth) {
                ally.health = ally.maxHealth;
            }
        }
        recordSkillUsed();
        return name + " heals himself for " + healAmount + " and ally for " + (healAmount - 2) + " health!";
    }
}
