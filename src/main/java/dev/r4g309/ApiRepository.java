package dev.r4g309;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

public class ApiRepository {
    private final String API_URL;
    private final HttpClient client;

    ApiRepository(String apiUrl) {
        this.API_URL = apiUrl;
        this.client = HttpClient.newHttpClient();
    }

    public CurrencyMoney obtainCurrency(String currency) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(API_URL + "latest/" + currency))
                .build();
        try {
            HttpResponse<String> data = client.send(request, HttpResponse.BodyHandlers.ofString());
            return CurrencyMoney.serializeCurrency(data.body());
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public Map<String, String> obtainSupportedCurrencies() {
        Map<String, String> supportedCodes = new LinkedHashMap<>();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create(API_URL + "codes")
                )
                .build();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var codes = gson.fromJson(response.body(), Codes.class);
            for (String[] code : codes.supportedCodes()) {
                supportedCodes.put(code[0], code[1]);
            }
            return supportedCodes.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(LinkedHashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
