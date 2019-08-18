package ru.rgs.csvparser.service;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CsvParserServiceImpl implements CsvParserService {

    @SneakyThrows
    @Bean
    public Path processCsv(Path source) {
        Path output = Paths.get(source.getParent() + "../output");

        List<String> lines = Files.readAllLines(source);
        for (String s : lines){
            System.out.println(s);
            String[] words = s.split(",");
            if (!Files.exists(output)) {
                Files.createDirectories(Paths.get(source.getParent() + "../output"));
            }

        }
        return (source);
    }

}
