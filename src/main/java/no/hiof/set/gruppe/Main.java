package no.hiof.set.gruppe;

import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        String name = "advbm .g.aw.,,a";
        if(!Pattern.matches("[^\\p{L}\\p{Nd}\\p{Zs}\\p{Po}]+", name) && name.length() < 50 && name.length() > 2)
            System.out.println("True");

    }
}
