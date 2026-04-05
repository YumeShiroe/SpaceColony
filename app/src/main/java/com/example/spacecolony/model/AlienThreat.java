package com.example.spacecolony.model;

import java.util.Random;

public class AlienThreat extends Threat {
    private Random random;
    public AlienThreat(int missionDifficulty) {
        super("Alien", 20, 5, missionDifficulty);
        this.random = new Random();
    }

    public void attack(CrewMember target) {
        System.out.println(name + " shots " + target.getName());
        super.attack(target);
    }

    public void usingSkills(CrewMember target1, CrewMember target2) {
        CrewMember target = chooseTarget(target1, target2);

        if (target != null) {
            attack(target);
        }
    }

    private CrewMember chooseTarget(CrewMember target1, CrewMember target2) {
        if (!target1.isDefeated() && !target2.isDefeated()) {
            int choice = random.nextInt();

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
