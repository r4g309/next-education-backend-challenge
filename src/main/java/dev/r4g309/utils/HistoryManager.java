package dev.r4g309.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class HistoryManager {
    public final static String HISTORY_FILE = Environment.getProperty("history.file.name");

    public static void saveHistory(String data) {
        try (FileWriter file = new FileWriter(HISTORY_FILE, true)) {
            file.write(data + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred. And the history could not be saved.");
        }
    }

    public static void showHistory() {
        try (Stream<String> lines = Files.lines(Paths.get(HISTORY_FILE))) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("An error occurred. And the history could not be displayed.");
        }

    }
}
