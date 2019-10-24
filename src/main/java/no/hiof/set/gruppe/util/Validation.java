package no.hiof.set.gruppe.util;

import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.constantInformation.ValidationResult;
import org.jetbrains.annotations.Contract;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * This class handles backend validation of various data classes.
 * @author Gruppe4
 */
public class Validation{
    private static final String textNotNullPattern = "(?!^ +$)^.+$";
    private static final String numPatt = "[0-9]+";

    private static final int maxNameL = 50;
    private static final int minNameL = 2;
    /**
     * Validates an arrangement
     * @param arrangement {@link Arrangement}
     * @return ValidationResult {@link ValidationResult}
     */
    public static ValidationResult ofArrangement(Arrangement arrangement) {
        StringBuilder str = new StringBuilder();
        String invalidNum = "Sett inn et gyldig antall deltakere\n";

        int participantsMax = 999999;
        int participantsMin = 0;
        String name = arrangement.getName();
        String address = arrangement.getAddress();
        LocalDate startDate = arrangement.getStartDate();
        LocalDate endDate = arrangement.getEndDate();

        str.append(regCheck(textNotNullPattern, name) && isBetween(minNameL, maxNameL, name.length()) ? "" : "Sett inn et gyldig navn.\n");
        str.append(arrangement.getSport() != null ? "" : "Velg en idrett.\n");
        str.append(regCheck(textNotNullPattern, address) && isBetween(minNameL, maxNameL, address.length()) ? "" : "Sett inn en gydlig adresse.\n");
        str.append(startDate.isBefore(endDate) || startDate.isEqual(endDate) ? "" : "Sett inn gyldige datoer.\n");
        str.append(isBetween(participantsMin, participantsMax, arrangement.getParticipants()) ? "" : invalidNum);
        return new ValidationResult(str.toString(), str.length() == 0);
    }

    public static boolean ofNumber(String num){
        return regCheck(numPatt, num);
    }

    /**
     * Tests if number is between two numbers, inclusive both ends.
     * @param min int
     * @param max int
     * @return boolean
     */
    @Contract(pure = true)
    private static boolean isBetween(int min, int max, int numToTest){return min <= numToTest && numToTest <= max;}

    /**
     * General regex tester
     * @param pattern String
     * @param str String
     * @return boolean
     */
    private static boolean regCheck(String pattern, String str) {return Pattern.matches(pattern, str);}
}