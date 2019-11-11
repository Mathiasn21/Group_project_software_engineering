package no.hiof.set.gruppe.ThirdPartyIntegrations.paymenIntegration;

import com.google.api.client.http.*;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.ArrayList;

public abstract class PaymentIntegration implements IPaymentIntegration {

    @NotNull
    public HttpResponse connectionResponse() throws IOException {

        GenericUrl genericUrl = new GenericUrl();

        HttpTransport transport = new MockHttpTransport();
        HttpRequest request = transport.createRequestFactory().buildGetRequest(genericUrl);
        HttpResponse response = request.execute();

        return response;
    }

    public String paymentValidation(){
        return "Accepted";
    }
}
