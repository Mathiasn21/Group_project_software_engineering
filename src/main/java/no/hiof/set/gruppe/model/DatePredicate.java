package no.hiof.set.gruppe.model;

import java.time.LocalDate;

@FunctionalInterface
public interface DatePredicate {
    boolean testDate(LocalDate startDate, LocalDate endDate);
}
