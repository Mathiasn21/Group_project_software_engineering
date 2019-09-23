package no.hiof.gruppefire.data;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class InputValidation {

    private static String validText = "^(?!\\s*$)[a-zA-Z]*$";
    private static String validNumbers = "^(?!\\s*$)[0-9]*$";
    private static String validDates = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
                                    + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                                    + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                                    + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";


    public static boolean arrangementInputValidation(String name, String sport, String participants, LocalDate startDate, LocalDate endDate) {

        Pattern pattern = Pattern.compile(validText);

        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches())
            return matcher.matches();

        matcher = pattern.matcher(sport);
        if (!matcher.matches())
            return matcher.matches();

        pattern = Pattern.compile(validNumbers);
        matcher = pattern.matcher(participants);
        if (!matcher.matches())
            return matcher.matches();

        boolean dateCheck = startDate.isBefore(endDate);

        pattern = Pattern.compile(validDates);
        matcher = pattern.matcher(startDate.toString());
        Matcher matcher2 = pattern.matcher(endDate.toString());

        //Fungerer i hodet mitt
        if (!matcher.matches() || !matcher2.matches() || !dateCheck)
            return false;

        return true;
    }

    //Ikke i bruk
    public static boolean alphabetCheck(String ...str){

        String alphabet = "^(?!\\s*$)[a-zA-Z]*$";
        Pattern pattern = Pattern.compile(alphabet);

        for(String s : str){
            Matcher matcher = pattern.matcher(s);
            if(!matcher.matches())
                return matcher.matches();
        }
        return true;
    }
}
