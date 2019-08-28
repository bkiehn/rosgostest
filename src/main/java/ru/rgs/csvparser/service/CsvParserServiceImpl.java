package ru.rgs.csvparser.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rgs.csvparser.entity.Store;
import ru.rgs.csvparser.MultiThread.Task;
import ru.rgs.csvparser.entity.Client;
import ru.rgs.csvparser.entity.Scoring;
import ru.rgs.csvparser.feign.RequestMockServer;
//import ru.rgs.csvparser.feign.ControllerCsv;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;

@Slf4j
public class CsvParserServiceImpl implements CsvParserService {

    @Autowired
    RequestMockServer requestMockServer;

    @Autowired
    Store store;

    private static final String inputHeader = "FIRST_NAME,LAST_NAME,MIDDLE_NAME,CONTRACT_DATE";
    private static final String outputHeader = "CLIENT_NAME,CONTRACT_DATE,SCORING";
    private static final String statusCompleted = "COMPLETED";
    private static final String statusNotFound = "NOT_FOUND";
    public static final String statusFailed = "FAILED";

    OpenOption[] optionsAdd = new OpenOption[]{APPEND};

    @SneakyThrows
    public Path processCsv(Path source) {
        String nameOut = source.getFileName().toString().replace("input", "output");
        Path output = Paths.get(source.getParent() + "/../output2/" + nameOut).normalize();
        Scanner scanner = new Scanner(new File(source.toString()));

        if (scanner.hasNextLine() && scanner.nextLine().matches(inputHeader)) {
            if (!Files.exists(output.getParent())) {
                Files.createDirectory(output.getParent());
            }
            Files.write(output, Arrays.asList(outputHeader));

            int id = 0;
            ArrayList<Thread> threads = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String[] words = scanner.nextLine().toUpperCase().split(",");
                String clientName = words[0] + " " + words[2] + " " + words[1];
                String contractDate = words[3];
                Client client = new Client(clientName, contractDate);

                threads.add(new Thread(new Task(client, requestMockServer, store,
                        id, contractDate)));
                id++;

            }

            treadsToJob(threads);
            store.sortById();
            writeToFile(output);
            store.clean();
        }
        return (output);
    }

    public String formatForCsv(Scoring scoring) {
        String str = new String();
        if (scoring.getStatus().equals(statusCompleted)) {
            str = scoring.getNameClient() + "," + scoring.getContractDate() + "," +
                    Double.parseDouble(scoring.getScoringValue());
        } else if (scoring.getStatus().equals(statusNotFound)) {
            str = scoring.getNameClient() + "," + scoring.getContractDate() + ",не найден";
        } else if (scoring.getStatus().equals(statusFailed)) {
            str = scoring.getNameClient() + "," + scoring.getContractDate() + "," +
                    scoring.getDescription();
        }
        return str;
    }

    @SneakyThrows
    private void treadsToJob(ArrayList<Thread> threads) {

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    @SneakyThrows
    public void writeToFile(Path output) {
        for (Scoring s : store.scorings) {
            String stringForCsv = formatForCsv(s);
            Files.write(output, Arrays.asList(stringForCsv), optionsAdd);
        }
    }

}
