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
    protected int shield;

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

        while (experience >= nextLevelExperience) {
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

    public void act(Threat threat) {
        System.out.println(name + " attacks " + threat.getName());
        threat.takeDamage(attackPower);
    }

    public void defend() {
        shield = defensePower;
        System.out.println(name + " gains a shield of " + shield);
    }

    public void resetShield() {
        shield = 0;
        System.out.println(name + " loses shield");
    }

    public void takeDamage(int amount) {
        // shield system: the damage is reduced by the shield value
        // the remaining damage will affect the Crew Member health

        int remainingDamage = amount;
        if (shield > 0) {
            if (shield >= remainingDamage) {
                shield -= remainingDamage;
                remainingDamage = 0;
            } else {
                remainingDamage -= shield;
                shield = 0;
            }
        }

        health -= remainingDamage;
        if (health < 0) {
            health = 0;
        }

        System.out.println(name + " takes " + remainingDamage + " damage.");
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

    public boolean isDefeated() {
        return health <= 0;
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
    public int getShield() {
        return shield;
    }
    public int getAttackPower() {
        return attackPower;
    }
    public int getDefensePower() {
        return defensePower;
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
