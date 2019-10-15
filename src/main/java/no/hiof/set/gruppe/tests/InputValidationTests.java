package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.data.InputValidation;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.Assert.*;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InputValidationTests {

    /*
    @Test
    @Order(1)
    public void LegalInput() {
        boolean test = InputValidation.arrangementInputValidation("pes","Annet","420","Hakkebakkeskogen", true, LocalDate.of(2019,10,10), LocalDate.of(2019,10,11));
        System.out.println(test);
    }

    @ParameterizedTest
    @ValueSource(strings = {"","P","This is way to loooooooooooooooooooooooooooooooooooong"})
    @Order(2)
    public void IllegalNames(String name) {
        assertFalse(InputValidation.arrangementInputValidation(name,"Annet","420","Hakkebakkeskogen", true, LocalDate.of(2019,10,10), LocalDate.of(2019,10,11)));
    }

    @Test
    @Order(3)
    public void IllegalSport() {
        assertFalse(InputValidation.arrangementInputValidation("Hello World",null,"420","Hakkebakkeskogen", true, LocalDate.of(2019,10,10), LocalDate.of(2019,10,11)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1","1000000","Words are not allowed"})
    @Order(4)
    public void IllegalParticipants(String participants) {
        assertFalse(InputValidation.arrangementInputValidation("Hello World","Annet",participants,"Hakkebakkeskogen", true, LocalDate.of(2019,10,10), LocalDate.of(2019,10,11)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Illegal with ()[]{}", "This address is way to looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong"})
    @Order(5)
    public void IllegalAddress(String address) {
        assertFalse(InputValidation.arrangementInputValidation("Hello World","Annet","420",address, true, LocalDate.of(2019,10,10), LocalDate.of(2019,10,11)));
    }

    @Test
    @Order(6)
    public void IllegalDates() {
        assertFalse(InputValidation.arrangementInputValidation("Hello World","Annet","420","Hakkebakkeskogen", true, LocalDate.of(2019,10,10), LocalDate.of(2019,10,9)));
    }
    */
}
