package no.hiof.set.gruppe.thirdPartyIntegrations.paymenIntegration;

import com.google.api.client.http.*;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

public class KlarnaIntegration implements IPaymentIntegration {


    /**
     * Simulating a Http request.
     */
    private final HttpTransport transport = new MockHttpTransport() {
        @Override
        public LowLevelHttpRequest buildGetRequest(String method) {
            return new MockLowLevelHttpRequest() {
                @Override
                public LowLevelHttpResponse execute() {
                    MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
                    response.addHeader("custom_header", "value");
                    response.statusCode = 200;
                    response.contentType = "HtmlResponse";
                    response.setContent("Accepted");

                    return response;
                }
            };
        }
    };

    /**
     * @return HttpRequest
     * @throws IOException
     */
    @NotNull
    public HttpResponse response() throws IOException {

        GenericUrl genericUrl = new GenericUrl();
        HttpRequest request = transport.createRequestFactory().buildGetRequest(genericUrl);

        return request.execute();
    }
}
