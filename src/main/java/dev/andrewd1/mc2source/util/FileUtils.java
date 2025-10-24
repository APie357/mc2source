package dev.andrewd1.mc2source.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class FileUtils {
    public static ArrayList<File> getFilesRecursive(File directory) {
        return getFilesRecursive(new ArrayList<>(), directory);
    }

    private static ArrayList<File> getFilesRecursive(ArrayList<File> files, File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                getFilesRecursive(files, file);
            } else {
                files.add(file);
            }
        }

        return files;
    }
}
