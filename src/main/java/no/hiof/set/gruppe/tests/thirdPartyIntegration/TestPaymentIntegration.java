package no.hiof.set.gruppe.tests.thirdPartyIntegration;

/*Guide
 * 1. Import Statements
 * 2. Unit Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//
import no.hiof.set.gruppe.thirdPartyIntegrations.paymenIntegration.IPaymentIntegration;
import no.hiof.set.gruppe.thirdPartyIntegrations.paymenIntegration.KlarnaIntegration;
import no.hiof.set.gruppe.thirdPartyIntegrations.paymenIntegration.TicketmasterIntegration;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import no.hiof.set.gruppe.model.Arrangement;
import no.hiof.set.gruppe.core.predicates.ArrangementSort;
import no.hiof.set.gruppe.core.predicates.DateTest;
import no.hiof.set.gruppe.core.validations.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.LocalDate;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class TestPaymentIntegration {

    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//

    @ParameterizedTest
    @ValueSource(ints = {200 /*, 201, 202, 203, 204, 205, 206, 207, 208, 226*/})
    void ticketmaster_access_success(int number) throws IOException{

        IPaymentIntegration ticketmasterIntegration = new TicketmasterIntegration();
        assertEquals(number,ticketmasterIntegration.connectionResponse().statusCode);
    }

    @Test
    void klarna_access_success() throws IOException{
        IPaymentIntegration klarnaIntegration = new KlarnaIntegration();
    }

    @Test
    void ticketmaster_payment_success(){
        IPaymentIntegration ticketmasterIntegration = new TicketmasterIntegration();

        assertEquals("Accepted", ticketmasterIntegration.paymentValidation());
    }

    @Test
    void transactionSuccess(){

    }

    @Test
    void transactionFailed(){

    }
}
