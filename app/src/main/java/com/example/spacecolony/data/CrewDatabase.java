package com.example.spacecolony.data;

import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Medic;
import com.example.spacecolony.model.Pilot;
import com.example.spacecolony.model.Soldier;
import com.example.spacecolony.model.Scientist;
import com.example.spacecolony.model.Engineer;
import com.example.spacecolony.model.Team;
import com.example.spacecolony.model.TraitRarity;
import com.example.spacecolony.model.Trait;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CrewDatabase {
    private ArrayList<CrewMember> crewList;
    private Random random;
    private static CrewDatabase instance;
    private int credits;
    private int recruitedCount;
    private int totalCrewRecruit;
    private int totalMissionWon;
    private int totalMissionLost;

    private String[] possibleNames = {
            "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry", "Ivy", "Jack", "Ethan"
    };
    public CrewDatabase() {
        crewList = new ArrayList<>();
        random = new Random();
        credits = 10000;
        recruitedCount = 0;
        totalCrewRecruit = 0;
        totalMissionWon = 0;
        totalMissionLost = 0;
        loadDefaultCrewList();
    }

    public static CrewDatabase getInstance() {
        if (instance == null) {
            instance = new CrewDatabase();
        }
        return instance;
    }

    private void loadDefaultCrewList() {
        crewList.add(new Scientist("David"));
        crewList.add(new Soldier("Ethan"));
        totalCrewRecruit += 2;
    }

    public ArrayList<CrewMember> getCrewList() {
        return crewList;
    }

    public CrewMember getCrewMemberByName(String name) {
        for (CrewMember member : crewList) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member;
            }
        }
        return null;
    }
    public CrewMember getCrewMemberByIndex(int index) {
        if (index >= 0 && index < crewList.size()) {
            return crewList.get(index);
        }
        return null;
    }

    public void displayCrewMemberList() {
        System.out.println("Crew List:");
        for (CrewMember member: crewList) {
            System.out.println(member.getName());
        }
    }
    public void addCrewMember(CrewMember member) {
        crewList.add(member);
    }
    public void removeCrewMember(CrewMember member) {
        crewList.remove(member);
    }
    public int getCrewSize() {
        return crewList.size();
    }
    public void displayCrewList() {
        System.out.println("Crew List:");
        for (int i = 0; i < crewList.size(); i++) {
            System.out.println((i + 1) + ". " + crewList.get(i).getName());
        }
    }

    // recruiting system: player choose a role and spent resources => random crew member
    public CrewMember recruitCrewMember(String role) {
        String randomName = generateRandomName();
        CrewMember newMember = null;
        int cost = getRecruitedCost();

        if (credits < cost) {
            return null;
        }
        if (crewList.size() >= possibleNames.length) {
            return null;
        }

        if (role.equalsIgnoreCase("Medic")) {
            newMember = new Medic(randomName);
        } else if (role.equalsIgnoreCase("Pilot")) {
            newMember = new Pilot(randomName);
        } else if (role.equalsIgnoreCase("Soldier")) {
            newMember = new Soldier(randomName);
        } else if (role.equalsIgnoreCase("Scientist")) {
            newMember = new Scientist(randomName);
        } else if (role.equalsIgnoreCase("Engineer")) {
            newMember = new Engineer(randomName);
        }

        if (newMember != null) {
            crewList.add(newMember);
            credits -= cost;
            recruitedCount++;
            totalCrewRecruit++;
        }

        return newMember;
    }

    // using separate function to make sure generated names are unique
    public String generateRandomName() {
        String randomName;
        boolean isUnique;
        do {
            randomName = possibleNames[random.nextInt(possibleNames.length)];
            isUnique = true;
            for (CrewMember member : crewList) {
                if (member.getName().equalsIgnoreCase(randomName)) {
                    isUnique = false;
                    break;
                }
            }
        } while (!isUnique);
        return randomName;
    }

    public Team createTeam() {
        Team team = new Team();
        Scanner sc = new Scanner(System.in);

        System.out.println("Team Selection:");

        while (team.getTeamSize() < 2) {
            displayCrewMemberList();
            System.out.println("Selected: " + team.getTeamSize() + "/2");
            System.out.println("Select a crew member (1-" + crewList.size() + "): ");
            int choice = sc.nextInt();

            if (choice < 1 || choice > crewList.size()) {
                System.out.println("Invalid choice. Please choose again!");
                continue;
            }

            if (team.contains(crewList.get(choice - 1))) {
                System.out.println("Crew member is already in the team.");
                continue;
            }
            team.addMember(crewList.get(choice - 1));
            System.out.println(crewList.get(choice - 1).getName() + " is added to the team.\n");
        }
        return team;
    }

    public boolean spendCredits(int amount) {
        if (credits >= amount) {
            credits -= amount;
            return true;
        }
        return false;
    }

    public TraitRarity getRandomTraitRarity() {
        int chance = random.nextInt(100);
        if (chance < 50) {
            return TraitRarity.COMMON;
        } else if (chance < 80) {
            return TraitRarity.RARE;
        } else {
            return TraitRarity.EPIC;
        }
    }
    public Trait getRandomTrait() {
        TraitRarity rarity = getRandomTraitRarity();
        ArrayList<Trait> currentTraits = new ArrayList<>();

        for (Trait trait : Trait.values()) {
            if (trait.getRarity() == rarity) {
                currentTraits.add(trait);
            }
        }
        return currentTraits.get(random.nextInt(currentTraits.size()));
    }

    public void addCredit(int amount) {
        credits += amount;
    }
    public void recordCrewRecruit() {
        totalCrewRecruit++;
    }
    public void recordMisionWon() {
        totalMissionWon++;
    }
    public void recordMissionLost() {
        totalMissionLost++;
    }

    public int getCredits() {
        return credits;
    }
    public int getRecruitedCount() {
        return recruitedCount;
    }
    public int getRecruitedCost() {
        if (recruitedCount >= 8) {
            return 999999999;
        }
        return 50 + recruitedCount * 50;
    }
    public int getTotalCrewRecruit() {
        return totalCrewRecruit;
    }
    public int getTotalMissionWon() {
        return totalMissionWon;
    }
    public int getTotalMissionLost() {
        return totalMissionLost;
    }
    public boolean isRecruitSoftCapReached() {
        return recruitedCount >= 8;
    }

    public String getAllStatistic() {
        return "Total Crew Recruited: " + totalCrewRecruit + "\n" +
                "Total Mission Won: " + totalMissionWon + "\n" +
                "Total Mission Lost: " + totalMissionLost;
    }
}
