package ru.rgs.csvparser.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rgs.csvparser.entity.Client;
import ru.rgs.csvparser.entity.Scoring;
import ru.rgs.csvparser.controller.ControllerScore;
//import ru.rgs.csvparser.controller.ControllerCsv;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;

@Service
@Slf4j
public class CsvParserServiceImpl implements CsvParserService {

    @Autowired
    ControllerScore controllerScore;

    private static final String inputHeader = "FIRST_NAME,LAST_NAME,MIDDLE_NAME,CONTRACT_DATE";
    private static final String outputHeader = "CLIENT_NAME,CONTRACT_DATE,SCORING";

    @SneakyThrows
    public Path processCsv(Path source) {
        String nameOut = source.getFileName().toString().replace("input", "output");
        Path output = Paths.get(source.getParent() + "/../output2/" + nameOut).normalize();
        Scanner scanner = new Scanner(new File(source.toString()));
        OpenOption[] optionsAdd = new OpenOption[]{APPEND};

        if (scanner.nextLine().matches(inputHeader)) {
            if (!Files.exists(output.getParent())) {
                Files.createDirectory(output.getParent());
            }
            Files.write(output, Arrays.asList(outputHeader));
            while (scanner.hasNextLine()) {
                String[] words = scanner.nextLine().toUpperCase().split(",");
                String clientName = words[0] + " " + words[2] + " " + words[1];
                String contractDate = words[3];

                Client client = new Client(clientName, contractDate);
                Scoring response = controllerScore.getScore(client);

                System.out.println("----------\n" + response.toString() + "\n----------");


                String str = new String();
                if (response.getStatus().equals("COMPLETED")) {
                    str = clientName + "," + contractDate + "," +
                            Double.parseDouble(response.getScoringValue());
                }
                else if (response.getStatus().equals("NOT_FOUND")) {
                    str = clientName + "," + contractDate + ",не найден";
                }
                else if (response.getStatus().equals("FAILED")) {
                    str = clientName + "," + contractDate + "," +
                            response.getDescription();
                }

                Files.write(output, Arrays.asList(str), optionsAdd);

            }
        }
        return (output);
    }

}
