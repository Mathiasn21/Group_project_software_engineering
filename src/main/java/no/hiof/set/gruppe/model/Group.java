package no.hiof.set.gruppe.model;

/*Guide
 * 1. Import Statements
 * 2. Local Fields
 * 3. Constructors
 * 4. Public Methods
 * 5. Public Getter Methods
 * 6. Public Setter Methods
 * 7. Overridden Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import javafx.collections.ObservableList;
import no.hiof.set.gruppe.model.constantInformation.DummyUsers;

import java.util.ArrayList;

public class Group implements IGetAllData {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String name;
    private int id;
    private ArrayList<DummyUsers> members;

    // --------------------------------------------------//
    //                3.Constructors                     //
    // --------------------------------------------------//
    public Group(){
        this("",0);
    }

    public Group(String name, int id) {
        this.name = name;
        this.id = id;
        this.members = new ArrayList<>();
    }

    // --------------------------------------------------//
    //                4.Public Methods                   //
    // --------------------------------------------------//
    public void addOnemember (DummyUsers dummyUser){
        members.add(dummyUser);
    }

    public void addMulipleMembers(ObservableList<DummyUsers> userlist){
        for(DummyUsers user : userlist)
            members.add(user);
    }

    // --------------------------------------------------//
    //                5.Public Getter Methods            //
    // --------------------------------------------------//
    public String getName() {
        return name;
    }
    public ArrayList<DummyUsers> getMembersr() { return members; }
    public int getId() { return id; }

    // --------------------------------------------------//
    //                6.Public Setter Methods            //
    // --------------------------------------------------//
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMembers(ArrayList<DummyUsers> member) { this.members = member; }

    // --------------------------------------------------//
    //                7.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public String[] getAllDataAsStringArr() { return new String[0]; }

    @Override
    public String toString(){
        return name + " " + members;
    }
}
