package no.hiof.gruppefire.data;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * InputValidation is a class that handles inputs from a user.
 *
 * @author Gruppe4
 */

public class InputValidation {

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
    public static boolean arrangementInputValidation(String name, String sport, int participants, String adress, boolean gruppe, LocalDate startDate, LocalDate endDate) {

        int validations = 0;

        if(Pattern.matches("[A-Za-z0-9 ]+", name) && name.length() < 30 && name.length() > 2)
            validations++;

        if(sport == "Fotball" || sport == "Basketball" || sport == "Friidrett" || sport == "Sykkelritt" || sport == "Skirenn" || sport == "Annet")
            validations++;

        if(participants < 1000000 && participants >= 0)
            validations++;

        if(Pattern.matches("[A-Za-z0-9 ]+", adress) && adress.length() < 100)
            validations++;

        if(startDate.isBefore(endDate) || startDate.isEqual(endDate))
            validations++;

        if(validations == 5) {
            return true;
        } else {
            return false;
        }
    }
}

