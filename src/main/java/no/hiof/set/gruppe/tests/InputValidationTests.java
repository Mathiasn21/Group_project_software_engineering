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





    //Dette kan nødvendigvis ikke brukes i innleveringen, men kjekt for å sjekke om den faktisk fungerer

    @Test
    @Order(1)
    public void LegalInput() {
        assertTrue(InputValidation.arrangementInputValidation("pes","Annet","420","Hakkebakkeskogen", LocalDate.of(2019,10,10), LocalDate.of(2019,10,11)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"","P","This is way to loooooooooooooooooooooooooooooooooooong"})
    @Order(2)
    public void IllegalNames(String name) {
        assertFalse(InputValidation.arrangementInputValidation(name,"Annet","420","Hakkebakkeskogen", LocalDate.of(2019,10,10), LocalDate.of(2019,10,11)));
    }

    @Test
    @Order(3)
    public void IllegalSport() {
        assertFalse(InputValidation.arrangementInputValidation("Hello World",null,"420","Hakkebakkeskogen", LocalDate.of(2019,10,10), LocalDate.of(2019,10,11)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1","Words are not allowed"})
    @Order(4)
    public void IllegalParticipants(String participants) {
        assertFalse(InputValidation.arrangementInputValidation("Hello World","Annet",participants,"Hakkebakkeskogen", LocalDate.of(2019,10,10), LocalDate.of(2019,10,11)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"This address is way to looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong"})
    @Order(5)
    public void IllegalAddress(String address) {
        assertFalse(InputValidation.arrangementInputValidation("Hello World","Annet","420",address, LocalDate.of(2019,10,10), LocalDate.of(2019,10,11)));
    }

    @Test
    @Order(6)
    public void IllegalDates() {
        assertFalse(InputValidation.arrangementInputValidation("Hello World","Annet","420","Hakkebakkeskogen", LocalDate.of(2019,10,10), LocalDate.of(2019,10,9)));
    }
}
