package no.hiof.set.gruppe.util;

import org.jetbrains.annotations.Contract;

import java.time.LocalDate;

/**
 * Contains references for date comparisons, used for testing given dates.
 * Based on {@link LocalDate} class, and the command pattern.
 * @see <a href="https://en.wikipedia.org/wiki/Command_pattern">https://en.wikipedia.org/wiki/Command_pattern</a>
 * For more information.
 */
public enum DateTest {

    TestExpired((start, end) -> end.isBefore(LocalDate.now())),
    TestFuture((start, end) -> start.compareTo(LocalDate.now()) >= 0 && end.compareTo(start) >= 0),
    ALL((startDate, endDate) -> true),

    TestOngoing((startDate, endDate) -> {
        LocalDate now = LocalDate.now();
        return endDate.isAfter(now) && startDate.isBefore(now);});

    private final DatePredicate predicate;

    /**
     * Ensures execution of test.
     * @param predicate {@link DatePredicate}
     */
    @Contract(pure = true)
    DateTest(DatePredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * @param startDate {@link LocalDate}
     * @param endDate {@link LocalDate}
     * @return boolean
     */
    public boolean execute(LocalDate startDate, LocalDate endDate){return predicate.testDate(startDate, endDate);}
}