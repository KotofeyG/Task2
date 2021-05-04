package com.kotov.flower_xml.validator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileValidator {
    public static boolean isFileValid(String pathToFile) {
        boolean result = false;
        if (pathToFile != null) {
            Path path = Paths.get(pathToFile);
            try {
                if (Files.exists(path) && !Files.isDirectory(path) && Files.isReadable(path) && Files.size(path) != 0) {
                    result = true;
                }
            } catch (IOException ignored) {
            }
        }
        return result;
    }
}