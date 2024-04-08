package dev.r4g309;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.r4g309.utils.HistoryManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentHour = new Date();
        String sb = "-".repeat(50) +
                "\n" +
                "Current date: " +
                df.format(currentHour) +
                "\n" +
                "Exchange last update: " +
                df.format(new Date(currency.timeLastUpdateUnix() * 1000L)) +
                "\n" +
                amount +
                " " +
                from +
                " = " +
                rate.multiply(amount) +
                " " +
                to +
                "\n" +
                "-".repeat(50);
        HistoryManager.saveHistory(sb);
        System.out.println(sb);
    }
}
