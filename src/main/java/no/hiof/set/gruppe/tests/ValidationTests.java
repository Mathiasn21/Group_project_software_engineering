package no.hiof.set.gruppe.tests;

import no.hiof.set.gruppe.data.Validation;
import no.hiof.set.gruppe.model.Arrangement;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.Assert.*;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidationTests {
    //Dette kan nødvendigvis ikke brukes i innleveringen, men kjekt for å sjekke om den faktisk fungerer

    @Test
    @Order(1)
    public void LegalInput() {
        Arrangement arrangement = new Arrangement("pes","Annet",420,"Hakkebakkeskogen", false, LocalDate.of(2019,10,10).toString(), LocalDate.of(2019,10,11).toString(), "testdwa dawd aw");
        assertTrue(Validation.ofArrangement(arrangement).IS_VALID);
    }

    @ParameterizedTest
    @ValueSource(strings = {"","P","This is way to loooooooooooooooooooooooooooooooooooong"})
    @Order(2)
    public void IllegalNames(String name) {
        Arrangement arrangement = new Arrangement(name,"Annet",420,"Hakkebakkeskogen", false, LocalDate.of(2019,10,10).toString(), LocalDate.of(2019,10,11).toString(), "test");
        assertFalse(Validation.ofArrangement(arrangement).IS_VALID);
    }

    @Test
    @Order(3)
    public void IllegalSport() {
        Arrangement arrangement = new Arrangement("pes",null,420,"Hakkebakkeskogen", false, LocalDate.of(2019,10,10).toString(), LocalDate.of(2019,10,11).toString(), "test");
        assertFalse(Validation.ofArrangement(arrangement).IS_VALID);
    }

    @ParameterizedTest
    @ValueSource(strings = {"This address is way to looooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong"})
    @Order(5)
    public void IllegalAddress(String address) {
        Arrangement arrangement = new Arrangement("pes","Annet",420,address, false, LocalDate.of(2019,10,10).toString(), LocalDate.of(2019,10,11).toString(), "test");
        assertFalse(Validation.ofArrangement(arrangement).IS_VALID);
    }

    @Test
    @Order(6)
    public void IllegalDates() {
        Arrangement arrangement = new Arrangement("pes","Annet",420,"Hakkebakkeskogen", false, LocalDate.of(2019,10,10).toString(), LocalDate.of(2019,10,9).toString(), "test");
        assertFalse(Validation.ofArrangement(arrangement).IS_VALID);
    }
}