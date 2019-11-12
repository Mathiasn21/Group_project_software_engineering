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

public class Group {

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

    public void addMultipleMembers(ObservableList<DummyUsers> userlist){
        members.addAll(userlist);
    }

    // --------------------------------------------------//
    //                5.Public Getter Methods            //
    // --------------------------------------------------//
    public String getName() {
        return name;
    }
    public ArrayList<DummyUsers> getMembers() { return members; }
    public int getId() { return id; }
    public String getMembersAsPrettyString(){
        StringBuilder res = new StringBuilder();
        for(DummyUsers member : members) res.append(member).append("\n");
        return res.toString();
    }

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
    public String toString(){
        return name;
    }
}
