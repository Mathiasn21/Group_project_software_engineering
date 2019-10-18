package no.hiof.set.gruppe.data;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Validation is a class that handles inputs from a user.
 *
 * @author Gruppe4
 */

public class Validation{


    private static final String textNotNullPattern = "(?!^ +$)^.+$";
    private static final String numPatt = "[0-9]+";

    private static final int maxNameL = 50;
    private static final int minNameL = 2;

    /**
     * Validates arrangement input data from user.
     * @param name
     * @param sport
     * @param participants
     * @param adress
     * @param gruppe
     * @param startDate
     * @param endDate
     * @return True if input is valid. False if input is not valid.
     */
    public static boolean ofArrangement(String name, String sport, String participants, String adress, LocalDate startDate, LocalDate endDate) {
        StringBuilder str = new StringBuilder();

        str.append(nameValidation(name));
        str.append(sportValidation(sport));
        str.append(participantsValidation(participants));
        str.append(adressValidation(adress));
        str.append(dateValidation(startDate, endDate));
        return str.length() == 0;
    }

    private static String nameValidation(String name) {
        return regCheck(textNotNullPattern, name) && isBetween(minNameL, maxNameL, name.length()) ? "" : "Sett inn et gyldig navn.\n";
    }

    private static String sportValidation(String sport){return sport != null ? "" : "Velg en idrett.\n";}


    /**
     * Tests if number is between two numbers, inclusive both ends.
     * @param min int
     * @param max int
     * @return boolean
     */
    private static boolean isBetween(int min, int max, int numToTest){
        return min <= numToTest && numToTest >= max;
    }

    private static String adressValidation(String adress){
        return regCheck(textNotNullPattern, adress) && isBetween(minNameL, maxNameL, adress.length()) ? "" : "Sett inn en gydlig adresse.\n";
    }

    private static String dateValidation(LocalDate startDate, LocalDate endDate){
        return startDate.isBefore(endDate) || startDate.isEqual(endDate) ? "" : "Sett inn gyldige datoer.\n";
    }

    private static String participantsValidation(String num){
        String invalidNum = "Sett inn et gyldig antall deltakere\n";
        String invalidNumFormat = "Ugyldig nummer format.\n";

        if(!regCheck(numPatt, num)) return invalidNumFormat;

        int participants = Integer.parseInt(num);
        int participantsMax = 999999;
        int participantsMin = 0;

        return isBetween(participantsMin, participantsMax, participants) ? "" : invalidNum;
    }

    /**
     * General regex tester
     * @param pattern String
     * @param str String
     * @return boolean
     */
    private static boolean regCheck(String pattern, String str) {
        return Pattern.matches(pattern, str);
    }
}