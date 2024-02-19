package org.example.parser;

import java.util.List;

public interface LineParser {
    void parse(String line, List<String> commands);
}
