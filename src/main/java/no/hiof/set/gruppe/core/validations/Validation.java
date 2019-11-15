package no.hiof.set.gruppe.core.validations;

/*Guide
 * 1. Import Statements
 * 2. Static Constants
 * 3. Validation Of Methods
 * 4. Private Helper Methods
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.core.Repository;
import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.model.Group;
import no.hiof.set.gruppe.model.ValidationResult;
import no.hiof.set.gruppe.model.user.RawUser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * This class handles backend validation of various data classes.
 * @author Gruppe4
 */
public class Validation{

    // --------------------------------------------------//
    //                2.Static Constants                 //
    // --------------------------------------------------//
    private static final String textNotNullPattern = "[^\0]+";
    private static final String numPattern = "[0-9]+";

    private static final int maxNameL = 50;
    private static final int minNameL = 2;
    private static final Repository repository = new Repository();


    // --------------------------------------------------//
    //                3.Validation Of Methods            //
    // --------------------------------------------------//
    /**
     * Validates an arrangement
     * @param arrangement {@link Arrangement}
     * @return ValidationResult {@link ValidationResult}
     */
    @NotNull
    @Contract("_ -> new")
    public static ValidationResult ofArrangement(@NotNull Arrangement arrangement) {
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
        str.append(LocalDate.now().isBefore(startDate) && (startDate.isBefore(endDate) || startDate.isEqual(endDate)) ? "" : "Sett inn gyldige datoer.\n");
        str.append(isBetween(participantsMin, participantsMax, arrangement.getParticipants()) ? "" : invalidNum);
        return new ValidationResult(str.toString(), str.length() == 0);
    }

    @NotNull
    @Contract("_ -> new")
    public static ValidationResult ofNewUser(@NotNull RawUser rawUser){
        StringBuilder res = new StringBuilder();
        int maxLengthName = 255;

        String email = rawUser.geteMail();
        String passHash = rawUser.getPassHash();
        LocalDate bDate;

        try{bDate = LocalDate.parse(rawUser.getbDate());
        }catch (DateTimeParseException wrongDateFormat){res.append("ERROR, feil dato format.\n");}

        res.append(rawUser.getfName().length() <= maxLengthName && rawUser.getlName().length() <= maxLengthName ? "" : "Ulovlig langt navn\n");
        res.append(!repository.queryAddress(rawUser.getStreetAddress()) ? "" : "Addressen er ikke gyldig.\n");
        res.append(rawUser.getCityCode().length() == 4 ? "" : "Ugyldig by kode.\n");
        res.append(regCheck(textNotNullPattern, email) && !repository.queryEmailExists(email) ? "" : "Ugyldig email.\n");
        res.append(passHash.length() >= 10 && passHash.length() <= 60 ? "" : "Ugyldig passord\n");

        return new ValidationResult(res.toString(), res.length() == 0);
    }

    @NotNull
    @Contract("_ -> new")
    public static ValidationResult ofGroup(@NotNull Group group){
        StringBuilder res = new StringBuilder();
        int maxNameLength = 30;
        int minNameLength = 3;

        res.append(group.getName().length() <= maxNameLength ? "" : "Navnet på gruppen er for langt");
        res.append(group.getName().length() >= minNameLength ? "" : "Navnet på gruppen er for kort");
        res.append(regCheck(textNotNullPattern, group.getName()) ? "" : "Navnet inneholder blankt felt");

        return  new ValidationResult(res.toString(), res.length() == 0);
    }

    /**
     * @param num String
     * @return boolean
     */
    public static boolean ofNumber(String num){
        return regCheck(numPattern, num);
    }


    // --------------------------------------------------//
    //                4.Private Helper Methods           //
    // --------------------------------------------------//
    /**
     * Tests if number is between two numbers, inclusive both ends.
     * @param min int
     * @param max int
     * @param numToTest int
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