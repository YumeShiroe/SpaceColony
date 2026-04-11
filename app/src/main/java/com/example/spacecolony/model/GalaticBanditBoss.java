package com.example.spacecolony.model;

import java.util.Random;

public class GalaticBanditBoss extends Threat {
    private Random random;
    private int ColpiDiTaglioCoolDown;
    private int SezionaturaDiElefanteCoolDown;
    private int DisposalCoolDown;

    public GalaticBanditBoss(int missionDifficulty) {
        super("Jane Bandit Boss", 50, 3, missionDifficulty);
        this.ColpiDiTaglioCoolDown = 2;
        this.SezionaturaDiElefanteCoolDown = 3;
        this.DisposalCoolDown = 5;
        this.random = new Random();
    }

    @Override
    public void attack(CrewMember target) {
        super.attack(target);
    }
    public String ColpiDiTaglio(CrewMember target1, CrewMember target2) {
        CrewMember target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower);
        }
        target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 1);
        }
        target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 2);
        }

        ColpiDiTaglioCoolDown = 1;
        return name + " uses Palermitan Style: Colpi di Taglio";
    }
    public String SezionaturaDiElefante(CrewMember target1, CrewMember target2) {
        CrewMember target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 2);
        }
        target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 2);
        }
        target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 2);
        }
        target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 1);
        }

        ColpiDiTaglioCoolDown = 2;
        return name + " uses Palermitan Style: Sezionatura di Elefante";
    }
    public String Disposal(CrewMember target1, CrewMember target2) {
        CrewMember target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 2);
        }
        target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 2);
        }
        target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 2);
        }
        target = chooseTarget(target1, target2);
        if (target != null) {
            target.takeDamage(attackPower - 2);
        }
        if (target != null) {
            target.takeDamage(attackPower - 2);
        }

        ColpiDiTaglioCoolDown = 10;
        return name + " uses Palermitan Style: Disposal";
    }


    @Override
    public void usingSkills(CrewMember target1, CrewMember target2) {
        performTurn(target1, target2);
    }

    @Override
    public String performTurn(CrewMember target1, CrewMember target2) {
        if (ColpiDiTaglioCoolDown > 0) {
            ColpiDiTaglioCoolDown--;
        }
        if (SezionaturaDiElefanteCoolDown > 0) {
            SezionaturaDiElefanteCoolDown--;
        }
        if (DisposalCoolDown > 0) {
            DisposalCoolDown--;
        }
        if (DisposalCoolDown == 0) {
            return Disposal(target1, target2);
        }
        if (ColpiDiTaglioCoolDown == 0) {
            int ColpiDiTaglioSkillChance = random.nextInt(100);

            if (ColpiDiTaglioSkillChance < 30) {
                return ColpiDiTaglio(target1, target2);
            }
        }
        if (SezionaturaDiElefanteCoolDown == 0) {
            int SezionaturaDiElefanteSkillChance = random.nextInt(100);

            if (SezionaturaDiElefanteSkillChance < 30) {
                return SezionaturaDiElefante(target1, target2);
            }
        }

        CrewMember target = chooseTarget(target1, target2);

        if (target != null) {
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

    public int getColpiDiTaglioCoolDown() {
        return ColpiDiTaglioCoolDown;
    }
    public int getSezionaturaDiElefanteCoolDown() {
        return SezionaturaDiElefanteCoolDown;
    }
    public int getDisposalCoolDown() {
        return DisposalCoolDown;
    }
}
