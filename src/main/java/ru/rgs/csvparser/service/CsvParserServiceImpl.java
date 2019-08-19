package ru.rgs.csvparser.service;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

@Service
public class CsvParserServiceImpl implements CsvParserService {

    private static final String inputHeader = "FIRST_NAME,LAST_NAME,MIDDLE_NAME,CONTRACT_DATE";
    private static final String outputHeader = "CLIENT_NAME,CONTRACT_DATE,SCORING";

    @SneakyThrows
    public Path processCsv(Path source) {
        String nameOut = source.getFileName().toString().replace("input", "output");
        Path output = Paths.get(source.getParent() + "/../output2/" + nameOut).normalize();
        System.out.println("------- " + output);
        Scanner scanner = new Scanner( new File(source.toString()));
        OpenOption[] optionsAdd = new OpenOption[] {APPEND};
       // OpenOption[] optionsNew = new OpenOption[] {CREATE};

        if (scanner.nextLine().matches(inputHeader)) {
            if (!Files.exists(output.getParent())) {
                Files.createDirectory(output.getParent());
            }
            Files.write(output, Arrays.asList(outputHeader));
            int count = 1;
            while (scanner.hasNextLine()) {
                System.out.println("------- cycle");
                String[] words = scanner.nextLine().toUpperCase().split(",");
                Files.write(output, Arrays.asList(words[0] + " " + words[2] + " " + words[1] + ","
                        + words[3] + "," + count ), optionsAdd);
                count++;
            }
        }
        return (output);
    }

}
