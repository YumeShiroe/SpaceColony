package com.example.spacecolony.model;

public enum Trait {
    TOUGH("tough", TraitRarity.COMMON),
    STRONG("Strong", TraitRarity.COMMON),
    DILIGENT("Diligent", TraitRarity.RARE),
    GENIUS("Genius", TraitRarity.EPIC);
    private String name;
    private TraitRarity rarity;

    Trait(String name, TraitRarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }
    public String getName() {
        return name;
    }
    public TraitRarity getRarity() {
        return rarity;
    }
}
