package com.example.spacecolony.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Mission {
    private String name;
    private int difficulty;
    private int energyCost;
    private int rewardXP;
    private Threat threat;

    public Mission(String name, int difficulty, int energyCost, int rewardXP) {
        this.name = name;
        this.difficulty = difficulty;
        this.energyCost = energyCost;
        this.rewardXP = rewardXP;
    }

    // Temporary threat
    public void assignThreat() {
        threat = new AlienThreat(difficulty);
    }
    // Starting mission: Crew member need a specific amount of energy to join the mission
    public boolean isThreatDefeated() {
        return threat.isDefeated();
    }

    public boolean isCrewDefeated(CrewMember member1, CrewMember member2) {
        return member1.isDefeated() && member2.isDefeated();
    }

    public void battleStatus(CrewMember member1, CrewMember member2) {
        System.out.println("_____Battle Status_____");
        System.out.println(member1.getName() + " HP: " + member1.getHealth() + "/" + member1.getMaxHealth() + " | Shield" + member1.getShield());
        System.out.println(member2.getName() + " HP: " + member2.getHealth() + "/" + member2.getMaxHealth() + " | Shield" + member2.getShield());
        System.out.println(threat.getName() + " HP: " + threat.getHealth());
        System.out.println();
    }

    public void crewMemberTurn(CrewMember member, Scanner sc) {
        if (member.isDefeated()) {
            return;
        }

        System.out.println(member.getName() + "'s turn:");
        System.out.println("1. Attack");
        System.out.println("2. Defend");
        System.out.println("Choose an action:");
        int choice = sc.nextInt();

        if (choice == 1) {
            member.act(threat);
        } else if (choice == 2) {
            member.defend();
        } else {
            System.out.println("Invalid action. The member will use shield");
            member.defend();
        }
    }

    public void threatTurn(CrewMember member1, CrewMember member2) {
        if (threat.isDefeated()) {
            return;
        }

        System.out.println(threat.getName() + "'s turn:");

        Random random = new Random();

        if (!member1.isDefeated() && !member2.isDefeated()) {
            int targetChoice = random.nextInt(2);

            if (targetChoice == 0) {
                threat.attack(member1);
            } else if (targetChoice == 1) {
                threat.attack(member2);
            }
        } else if (!member1.isDefeated()) {
            threat.attack(member1);
        } else if (!member2.isDefeated()) {
            threat.attack(member2);
        }
    }

    public void startBattle(CrewMember member1, CrewMember member2) {
        if (!member1.enoughEnergyForMission(energyCost)) {
            System.out.println(member1.getName() + " does not have enough energy to join the mission.");
            return;
        }
        if (!member2.enoughEnergyForMission(energyCost)) {
            System.out.println(member2.getName() + " does not have enough energy to join the mission.");
            return;
        }

        member1.useEnergy(energyCost);
        member2.useEnergy(energyCost);

        assignThreat();
        Scanner sc = new Scanner(System.in);

        while (!isThreatDefeated() && !isCrewDefeated(member1, member2)) {
            battleStatus(member1, member2);

            crewMemberTurn(member1, sc);
            if (isThreatDefeated()) {
                break;
            }
            crewMemberTurn(member2, sc);
            if (isThreatDefeated()) {
                break;
            }

            threatTurn(member1, member2);

            member1.resetShield();
            member2.resetShield();
        }

        battleStatus(member1, member2);

        if (isThreatDefeated()) {
            System.out.println(threat.getName() + " has been defeated!");
            if (!member1.isDefeated()) {
                member1.gainExperience(rewardXP);
            }
            if (!member2.isDefeated()) {
                member2.gainExperience(rewardXP);
            }
        } else {
            System.out.println(member1.getName() + " and " + member2.getName() + " have been defeated!");
            System.out.println("Mission failed");
        }
    }

    // getters
    public String getName() {
        return name;
    }
    public int getDifficulty() {
        return difficulty;
    }
    public int getEnergyCost() {
        return energyCost;
    }
    public int getRewardXP() {
        return rewardXP;
    }

    public Threat getThreat() {
        return threat;
    }
}

