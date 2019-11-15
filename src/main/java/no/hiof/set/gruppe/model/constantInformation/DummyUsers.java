package no.hiof.set.gruppe.model.constantInformation;

/*Guide
 * 1. Import Statements
 * 2. Constants
 * 3. Contracts
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.IBaseEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public enum DummyUsers implements IBaseEntity {
    // --------------------------------------------------//
    //                2.Constants                        //
    // --------------------------------------------------//
    USER1("Nub"),
    USER2("Prætorius"),
    USER3("Odd-Leif"),
    USER4("Gauda"),
    USER5("Leif-Olav"),
    USER6("Oddbjørg"),
    USER7("Bønne"),
    USER8("Rosine"),
    USER9("Fritz"),
    USER10("Grissilde"),
    USER11("Gjær"),
    ;
    private final String user;
    private final String ID;


    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
    /**
     * @param user String
     */
    @Contract(pure = true)
    DummyUsers (@NotNull String user){
        this.user = user;
        this.ID = UUID.randomUUID().toString();
    }

    /**
     * @return String
     */
    @Contract(pure = true)
    @Override
    public String toString(){
        return user;
    }

    @Override
    public String getID() {return this.ID;}
}
