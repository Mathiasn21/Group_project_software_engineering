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

        if(Pattern.matches("[a-zA-Z]+", Integer.toString(participants))) {
            // participants innholder bokstaver
        }

        return true;
    }
}

