package no.hiof.set.gruppe.core.entities;

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
import no.hiof.set.gruppe.core.interfaces.IBaseEntity;
import no.hiof.set.gruppe.core.entities.constantInformation.DummyUsers;

import java.util.*;

public class Group implements IBaseEntity {

    // --------------------------------------------------//
    //                2.Local Fields                     //
    // --------------------------------------------------//
    private String name;
    private final String ID;
    private List<DummyUsers> members = new ArrayList<>();

    // --------------------------------------------------//
    //                3.Constructors                     //
    // --------------------------------------------------//
    public Group(){
        this("");
    }

    public Group(String name) {this(name, UUID.randomUUID().toString());}
    public Group(String name, String ID) {
        this.name = name;
        this.members = new ArrayList<>();
        this.ID = ID;
    }

    // --------------------------------------------------//
    //                4.Public Methods                   //
    // --------------------------------------------------//
    public void addMember (DummyUsers dummyUser){members.add(dummyUser);}

    public void addAllMembers(List<DummyUsers> userlist){
        members.addAll(userlist);
    }

    // --------------------------------------------------//
    //                5.Public Getter Methods            //
    // --------------------------------------------------//
    public String getName() {
        return name;
    }
    public List<DummyUsers> getMembers() { return members; }
    public String getMembersAsPrettyString(){
        StringBuilder res = new StringBuilder();
        for(DummyUsers member : members) res.append(member).append("\n");
        return res.toString();
    }

    // --------------------------------------------------//
    //                6.Public Setter Methods            //
    // --------------------------------------------------//
    public void setName(String name) { this.name = name; }
    public void setMembers(ArrayList<DummyUsers> member) { this.members = member; }
    public void removeMembers(DummyUsers ...members){this.members.removeAll(Arrays.asList(members));}

    // --------------------------------------------------//
    //                7.Overridden Methods               //
    // --------------------------------------------------//
    @Override
    public String toString(){
        return name;
    }

    @Override
    public String getID() { return ID; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;

        Group group = (Group) o;

        if (!Objects.equals(name, group.name)) return false;
        if (!Objects.equals(ID, group.ID)) return false;
        return Objects.equals(members, group.members);
    }
}
