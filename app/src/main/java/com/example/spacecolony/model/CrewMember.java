package com.example.spacecolony.model;

import com.example.spacecolony.model.Trait;

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
    protected boolean isDead;
    protected int skillCooldown;
    private int missionWon;
    private int timesDefeated;
    private int damageDealt;
    private int damageTaken;
    private int skillUsed;
    protected Trait trait;
    protected int bonusHealthGrowth;
    protected int bonusAttackGrowth;

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

        this.skillCooldown = 0;

        this.isDead = false;

        this.missionWon = 0;
        this.timesDefeated = 0;
        this.damageDealt = 0;
        this.damageTaken = 0;
        this.skillUsed = 0;

        this.trait = null;
        this.bonusHealthGrowth = 0;
        this.bonusAttackGrowth = 0;
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

        maxHealth += getHealthGrowth() + bonusHealthGrowth;
        maxEnergy += getEnergyGrowth();

        health = maxHealth;
        energy = maxEnergy;

        attackPower += getAttackGrowth() + bonusAttackGrowth;
        defensePower += getDefenseGrowth();
    }

    public void act(Threat threat) {
        //System.out.println(name + " attacks " + threat.getName());
        threat.takeDamage(attackPower);
        recordDamageDealt(attackPower);
    }

    public void defend() {
        shield = defensePower;
        //System.out.println(name + " gains a shield of " + shield);
    }

    public void resetShield() {
        shield = 0;
        System.out.println(name + " loses shield");
    }

    public void takeDamage(int amount) {
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
        recordDamageTaken(remainingDamage);

        if (health < 0) {
            health = 0;
        }

        //System.out.println(name + " takes " + remainingDamage + " damage.");
    }

    public void useEnergy(int amount) {
        energy -= amount;

        if (energy < 0) {
            energy = 0;
        }
    }

    public void die() {
        isDead = true;
        health = 0;
    }
    public void revive() {
        isDead = false;
        health = maxHealth;
        energy = maxEnergy;
    }

    public void addTrait(Trait trait) {
        this.trait = trait;
        if (trait == Trait.TOUGH) {
            maxHealth += 4;
            health += 4;
        } else if (trait == Trait.STRONG) {
            attackPower += 2;
        } else if (trait == Trait.DILIGENT) {
            bonusAttackGrowth += 1;
            bonusHealthGrowth += 1;
        } else if (trait == Trait.GENIUS) {
            bonusAttackGrowth += 2;
            bonusHealthGrowth += 2;
        }
    }

    public abstract String useSkill(Threat threat, CrewMember ally);

    public boolean canUseSkill() {
        return skillCooldown == 0;
    }
    public void reduceSkillCooldown() {
        if (skillCooldown > 0) {
            skillCooldown--;
        }
    }
    public void enableSkillCooldown() {
        skillCooldown = 2;
    }

    public boolean enoughEnergyForMission(int amount) {
        return energy >= amount;
    }

    public boolean isDefeated() {
        return isDead || health <= 0;
    }
    public void recordMissionWon() {
        missionWon++;
    }
    public void recordDefeat() {
        timesDefeated++;
    }
    public void recordDamageDealt(int amount) {
        if (amount >= 0) {
            damageDealt += amount;
        }
    }
    public void recordDamageTaken(int amount) {
        if (amount >= 0) {
            damageTaken += amount;
        }
    }
    public void recordSkillUsed() {
        skillUsed++;
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
        return nextLevelExperience;
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
    public boolean isDead() {
        return isDead;
    }
    public int getSKillCooldown() {
        return skillCooldown;
    }
    public int getMissionWon() {
        return missionWon;
    }
    public int getTimesDefeated() {
        return timesDefeated;
    }
    public int getDamageDeal() {
        return damageDealt;
    }
    public int getDamageTaken() {
        return damageTaken;
    }
    public int getSkillUsed() {
        return skillUsed;
    }
    public String getStatistics() {
        return "Wins: " + missionWon + " | Defeats: " + timesDefeated + "\n" +
                "Damage Dealt: " + damageDealt + " | Damage Taken: " + damageTaken + "\n" +
                "Skill Used: " + skillUsed;
    }
    public Trait getTrait() {
        return trait;
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
