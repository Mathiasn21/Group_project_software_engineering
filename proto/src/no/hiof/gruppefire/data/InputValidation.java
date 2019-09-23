package no.hiof.gruppefire.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {


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
