package org.example.parser;

import org.example.config.AppConfig;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class LineParserImpl implements LineParser {
    private final AppConfig appConfig;
    private Predicate<String> commandFilter;


    public LineParserImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
        initFilter();
    }

    @Override
    public void parse(String line, List<String> commands) {
        List<String> linesToFiler = trySplitLine(line);
        commands.addAll(linesToFiler.stream()
                .filter(commandFilter)
                .toList());
    }

    private List<String> trySplitLine(String line) {
        return appConfig.flagUseSeparator() ?
                Arrays.stream(line.split(appConfig.separatorForSplit())).toList()
                : List.of(line);
    }

    private void initFilter() {
        commandFilter = line -> line.equals(appConfig.patternCommandTurnLeft())
                || line.equals(appConfig.patternCommandTurnRight())
                || line.equals(appConfig.patternCommandMove())
                || line.equals(appConfig.patternCommandTurn180());
    }
}
