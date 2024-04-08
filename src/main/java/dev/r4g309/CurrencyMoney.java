package dev.r4g309;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;
import java.util.Map;

public record CurrencyMoney(
        String result,
        int timeLastUpdateUnix,
        String baseCode,
        Map<String, BigDecimal> conversionRates
) {

    public static CurrencyMoney serializeCurrency(String data) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();
        return gson.fromJson(data, CurrencyMoney.class);
    }

    public static void convertCurrency(String from, String to, BigDecimal amount, ApiRepository apiRepository) {
        CurrencyMoney currency = apiRepository.obtainCurrency(from);

        BigDecimal rate = currency.conversionRates()
                .get(to);
        System.out.println(amount + " " + from + " = " + rate.multiply(amount) + " " + to);
    }
}
