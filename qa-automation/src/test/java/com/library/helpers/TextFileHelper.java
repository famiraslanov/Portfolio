package com.library.helpers;

import com.library.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextFileHelper
{
    public static void save(String fileLabel, String content)
    {
        String fileName = "files/" + fileLabel + "-" + DateHelper.dtInsert() + ".txt";
        try {
            Path path = Paths.get(fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, content.getBytes());
            Log.file(fileLabel, path.toFile());
        } catch (IOException e) {
            Log.exception(e);
            throw new RuntimeException(e);
        }
    }
}
