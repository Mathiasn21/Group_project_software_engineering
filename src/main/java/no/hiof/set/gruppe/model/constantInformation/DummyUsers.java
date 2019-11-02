package no.hiof.set.gruppe.model.constantInformation;

/*Guide
 * 1. Import Statements
 * 2. Constants
 * 3. Contracts
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum DummyUsers {
    // --------------------------------------------------//
    //                2.Constants                        //
    // --------------------------------------------------//
    USER1("Arne"),
    USER2("Kåre"),
    USER3("Oda"),
    USER4("Nisse"),
    USER5("Nils"),
    USER6("Per"),
    USER7("Rune"),
    USER8("Bernt"),
    USER9("Bjørn"),
    USER10("Line"),
    USER11("Geir"),
    ;
    final String user;


    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
    /**
     * @param user String
     */
    @Contract(pure = true)
    DummyUsers (@NotNull String user){
        this.user = user;
    }

    /**
     * @return String
     */
    @Contract(pure = true)
    @Override
    public String toString(){
        return user;
    }
}
