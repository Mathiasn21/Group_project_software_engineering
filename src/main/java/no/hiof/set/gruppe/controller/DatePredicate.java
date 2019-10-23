package no.hiof.set.gruppe.controller;

import java.time.LocalDate;

@FunctionalInterface
public interface DatePredicate {
    boolean isBefore(LocalDate date);
}
