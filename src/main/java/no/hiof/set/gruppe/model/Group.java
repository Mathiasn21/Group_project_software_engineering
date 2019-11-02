package no.hiof.set.gruppe.model;

import no.hiof.set.gruppe.model.constantInformation.DummyUsers;

import java.util.ArrayList;

public class Group implements IGetAllData {

    private String name;
    private int id;
    private ArrayList<DummyUsers> members;

    public Group(){
        this("",0);
    }

    public Group(String name, int id) {
        this.name = name;
        this.id = id;
        this.members = new ArrayList<>();
    }

    public void addmember (DummyUsers dummyUser){
        members.add(dummyUser);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<DummyUsers> getMedlemmer() {
        return members;
    }

    public void setMedlemmer(ArrayList<DummyUsers> medlemmer) {
        this.members = medlemmer;
    }

    @Override
    public String[] getAllDataAsStringArr() {
        return new String[0];
    }
}
