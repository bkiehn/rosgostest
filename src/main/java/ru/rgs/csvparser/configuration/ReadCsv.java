package ru.rgs.csvparser.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;

public class ReadCsv {
    public static void main(String[] args) throws IOException {
//        Path input = Paths.get("input/input_1.csv");
//        Path output = Paths.get("output/output_1.csv");
//
//
//        Scanner scanner = new Scanner( new File("input/input_1.csv"));
//        if (scanner.nextLine().matches("FIRST_NAME,LAST_NAME,MIDDLE_NAME,CONTRACT_DATE")) {
//            Files.createDirectories(output.getParent());
//            Files.write(output, Arrays.asList("CLIENT_NAME,CONTRACT_DATE,SCORING"));
//            OpenOption[] options = new OpenOption[] {APPEND};
//            int count = 1;
//            while (scanner.hasNextLine()) {
//                String[] words = scanner.nextLine().toUpperCase().split(",");
//                Files.write(output, Arrays.asList(words[0] + " " + words[2] + " " + words[1] + ","
//                        + words[3] + "," + count ), options);
//                count++;
//            }
//        }
        String str = ",,,a,,b,c,,3,,, ";
        String[] strs = str.split(",");
        int i = 1;
        for (String s : strs) {
            System.out.println(i + ") " + s);
            i++;
        }


        HashMap<String, String> request = new HashMap<>();
        request.put("clientName", "Василий");
        request.put("contractDate", "25.25.25");

        System.out.println();
        String name = request.get("clientName");
        System.out.println(name);
    }
}
