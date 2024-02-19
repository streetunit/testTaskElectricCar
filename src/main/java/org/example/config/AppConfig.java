package org.example.config;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.example.car.Direction;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

import static java.util.Objects.nonNull;

@Getter
@Slf4j
@Accessors(fluent = true)
public class AppConfig {
    private static final String CONFIG_FILE = "config.yaml";

    private String patternCommandTurnLeft;
    private String patternCommandTurnRight;
    private String patternCommandMove;
    private String patternCommandTurn180;
    private String defaultFilepath;
    private String separatorForSplit;
    private boolean flagUseSeparator;

    private int fieldWidth;
    private int fieldHeight;
    private int startCarPosX;
    private int startCarPosY;
    private Direction startDirection;

    public AppConfig() {
        loadConfig();
    }

    private void loadConfig() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            Map<String, Map<String, Object>> config = yaml.load(inputStream);
            getCommandInfo(config.get("commands"));
            getParseConf(config.get("parser-conf"));
            getInitProperties(config.get("init-properties"));
        } catch (Exception e) {
            initCommandsDefault();
            initParserConfDefault();
            e.printStackTrace();
        }
    }

    private void getCommandInfo(Map<String, Object> commandsConf) {
        if (nonNull(commandsConf)) {
            patternCommandTurnLeft = (String) commandsConf.getOrDefault("left", DefaultValues.PATTERN_COMMAND_LEFT.getValue());
            patternCommandTurnRight = (String) commandsConf.getOrDefault("right", DefaultValues.PATTERN_COMMAND_RIGHT.getValue());
            patternCommandMove = (String) commandsConf.getOrDefault("move", DefaultValues.PATTERN_COMMAND_MOVE.getValue());
            patternCommandTurn180 = (String) commandsConf.getOrDefault("turn-180degrees", DefaultValues.PATTERN_COMMAND_TURN180.getValue());
        } else {
            initCommandsDefault();
        }
    }

    private void getParseConf(Map<String, Object> parserConf) {
        if (nonNull(parserConf)) {
            defaultFilepath = (String) parserConf.getOrDefault("default-filepath", DefaultValues.COMMANDS_FILEPATH.getValue());
            separatorForSplit = (String) parserConf.getOrDefault("separator", DefaultValues.SEPARATOR.getValue());
            flagUseSeparator = (boolean) parserConf.getOrDefault("use-separator", false);
        } else {
            initParserConfDefault();
        }
    }

    private void getInitProperties(Map<String, Object> conf) {
        Map<String, Integer> fieldsConf = (Map<String, Integer>) conf.get("field");
        fieldHeight = fieldsConf.getOrDefault("height", 10);
        fieldWidth = fieldsConf.getOrDefault("width", 10);

        Map<String, Object> carConf = (Map<String, Object>) conf.get("car-startposition");
        startCarPosX = (int) carConf.getOrDefault("positionX", 0);
        startCarPosY = (int) carConf.getOrDefault("positionY", 0);
        startDirection = Direction.valueOf((String) carConf.getOrDefault("direction", "NORTH"));
    }

    private void initCommandsDefault() {
        patternCommandTurnLeft = DefaultValues.PATTERN_COMMAND_LEFT.getValue();
        patternCommandTurnRight = DefaultValues.PATTERN_COMMAND_RIGHT.getValue();
        patternCommandMove = DefaultValues.PATTERN_COMMAND_MOVE.getValue();
        patternCommandTurn180 = DefaultValues.PATTERN_COMMAND_TURN180.getValue();
    }

    private void initParserConfDefault() {
        defaultFilepath = DefaultValues.COMMANDS_FILEPATH.getValue();
        separatorForSplit = DefaultValues.SEPARATOR.getValue();
        flagUseSeparator = false;
    }
}

@Getter
enum DefaultValues {
    COMMANDS_FILEPATH("src/main/resources/textCommands.txt"),
    SEPARATOR(";"),
    PATTERN_COMMAND_LEFT("turn left"),
    PATTERN_COMMAND_RIGHT("turn right"),
    PATTERN_COMMAND_MOVE("move"),
    PATTERN_COMMAND_TURN180("turn180");

    private final String value;

    DefaultValues(String v) {
        value = v;
    }
}