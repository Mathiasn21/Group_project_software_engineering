package no.hiof.set.gruppe.ThirdPartyIntegrations.paymenIntegration;

import com.google.api.client.http.HttpResponse;
import java.io.IOException;
import java.util.ArrayList;

public interface IPaymentIntegration {

    HttpResponse response() throws IOException;
}
