package no.hiof.set.gruppe.core.predicates;

/*Guide
 * 1. Import Statements
 * 2. Constants
 * 3. Contracts
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.model.Arrangement;
import org.jetbrains.annotations.Contract;

import java.util.Comparator;

/**
 * Contains references for sorting a list, given a {@link Comparator}.
 * Based on command pattern.
 * @see <a href="https://en.wikipedia.org/wiki/Command_pattern">https://en.wikipedia.org/wiki/Command_pattern</a>
 * For more information.
 */
public enum ArrangementSort {

    // --------------------------------------------------//
    //                2.Constants                        //
    // --------------------------------------------------//
    COMP_NAME_ASC((ob1, ob2) -> ob1.getName().toLowerCase().compareTo(ob2.getName().toLowerCase())),
    COMP_DATE_ASC((ob1, ob2) -> ob1.getStartDate().compareTo(ob2.getStartDate())),
    COMP_PARTICIPANTS_ASC((ob1, ob2) -> Integer.compare(ob1.getParticipants(), ob2.getParticipants())),
    COMP_SPORT_ASC((ob1, ob2) -> ob1.getSport().toLowerCase().compareTo(ob2.getSport().toLowerCase()));

    private Comparator<? super Arrangement> comparator;

    // --------------------------------------------------//
    //                3.Contracts                        //
    // --------------------------------------------------//
    /**
     * @param comparator {@link Comparator}
     */
    @Contract(pure = true)
    ArrangementSort(Comparator<? super Arrangement> comparator){
        this.comparator = comparator;
    }

    /**
     * @return String
     */
    @Contract(pure = true)
    public Comparator<? super Arrangement> getComparator() {return comparator;}
}
