package no.hiof.set.gruppe.core.interfaces;

import java.time.LocalDate;

@FunctionalInterface
public interface DatePredicate {
    /**
     * @param startDate {@link LocalDate}
     * @param endDate {@link LocalDate}
     * @return boolean
     */
    boolean testDate(LocalDate startDate, LocalDate endDate);
}