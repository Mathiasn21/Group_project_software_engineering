package no.hiof.set.gruppe.thirdPartyIntegrations.paymenIntegration;

import com.google.api.client.http.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;

public interface IPaymentIntegration {

    HttpResponse connectionResponse() throws IOException;
    String paymentValidation();
}