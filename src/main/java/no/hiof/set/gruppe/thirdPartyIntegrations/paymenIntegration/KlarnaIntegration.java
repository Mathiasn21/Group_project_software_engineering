package no.hiof.set.gruppe.thirdPartyIntegrations.paymenIntegration;

import com.google.api.client.http.*;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;

public class KlarnaIntegration implements IPaymentIntegration{

    HttpTransport transport = new MockHttpTransport() {
        @Override
        public LowLevelHttpRequest buildGetRequest(String method) throws IOException {
            return new MockLowLevelHttpRequest() {
                @Override
                public LowLevelHttpResponse execute() throws IOException {
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

    @NotNull
    public HttpResponse connectionResponse() throws IOException {

        GenericUrl genericUrl = new GenericUrl();
        HttpRequest request = transport.createRequestFactory().buildGetRequest(genericUrl);

        return request.execute();
    }


}
