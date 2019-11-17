package no.hiof.set.gruppe.core.interfaces;

import com.google.api.client.http.HttpResponse;

import java.io.IOException;

public interface IPaymentIntegration {

    HttpResponse response() throws IOException;
}
