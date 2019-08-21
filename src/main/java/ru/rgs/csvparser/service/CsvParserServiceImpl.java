package ru.rgs.csvparser.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                log.info("------- cycle");
                String[] words = scanner.nextLine().toUpperCase().split(",");
                String clientName = words[0] + " " + words[2] + " " + words[1];
                String contractDate = words[3];
                HashMap<String, String> request = new HashMap<>();
                request.put("clientName", clientName);
                request.put("contractDate", contractDate);

                HashMap response = controllerScore.score(request);

//                System.out.printf("-----------------------\n");
//                for (Map.Entry<String, String> s: response.entrySet()) {
//                    System.out.printf("%s : %s\n", s.getKey(), s.getValue());
//                }
//                System.out.printf("-----------------------\n");
                String str = new String();
                if (response.get("status").equals("COMPLETED")) {
                    Object scoringValue = response.get("scoringValue");
                    str = clientName + "," + contractDate + "," + scoringValue.toString();
                }
                else if (response.get("status").equals("NOT_FAILED")) {
                    Object scoringValue = 0;
                    str = clientName + "," + contractDate + "," + scoringValue.toString();
                }
                else if (response.get("status").equals("FAILED")) {
                    Object scoringValue = "ошибка обработки";
                    str = clientName + "," + contractDate + "," + scoringValue.toString();
                }



                Files.write(output, Arrays.asList(str), optionsAdd);



            }
        }
        return (output);
    }

}
