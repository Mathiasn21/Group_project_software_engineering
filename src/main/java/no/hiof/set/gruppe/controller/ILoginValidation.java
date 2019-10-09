package no.hiof.set.gruppe.controller;

public interface ILoginValidation<T extends String> {
    boolean isValidUName(T str);
    boolean isValidPass(T str);
}
