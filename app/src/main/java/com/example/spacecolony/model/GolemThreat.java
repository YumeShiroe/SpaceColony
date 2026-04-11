package com.example.spacecolony.model;

import java.util.Random;

public class GolemThreat extends Threat {
    private Random random;
    private int heavySlamCoolDown;

    public GolemThreat(int missionDifficulty) {
        super("Guardian Golem", 50, 3, missionDifficulty);
        this.heavySlamCoolDown = 3;
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
        heavySlamCoolDown = 1;
        return name + " uses Ground Slam!!!";
    }
    @Override
    public void usingSkills(CrewMember target1, CrewMember target2) {
        performTurn(target1, target2);
    }

    @Override
    public String performTurn(CrewMember target1, CrewMember target2) {
        if (heavySlamCoolDown > 0) {
            heavySlamCoolDown--;
        }
        if (heavySlamCoolDown == 0) {
            int heavySlamSkillChance = random.nextInt(100);

            if (heavySlamSkillChance < 40) {
                return GroundSlam(target1, target2);
            }
        }

        CrewMember target = chooseTarget(target1, target2);

        if (target != null) {
            attack(target);
            return name + " strikes" + target.getName() + " with its hand.";
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

    public int getHeavySlamCoolDown() {
        return heavySlamCoolDown;
    }
}
