package org.example.parser;

import lombok.extern.slf4j.Slf4j;
import org.example.config.AppConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class FileParser {
    private final AppConfig appConfig;
    private final LineParser lineParser;

    public FileParser(AppConfig appConfig) {
        this.appConfig = appConfig;
        lineParser = new LineParserImpl(appConfig);
    }

    public List<String> getCommandsFromFile() {
        return getCommandsFromFile(appConfig.defaultFilepath());
    }

    public List<String> getCommandsFromFile(String filename) {
        List<String> commandsList = new ArrayList<>();
        try {
            File file = new File(filename);
            try (Scanner fileScanner = new Scanner(file, "UTF-8")) {
                while (fileScanner.hasNext()) {
                    lineParser.parse(fileScanner.nextLine(), commandsList);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return commandsList;
    }
}
