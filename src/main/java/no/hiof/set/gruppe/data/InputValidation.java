package no.hiof.set.gruppe.data;

import no.hiof.set.gruppe.model.Arrangement;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * InputValidation is a class that handles inputs from a user.
 *
 * @author Gruppe4
 */

public class InputValidation implements IInputValidation{

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
        /*
        * This whole class can be truncated, where the reg patterns are put onto the top the application.
        * A method could be used to test for the actual string, given pattern.
        * Should use double negation as thi save quite a few cpu cycles.
        *
        * Each if could and should be calling a validation function
        * Every message should just be added with stringbuilder.
        *
        * */


        int validations = 0;


        //[\p{L}\p{Nd}\p{Zs}\p{Po}]+ Converted to double negation as to save some cpu cycles and therefore less strain
        //on the overall memory
        if(!Pattern.matches("\\00", name) && name.length() < 50 && name.length() > 2)
            validations++;
        else
            System.out.println("Sett inn et gyldig navn");

        if(sport != null)
            validations++;
        else
            System.out.println("Velg en idrett!");

        if(Pattern.matches("[0-9 ]+", participants)){
            int participantsInt = Integer.parseInt(participants);
            if(participantsInt < 1000000 && participantsInt >= 0)
                validations++;
            else
                System.out.println("Et arrangement må ha 0 eller flere deltakere!");
        }
        else
            System.out.println("Deltakere må være et nummmer!");

        if(Pattern.matches("[A-Za-z0-9. ]+", adress) && adress.length() < 100)
            validations++;
        else
            System.out.println("Sett inn gydlig adresse!");

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

    @Override
    public boolean isValidArrangement(Arrangement arrangement) {
        return false;
    }
}

