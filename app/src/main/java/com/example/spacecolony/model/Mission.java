package com.example.spacecolony.model;

public class Mission {
    private String name;
    private int difficulty;
    private int energyCost;
    private int rewardXP;

    public Mission(String name, int difficulty, int energyCost, int rewardXP) {
        this.name = name;
        this.difficulty = difficulty;
        this.energyCost = energyCost;
        this.rewardXP = rewardXP;
    }

    // Starting mission: Crew member need a specific amount of energy to join the mission
    public void executeMission(Team team) {
        for (CrewMember member : team.getMembers()) {
            if (member.enoughEnergyForMission(energyCost)) {
                member.useEnergy(energyCost);
                member.gainExperience(rewardXP);
                System.out.println(member.getName() + " has completed the mission.");
            } else {
                System.out.println(member.getName() + " does not have enough energy.");
            }
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
}
