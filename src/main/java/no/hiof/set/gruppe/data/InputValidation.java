package no.hiof.set.gruppe.data;

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
    public static boolean arrangementInputValidation(String name, String sport, String participants, String adress, boolean gruppe, LocalDate startDate, LocalDate endDate) {

        int validations = 0;


        //Could be done with:[^\p{L}\p{Nd}]+
        //
        if(Pattern.matches("[A-Za-z0-9]+", name) && name.length() < 50 && name.length() > 2)
            validations++;
        else
            System.out.println("Sett inn et gyldig navn");

        if(sport != null)
            validations++;
        else
            System.out.println("Velg en idrett");

        if(Pattern.matches("[0-9 ]+", participants)){
            int participantsInt = Integer.parseInt(participants);
            if(participantsInt < 1000000 && participantsInt >= 0)
                validations++;
            else
                System.out.println("Et arrangement må ha 0 eller flere deltakere");
        }
        else
            System.out.println("Deltakere må være et nummmer");

        if(Pattern.matches("[A-Za-z0-9 ]+", adress) && adress.length() < 100)
            validations++;
        else
            System.out.println("Sett inn gydlig adresse");

        if(startDate.isBefore(endDate) || startDate.isEqual(endDate))
            validations++;
        else
            System.out.println("Sett inn gydlig dato");

        if(validations == 5) {
            return true;
        } else {
            return false;
        }
    }
}

