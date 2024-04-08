package dev.r4g309;

import dev.r4g309.utils.Environment;
import dev.r4g309.utils.HistoryManager;
import dev.r4g309.utils.UserInput;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static String API_URL = "https://v6.exchangerate-api.com/v6/%s/";
    public static final int EXIT_OPTION = 10;
    public static final String menu = """
            Exchange Rate, choose an option:
            \t1. Dólar =>> Peso argentino
            \t2. Peso argentino =>> Dólar
            \t3. Dólar =>> Real brasileño
            \t4. Real brasileño =>> Dólar
            \t5. Dólar =>> Peso colombiano
            \t6. Peso colombiano =>> Dólar
            \t7. Show all supported currencies
            \t8. Custom exchange
            \t9. Show history
            \t%d. Exit
            >>>\s""".formatted(EXIT_OPTION);

    public static void main(String[] args) {
        int option;
        API_URL = String.format(API_URL, Environment.getProperty("exchange.apikey"));
        UserInput userInput = new UserInput();
        ApiRepository apiRepo = new ApiRepository(API_URL);

        do {
            System.out.print(menu);
            option = userInput.getInt();
            if (option > 0 && option < 7) {
                System.out.println("Enter the amount to convert:");
            }
            switch (option) {
                case 1 -> {
                    BigDecimal userAmount = userInput.getBigDecimal();
                    CurrencyMoney.convertCurrency(DefaultCurrency.USD, DefaultCurrency.ARS, userAmount, apiRepo);
                }
                case 2 -> {
                    BigDecimal userAmount = userInput.getBigDecimal();
                    CurrencyMoney.convertCurrency(DefaultCurrency.ARS, DefaultCurrency.USD, userAmount, apiRepo);
                }
                case 3 -> {
                    BigDecimal userAmount = userInput.getBigDecimal();
                    CurrencyMoney.convertCurrency(DefaultCurrency.USD, DefaultCurrency.BRL, userAmount, apiRepo);
                }
                case 4 -> {
                    BigDecimal userAmount = userInput.getBigDecimal();
                    CurrencyMoney.convertCurrency(DefaultCurrency.BRL, DefaultCurrency.USD, userAmount, apiRepo);
                }
                case 5 -> {
                    BigDecimal userAmount = userInput.getBigDecimal();
                    CurrencyMoney.convertCurrency(DefaultCurrency.USD, DefaultCurrency.COP, userAmount, apiRepo);
                }
                case 6 -> {
                    BigDecimal userAmount = userInput.getBigDecimal();
                    CurrencyMoney.convertCurrency(DefaultCurrency.COP, DefaultCurrency.USD, userAmount, apiRepo);
                }
                case 7 -> {
                    System.out.println("Supported currencies:");
                    Objects.requireNonNull(apiRepo.obtainSupportedCurrencies())
                            .forEach((key, value) -> System.out.println(key + " - " + value));
                }
                case 8 -> {
                    userInput.flush();
                    Map<String, String> supportedCurrencies = Objects.requireNonNull(apiRepo.obtainSupportedCurrencies());
                    System.out.println("Enter the currency to convert from:");
                    String fromCurrency = userInput.getString();
                    System.out.println("Enter the currency to convert to:");
                    String toCurrency = userInput.getString();
                    System.out.println("fromCurrency = " + fromCurrency);
                    System.out.println("supportedCurrencies = " + supportedCurrencies.containsKey(fromCurrency) + " " + supportedCurrencies.containsKey(toCurrency));
                    if (supportedCurrencies.containsKey(fromCurrency) && supportedCurrencies.containsKey(toCurrency)) {
                        System.out.println("Enter the amount to convert:");
                        BigDecimal userAmount = userInput.getBigDecimal();
                        CurrencyMoney.convertCurrency(fromCurrency, toCurrency, userAmount, apiRepo);
                    } else {
                        System.out.println("Invalid currency");
                    }
                }
                case 9 -> HistoryManager.showHistory();
                default -> System.out.println("Invalid option");
            }
        } while (option != EXIT_OPTION);
    }
}