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

    private static final String textNotNullPattern = "[\\00]";
    private static final String numberPattern = "[^0-9]";

    public static boolean arrangementInputValidation(String name, String sport, String participants, String adress, LocalDate startDate, LocalDate endDate) {

        String mainResult = "";

        /*
        * This whole class can be truncated, where the reg patterns are put onto the top the application.
        * A method could be used to test for the actual string, given pattern.
        * Should use double negation as thi save quite a few cpu cycles.
        *
        * Each if could and should be calling a validation function
        * Every message should just be added with stringbuilder.
        *
        * */

        //[\p{L}\p{Nd}\p{Zs}\p{Po}]+ Converted to double negation as to save some cpu cycles and therefore less strain
        //on the overall memory

        mainResult += nameValidation(name) + "\n";
        mainResult += sportValidation(sport) + "\n";
        mainResult += participantsValidation(participants) + "\n";
        mainResult += adressValidation(adress) + "\n";
        mainResult += dateValidation(startDate, endDate) + "\n";

        if(mainResult == ""){
            return true;
        }

        return false;
    }

    private static String nameValidation(String name) {

        String result = "";

        if (!Pattern.matches (textNotNullPattern, name) && name.length() < 50 && name.length() > 2)
            result = "";
        else
            result = "Sett inn et gyldig navn";

        return result;
    }


    private static String sportValidation(String sport){
        String result = "";

        if(sport != null)
            result = "";
        else
            result = "Velg en idrett!";

        return result;
    }

    private static String participantsValidation(String participants){

        String result = "";

        if(Pattern.matches(numberPattern, participants)){

            int participantsInt = Integer.parseInt(participants);

            if(participantsInt < 1000000 && participantsInt >= 0){
                result = "";
            }
            else
                result = "Et arrangement må ha 0 eller flere deltakere";
        }
        else
            result = "Antall deltakere må være et tall";

        return result;
    }

    private static String adressValidation(String adress){

        String result = "";

        if(Pattern.matches(textNotNullPattern, adress) && adress.length() < 100)
            result = "";
        else
            result = "Sett inn en gydlig adresse";

        return result;
    }

    private static String dateValidation(LocalDate startDate, LocalDate endDate){

        String result = "";

        if(startDate.isBefore(endDate) || startDate.isEqual(endDate))
            result = "";

        else
            result = "Sett inn gyldige datoer";

        return result;
    }

    @Override
    public boolean isValidArrangement(Arrangement arrangement) {
        return false;
    }
}

