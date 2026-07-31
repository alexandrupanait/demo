package com.example.demo.account;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Client for the real Axapta ERP integration servlet that the old site used
 * for the ralonline invoices/orders sections - a live, internal service on
 * the same network, outside our control. Every call can fail (timeout,
 * unreachable, 5xx) so callers get an Optional rather than a thrown
 * exception reaching the user as a 500.
 */
@Service
public class AxaptaStatisticsClient {

    private static final String BASE_URL = "http://192.168.87.15:8081/axapta/Statistics";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public Optional<String> fetchInvoices(String firma) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("module", "invoices")
                .queryParam("account", firma)
                .queryParam("product", "")
                .queryParam("docdatefrom", "")
                .queryParam("docdateto", "")
                .queryParam("duedatefrom", "")
                .queryParam("duedateto", "")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
        return fetch(url);
    }

    public Optional<String> fetchOrders(String firma) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("module", "orders")
                .queryParam("account", firma)
                .queryParam("product", "")
                .queryParam("docdatefrom", "")
                .queryParam("docdateto", "")
                .queryParam("state", "")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
        return fetch(url);
    }

    private Optional<String> fetch(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return Optional.empty();
            }
            return Optional.of(response.body());
        } catch (IOException | RuntimeException e) {
            return Optional.empty();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }
}
