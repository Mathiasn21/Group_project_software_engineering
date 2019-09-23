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

    public static boolean numberCheck(String ...num){
        String numbers = "^(?!\\s*$)[0-9]*$";
        Pattern pattern = Pattern.compile(numbers);

        for(String n : num){
            Matcher matcher = pattern.matcher(n);
            if(!matcher.matches())
                return matcher.matches();
        }
        return true;
    }
}
