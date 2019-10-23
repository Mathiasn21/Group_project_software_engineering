package no.hiof.set.gruppe.model;

import org.jetbrains.annotations.Contract;

import java.time.LocalDate;

public enum DateTest {

    TestExpired("Expirert", (start, end) -> end.isBefore(LocalDate.now())),
    TestOngoing("Pågående", (startDate, endDate) -> {
        LocalDate now = LocalDate.now();
        return endDate.isAfter(now) && startDate.isBefore(now);}),

    TestFuture("Fremtidige",(start, end) -> end.isAfter(LocalDate.now()) && start.isAfter(LocalDate.now())),
    ALL("Alle", (startDate, endDate) -> true);

    private final String name;
    private final DatePredicate predicate;

    @Contract(pure = true)
    DateTest(String name, DatePredicate predicate) {
        this.name = name;
        this.predicate = predicate;
    }

    @Contract(pure = true)
    @Override
    public String toString(){return name;}

    public boolean execute(LocalDate startDate, LocalDate endDate){return predicate.testDate(startDate, endDate);}
}
