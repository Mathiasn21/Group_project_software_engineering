package no.hiof.gruppefire;

import no.hiof.gruppefire.model.*;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {


        //Klassetest
        Arrangement arrangement1 = new Arrangement("HaldenLøpet", "Marathon", 100);
        Arrangement arrangement2 = new Arrangement("KongsvingerLøpet", "Marathon", 300);

        System.out.println(arrangement1.getName());

        ArrayList<Arrangement>liste = new ArrayList<>();

        liste.add(arrangement1);
        liste.add(arrangement2);

        for(Arrangement a : liste){
            System.out.println(a.getName());
        }


    }
}
