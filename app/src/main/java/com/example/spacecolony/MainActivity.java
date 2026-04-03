package com.example.spacecolony;

public class MainActivity {
    public static void main(String[] args) {
        Colony team = new Colony();

        CrewMember member1 = new CrewMember("Alice", Role.Pilot);
        CrewMember member2 = new CrewMember("Bob", Role.Engineer);
        CrewMember member3 = new CrewMember("Clara", Role.Medic);

        team.addCrewMember(member1);
        team.addCrewMember(member2);
        team.addCrewMember(member3);

        team.displayCrewMembers();
    }
}