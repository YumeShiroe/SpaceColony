package com.example.spacecolony.model;

public abstract class Threat {
    protected String name;
    protected int health;
    protected int attackPower;

    public Threat(String name, int baseHealth, int baseAttackPower, int missionDifficulty) {
        this.name = name;
        this.health = baseHealth + (missionDifficulty * 15);
        this.attackPower = baseAttackPower + (missionDifficulty * 2);
    }

    public abstract void usingSkills(CrewMember target1, CrewMember target2);
    public abstract String performTurn(CrewMember member1, CrewMember member2);

    public void attack(CrewMember target) {
        System.out.println(name + " attacks " + target.getName());
        target.takeDamage(attackPower);
    }

    public void takeDamage(int amount) {
        health -= amount;

        if (health < 0) {
            health = 0;
        }
    }

    public boolean isDefeated() {
        return health <= 0;
    }

    // getters
    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public int getAttackPower() {
        return attackPower;
    }
}
