package no.hiof.set.gruppe.model;

/**
 * Grab all data from a given data object. Formatted into an array
 * Ensures easy access and reduced repetition of getters.
 */
@FunctionalInterface
public interface IGetAllData {
    String[] getAllDataAsStringArr();
}
