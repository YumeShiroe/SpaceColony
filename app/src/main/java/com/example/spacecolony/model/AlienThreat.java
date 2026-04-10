package com.example.spacecolony.model;

import java.util.Random;

public class AlienThreat extends Threat {
    private Random random;
    public AlienThreat(int missionDifficulty) {
        super("Alien", 20, 5, missionDifficulty);
        this.random = new Random();
    }
    @Override
    public void attack(CrewMember target) {
        super.attack(target);
    }

    public String piercingShot(CrewMember target) {
        int damage = attackPower + 2;
        target.takeDamage(damage);
        return name + " uses Piercing Shot on " + target.getName() + " for " + damage + " damage";
    }

    @Override
    public void usingSkills(CrewMember target1, CrewMember target2) {
        performTurn(target1, target2);
    }
    @Override
    public String performTurn(CrewMember target1, CrewMember target2) {
        CrewMember target = chooseTarget(target1, target2);

        if (target == null) {
            return name + " has no valid target";
        }

        int skillChance = random.nextInt(100);

        if (skillChance < 30) {
            return piercingShot(target);
        } else {
            attack(target);
            return name + " shoots " + target.getName();
        }
    }
    private CrewMember chooseTarget(CrewMember target1, CrewMember target2) {
        if (!target1.isDefeated() && !target2.isDefeated()) {
            int choice = random.nextInt(2);

            if (choice == 0) {
                return target1;
            } else {
                return target2;
            }
        } else if (target1.isDefeated()) {
            return target2;
        } else if (target2.isDefeated()) {
            return target1;
        }

        return null;
    }
}
