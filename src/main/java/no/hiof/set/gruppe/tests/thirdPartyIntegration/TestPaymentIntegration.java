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
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

class TestPaymentIntegration {

    // --------------------------------------------------//
    //                2.Unit Tests                       //
    // --------------------------------------------------//
    @Test
    void transactionSuccess(){
        IPaymentIntegration paymentIntegration = new KlarnaIntegration();


    }

    @Test
    void transactionFailed(){

    }
}
