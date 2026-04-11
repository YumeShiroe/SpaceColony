package com.example.spacecolony.model;

import java.util.Random;

public class GalaticBandit extends Threat {
    private Random random;
    private int horizontalSlashCoolDown;

    public GalaticBandit(int missionDifficulty) {
        super("Galatic Bandit", 25, 3, missionDifficulty);
        this.horizontalSlashCoolDown = 2;
        this.random = new Random();
    }

    @Override
    public void attack(CrewMember target) {
        super.attack(target);
    }
    public String GroundSlam(CrewMember target1, CrewMember target2) {
        if (!target1.isDefeated()) {
            target1.takeDamage(attackPower + 1);
        }
        if (!target2.isDefeated()) {
            target2.takeDamage(attackPower + 1);
        }
        horizontalSlashCoolDown = 2;
        return name + " proficiently slashes at " + target1.getName() + " and " + target2.getName() + "!!!";
    }
    @Override
    public void usingSkills(CrewMember target1, CrewMember target2) {
        performTurn(target1, target2);
    }

    @Override
    public String performTurn(CrewMember target1, CrewMember target2) {
        if (horizontalSlashCoolDown > 0) {
            horizontalSlashCoolDown--;
        }
        if (horizontalSlashCoolDown == 0) {
            int horizontalSlashSkillChance = random.nextInt(100);

            if (horizontalSlashSkillChance < 40) {
                return GroundSlam(target1, target2);
            }
        }

        CrewMember target = chooseTarget(target1, target2);

        if (target != null) {
            attack(target);
            return name + " slashes " + target.getName() + " with his sword.";
        }
        return name + " has no valid target";
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

    public int getHorizontalSlashCoolDown() {
        return horizontalSlashCoolDown;
    }
}
