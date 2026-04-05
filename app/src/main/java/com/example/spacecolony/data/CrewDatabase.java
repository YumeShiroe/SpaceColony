package com.example.spacecolony.data;

import com.example.spacecolony.model.CrewMember;
import com.example.spacecolony.model.Medic;
import com.example.spacecolony.model.Pilot;
import com.example.spacecolony.model.Soldier;
import com.example.spacecolony.model.Scientist;
import com.example.spacecolony.model.Engineer;

import java.util.ArrayList;
import java.util.Random;

public class CrewDatabase {
    private ArrayList<CrewMember> crewList;
    private Random random;

    private String[] possibleNames = {
            "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry", "Ivy", "Jack"
    };
    public CrewDatabase() {
        crewList = new ArrayList<>();
        Random random = new Random();
        loadDefaultCrewList();
    }

    private void loadDefaultCrewList() {
        crewList.add(new Scientist("David"));
        crewList.add(new Soldier("Ethan"));
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
        for (CrewMember member : crewList) {
            System.out.println(member.getName());
        }
    }

    // recruiting system: player choose a role and spent resources => random crew member
    public CrewMember recruitCrewMember(String role) {
        String randomName = generateRandomName();
        CrewMember newMember = null;

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
        } while (!isUnique); {
            return randomName;
        }
    }
}
