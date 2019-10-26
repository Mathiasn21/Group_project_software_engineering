package no.hiof.set.gruppe.Exceptions;

/**
 * A custom exception that represents a malformed class format.
 */
public final class DataFormatException extends Exception {
    public DataFormatException(){
        super("Exception: Wrong Data Object format.");
    }
}