package com.example.spacecolony.model;

import java.util.Random;

public class MaxwellThreat extends Threat{
    private int loafCoolDown;
    private Random random;

    public MaxwellThreat(int missionDifficulty) {
        super("Maxwell the Cat", 30, 6, missionDifficulty);
        this.loafCoolDown = 2;
        this.random = new Random();
    }

    public void attack(CrewMember target) {
        System.out.println(name + " scratches " + target.getName() + " with its claws.");
        super.attack(target);
    }

    public String loaf(CrewMember target1, CrewMember target2) {
        System.out.println(name + " decides to loaf! It hits everyone!");
        if (!target1.isDefeated()) {
            target1.takeDamage(attackPower - 2);
        }
        if (!target2.isDefeated()) {
            target2.takeDamage(attackPower - 2);
        }

        loafCoolDown = 3;
        return name + " uses loaf skill!!!";
    }
    @Override
    public void usingSkills(CrewMember target1, CrewMember target2) {
        performTurn(target1, target2);
    }

    @Override
    public String performTurn(CrewMember target1, CrewMember target2) {
        if (loafCoolDown > 0) {
            loafCoolDown--;
        }
        if (loafCoolDown == 0) {
            int loafSkillChance = random.nextInt(100);

            if (loafSkillChance < 40) {
                return loaf(target1, target2);
            }
        }

        CrewMember target = chooseTarget(target1, target2);

        if (target != null) {
            attack(target);
            return name + " carefully rips " + target.getName() + " with its claws.";
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

    public int getLoafCoolDown() {
        return loafCoolDown;
    }
}
