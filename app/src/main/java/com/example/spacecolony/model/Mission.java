package com.example.spacecolony.model;

import com.example.spacecolony.data.CrewDatabase;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Mission {
    private String name;
    private int difficulty;
    private int energyCost;
    private int rewardXP;
    private int rewardCredit;
    private Threat threat;
    private int turnCount;
    private boolean isBattleStarted;
    private CrewMember member1;
    private CrewMember member2;

    public Mission(String name, int difficulty, int energyCost, int rewardXP, int rewardCredit) {
        this.name = name;
        this.difficulty = difficulty;
        this.energyCost = energyCost;
        this.rewardXP = rewardXP;
        this.rewardCredit = rewardCredit;
    }

    // Temporary threat
    public void assignThreat() {
        Random random = new Random();
        if (difficulty >= 5) {
            threat = new GalaticBanditBoss(difficulty);
            return;
        }
        int rand = random.nextInt(5);

        if (rand == 0) {
            threat = new AlienThreat(difficulty);
        } else if (rand == 1) {
            threat = new GalaticBandit(difficulty);
        } else if (rand == 2) {
            threat = new GolemThreat(difficulty);
        } else if (rand == 3) {
            threat = new LesserSandstorm(difficulty);
        } else {
            threat = new MaxwellThreat(difficulty);
        }
    }

    public void increaseDifficulty() {
        if (difficulty < 5) {
            difficulty++;
        }
    }
    // Starting mission: Crew member need a specific amount of energy to join the mission
    public boolean isThreatDefeated() {
        return threat.isDefeated();
    }

    public boolean isCrewDefeated(CrewMember member1, CrewMember member2) {
        return member1.isDefeated() && member2.isDefeated();
    }

    public boolean startBattle(Team team) {
        if (team == null || team.getTeamSize() != 2) {
            return false;
        }

        member1 = team.getMembers().get(0);
        member2 = team.getMembers().get(1);

        if (!member1.enoughEnergyForMission(energyCost)) {
            return false;
        }
        if (!member2.enoughEnergyForMission(energyCost)) {
            return false;
        }

        member1.useEnergy(energyCost);
        member2.useEnergy(energyCost);

        assignThreat();
        turnCount = 1;
        isBattleStarted = true;
        return true;
    }

    public String playerAttack() {
        if (!isBattleStarted) {
            return "Battle not started";
        }

        CrewMember currentMember;

        if (isMember1Turn()) {
            currentMember = member1;
        } else if (isMember2Turn()) {
            currentMember = member2;
        } else {
            return "It's not your turn";
        }
        if (currentMember.isDefeated()) {
            String battleLog = "This member is defeated and cannot act.";
            turnCount++;
            return battleLog;
        }
        currentMember.act(threat);
        String battleLog = currentMember.getName() + " attacks " + threat.getName();
        currentMember.reduceSkillCooldown();

        if (threat.isDefeated()) {
            dropReward();
            return battleLog + "\nThreat Defeated! Mission Completed!";
        }
        turnCount++;
        return battleLog;
    }

    public String playerDefend() {
        if (!isBattleStarted) {
            return "Battle not started";
        }

        CrewMember currentMember;

        if (isMember1Turn()) {
            currentMember = member1;
        } else if (isMember2Turn()) {
            currentMember = member2;
        } else {
            return "It's not your turn";
        }

        if (currentMember.isDefeated()) {
            String battleLog =  "This member is defeated and cannot defend.";
            turnCount++;
            return battleLog;
        }
        currentMember.defend();
        String battleLog = currentMember.getName() + " defends";
        currentMember.reduceSkillCooldown();

        turnCount++;
        return battleLog;
    }

    public String playerSkill() {
        if (!isBattleStarted) {
            return "Battle not started";
        }

        CrewMember currentMember;
        CrewMember allyMember;
        if (isMember1Turn()) {
            currentMember = member1;
            allyMember = member2;
        } else if (isMember2Turn()) {
            currentMember = member2;
            allyMember = member1;
        } else {
            return "It's not your turn";
        }
        if (currentMember.isDefeated()) {
            String battleLog =  "This member is defeated and cannot use skill.";
            turnCount++;
            return battleLog;
        }

        if (!currentMember.canUseSkill()) {
            return currentMember.getName() + "'s skill is on cooldown for " + currentMember.getSKillCooldown() + " turn(s).";
        }

        String battleLog = currentMember.useSkill(threat, allyMember);
        currentMember.enableSkillCooldown();

        if (threat.isDefeated()) {
            dropReward();
            return battleLog + "\nThreat Defeated! Mission Completed!";
        }
        turnCount++;
        return battleLog;
    }

    public String threatTurn() {
        if (!isBattleStarted) {
            return "Battle not started";
        }
        if (!isThreatTurn()) {
            return "It's not threat's turn";
        }
        if (threat.isDefeated()) {
            dropReward();
            return threat.getName() + " Defeated! Mission Completed!";
        }
        String battleLog = threat.performTurn(member1, member2);

        member1.resetShield();
        member2.resetShield();

        if (isCrewDefeated(member1, member2)) {
            return battleLog + "\nCrew Defeated! Mission Failed!";
        }
        turnCount++;
        return battleLog;
    }

    private void dropReward() {
        if (!member1.isDefeated()) {
            member1.gainExperience(rewardXP);
        }
        if (!member2.isDefeated()) {
            member2.gainExperience(rewardXP);
        }
        if (!member1.isDefeated() && !member2.isDefeated()) {
            CrewDatabase.getInstance().addCredit(rewardCredit);
        }
    }

    public boolean retryBattleWithNewTeam(CrewMember newMember1, CrewMember newMember2) {
        if (newMember1 == null || newMember2 == null) {
            return false;
        }
        if (newMember1.isDead() || newMember2.isDead()) {
            return false;
        }
        member1 = newMember1;
        member2 = newMember2;

        if (!member1.enoughEnergyForMission(energyCost)) {
            return false;
        }
        if (!member2.enoughEnergyForMission(energyCost)) {
            return false;
        }
        member1.useEnergy(energyCost);
        member2.useEnergy(energyCost);

        turnCount = 1;
        isBattleStarted = true;
        return true;
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
        return rewardXP * difficulty;
    }
    public int getRewardCredit() {
        return rewardCredit * difficulty;
    }

    public Threat getThreat() {
        return threat;
    }
    public CrewMember getMember1() {
        return member1;
    }
    public CrewMember getMember2() {
        return member2;
    }
    public int getTurnCount() {
        return turnCount;
    }
    public boolean isMember1Turn() {
        return turnCount % 3 == 1;
    }
    public boolean isMember2Turn() {
        return turnCount % 3 == 2;
    }
    public boolean isThreatTurn() {
        return turnCount % 3 == 0;
    }
    public String getCurrentTurn() {
        if (isMember1Turn()) {
            return member1.getName() + "'s turn";
        } else if (isMember2Turn()) {
            return member2.getName() + "'s turn";
        } else {
            return threat.getName() + "'s turn";
        }
    }
}

