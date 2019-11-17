package no.hiof.set.gruppe.core.infrastructure.factory.generators;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import no.hiof.set.gruppe.core.entities.Arrangement;
import no.hiof.set.gruppe.core.entities.constantInformation.SportCategory;

import java.time.LocalDate;

public class GenArrangements  implements GenData {
    private String[] titles = {"LANd", "rover", "≖_≖", "AdviceSincere",
            "BlogFeline", "Buseystoa", "Carmelo", "Connecru", "Craziite",
            "Cyberle", "Fondsonip", "Hypernade", "Loverhem", "MarcsMuch",
            "MaxExpert", "Midnight", "MinyCyber", "Moonix", "Neotrolef",
            "OneLink", "Perkson", "PlatinumSk8r", "PlusPsych", "Puffwo",
            "Riuminat", "Rockac", "Simonessl"
    };
    private Lorem lorem = LoremIpsum.getInstance();

    @Override
    public Arrangement createData() {
        return new Arrangement(genTitle(), genSport(), (int) (Math.random() * 250), genAddress(), true, genStartDate().toString(), genEndDate().toString(), genDescription());
    }

    private String genTitle(){return titles[(int) (Math.random() * titles.length)];}
    private String genSport(){
        SportCategory[] sports = SportCategory.values();
        return sports[(int) (Math.random()*sports.length)].toString();
    }
    private String genAddress(){return lorem.getCity();}
    private String genDescription(){return lorem.getWords(10, 50);}
    private LocalDate genStartDate(){return LocalDate.now().minusDays((long) Math.min(Math.random()*50, 15));}
    private LocalDate genEndDate(){return LocalDate.now().minusDays((long) Math.max(Math.random()*14, 14));}
}
