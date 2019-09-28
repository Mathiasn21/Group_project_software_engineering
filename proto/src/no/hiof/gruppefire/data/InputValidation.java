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

    public static boolean arrangementInputValidation(String name, String sport, int participants, String adress, boolean gruppe, LocalDate startDate, LocalDate endDate) {

        //String notValidtext = "";
        //Pattern validnNumbers = Pattern.compile("[0-9]");
        //Pattern validAdress = Pattern.compile("[a-zA-Z0-9æøå.]");

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

        /*if(Pattern.matches("[a-zA-Z]+", Integer.toString(participants))) {
            // participants innholder bokstaver
        }*/
    }
}

