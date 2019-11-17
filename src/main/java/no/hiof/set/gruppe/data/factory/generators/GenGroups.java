package no.hiof.set.gruppe.data.factory.generators;

import no.hiof.set.gruppe.model.Group;

public class GenGroups implements GenData {
    private final String[] names = {
    "Annihilators", "Avengers", "Bad to The Bone", "Black Panthers",
    "Black Widows", "Blitzkrieg", "Braindead Zombies", "Brewmaster Crew ",
    "Brute Force ", "Butchers", "Chaos", "Chargers", "Dominators",
    "Chernobyl", "Collision Course", "Deathwish", "Defenders", "Demolition",
    "Desert", "Divide"
    };

    @Override
    public Group createData() {return new Group(genNames());}
    private String genNames(){return names[(int) (Math.random()*names.length)];}
}
