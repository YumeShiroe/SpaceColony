package com.example.spacecolony.model;

public abstract class CrewMember {
    protected String name;

    protected int health;
    protected int maxHealth;
    protected int energy;
    protected int maxEnergy;

    protected int level;
    protected int experience;
    protected int nextLevelExperience;
    protected int skillPoints;

    protected int attackPower;
    protected int defensePower;

    public CrewMember(String name, int maxHealth, int maxEnergy, int attackPower, int defensePower) {
        this.name = name;

        this.level = 1;
        this.experience = 0;
        this.nextLevelExperience = 20;
        this.skillPoints = 0;

        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;

        this.attackPower = attackPower;
        this.defensePower = defensePower;
    }

    public void gainExperience(int amount) {
        experience += amount;

        if (experience >= nextLevelExperience) {
            experience -= nextLevelExperience;
            levelUp();
        }
    }

    public void levelUp() {
        level++;
        skillPoints++;
        nextLevelExperience += 5;

        maxHealth += getHealthGrowth();
        maxEnergy += getEnergyGrowth();

        health = maxHealth;
        energy = maxEnergy;

        attackPower += getAttackGrowth();
        defensePower += getDefenseGrowth();
    }

    public int act() {
        return attackPower;
    }

    public int defend(int incomingAttackDamage) {
        int reducedDamage = incomingAttackDamage - defensePower;
        if (reducedDamage < 0) {
            reducedDamage = 0;
        }

        health -= reducedDamage;

        if (health < 0) {
            health = 0;
        }

        return reducedDamage;
    }

    public void useEnergy(int amount) {
        energy -= amount;

        if (energy < 0) {
            energy = 0;
        }
    }

    public boolean enoughEnergyForMission(int amount) {
        return energy >= amount;
    }

    // general method for crew member
    public abstract int getHealthGrowth();
    public abstract int getEnergyGrowth();
    public abstract int getDefenseGrowth();
    public abstract int getAttackGrowth();
    public abstract String getRoleName();

    // getters
    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }
    public int getExperience() {
        return experience;
    }
    public int getExperienceToNextLevel() {
        return nextLevelExperience - experience;
    }
    public int getSkillPoints() {
        return skillPoints;
    }
    public int getHealth() {
        return health;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getEnergy() {
        return energy;
    }
    public int getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public String toString() {
        return "Name: " + name + "(" + getRoleName() + ")\n" +
                "Level: " + level + "\n" +
                "Experience: " + experience + "/" + nextLevelExperience + "\n" +
                "Skill Points: " + skillPoints + "\n" +
                "Health: " + health + "/" + maxHealth + "\n" +
                "Energy: " + energy + "/" + maxEnergy;
    }
}
