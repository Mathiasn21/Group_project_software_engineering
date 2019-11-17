package no.hiof.set.gruppe.tests.notGUI.thirdPartyIntegration;

/*Guide
 * 1. Import Statements
 * 2. Unit Tests
 * */

// --------------------------------------------------//
//                1.Import Statements                //
// --------------------------------------------------//

import no.hiof.set.gruppe.core.interfaces.IPaymentIntegration;
import no.hiof.set.gruppe.thirdPartyIntegrations.paymenIntegration.KlarnaIntegration;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestPaymentIntegration {

    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//

    @Test
    void klarna_access_success() throws IOException{
        IPaymentIntegration klarnaIntegration = new KlarnaIntegration();
        assertEquals(200, klarnaIntegration.response().statusCode);
    }

    @Test
    void klarna_payment_accepted() throws IOException{

        IPaymentIntegration klarnaIntegration = new KlarnaIntegration();

        InputStream inputStream = klarnaIntegration.response().getContent();
        InputStreamReader r = new InputStreamReader(inputStream);

        BufferedReader reader = new BufferedReader(r);
        StringBuilder sb = new StringBuilder();
        String str;

        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        assertEquals("Accepted", sb.toString());
    }
}
