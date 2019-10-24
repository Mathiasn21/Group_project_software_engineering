package no.hiof.set.gruppe.util;

import no.hiof.set.gruppe.model.Arrangement;
import org.jetbrains.annotations.Contract;

import java.util.Comparator;

public enum ArrangementSort {
    COMP_NAME_ASC("Sorter navn synkende", (ob1, ob2) -> ob1.getName().compareTo(ob2.getName())),
    COMP_DATE_ASC("Sorter dato synkende", (ob1, ob2) -> ob1.getStartDate().compareTo(ob2.getStartDate())),
    COMP_PARTICIPANTS_ASC("Sorter navn synkende", (ob1, ob2) -> Integer.compare(ob1.getParticipants(), ob2.getParticipants())),
    COMP_SPORT_ASC("Sorter navn synkende", (ob1, ob2) -> ob1.getName().compareTo(ob2.getName()));

    private String definition;
    private Comparator<? super Arrangement> comparator;

    @Contract(pure = true)
    ArrangementSort(String definition, Comparator<? super Arrangement> comparator){
        this.definition = definition;
        this.comparator = comparator;
    }

    @Contract(pure = true)
    public String getDefinition() {return definition;}

    @Contract(pure = true)
    public Comparator<? super Arrangement> getComparator() {return comparator;}
}
