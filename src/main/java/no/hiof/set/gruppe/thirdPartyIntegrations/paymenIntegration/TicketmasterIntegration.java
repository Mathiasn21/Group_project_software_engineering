package no.hiof.set.gruppe.ThirdPartyIntegrations.paymenIntegration;

import com.google.api.client.http.*;
import com.google.api.client.testing.http.*;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;


public class TicketmasterIntegration implements no.hiof.set.gruppe.ThirdPartyIntegrations.paymenIntegration.IPaymentIntegration {

    HttpTransport transport = new MockHttpTransport() {
        @Override
        public LowLevelHttpRequest buildGetRequest(String method) throws IOException {
            return new MockLowLevelHttpRequest() {
                @Override
                public LowLevelHttpResponse execute() throws IOException {
                    MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                    response.addHeader("custom_header", "value");
                    response.statusCode = 202;
                    response.contentType = "TXT";
                    response.setContent("Page not found");
                    return response;
                }
            };
        }
    };

    @NotNull
    public HttpResponse connectionResponse() throws IOException{

        GenericUrl genericUrl = new GenericUrl();
        HttpRequest request = transport.createRequestFactory().buildGetRequest(genericUrl);
        HttpResponse response = request.execute();

        return response;
    }
}


