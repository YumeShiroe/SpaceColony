package com.example.spacecolony.model;

import java.util.Random;

public class LesserSandstorm extends Threat {
    private Random random;
    private int dustStormCoolDown;

    public LesserSandstorm(int missionDifficulty) {
        super("Lesser Standstorm", 30, 5, missionDifficulty);
        this.dustStormCoolDown = 3;
        this.random = new Random();
    }

    @Override
    public void attack(CrewMember target) {
        super.attack(target);
    }
    public String DustStorm(CrewMember target1, CrewMember target2) {
        if (!target1.isDefeated()) {
            target1.takeDamage(attackPower);
        }
        if (!target2.isDefeated()) {
            target2.takeDamage(attackPower);
        }
        dustStormCoolDown = 1;
        return name + " uses Dust Storm!!!";
    }
    @Override
    public void usingSkills(CrewMember target1, CrewMember target2) {
        performTurn(target1, target2);
    }

    @Override
    public String performTurn(CrewMember target1, CrewMember target2) {
        if (dustStormCoolDown > 0) {
            dustStormCoolDown--;
        }
        if (dustStormCoolDown == 0) {
            int dustStormSkillChance = random.nextInt(100);

            if (dustStormSkillChance < 40) {
                return DustStorm(target1, target2);
            }
        }

        CrewMember target = chooseTarget(target1, target2);

        if (target != null) {
            return name + " throws sand at " + target.getName();
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

    public int getDustStormCoolDown() {
        return dustStormCoolDown;
    }
}
