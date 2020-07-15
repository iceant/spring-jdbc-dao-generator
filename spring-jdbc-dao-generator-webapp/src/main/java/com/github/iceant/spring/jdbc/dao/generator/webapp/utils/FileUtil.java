package com.github.iceant.spring.jdbc.dao.generator.webapp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtil {

    public static Path getPath(String root, String ... paths){
        return Paths.get(root, paths);
    }

    public static String getPathAsString(String root, String ... paths){
        return Paths.get(root, paths).toString();
    }

    public static List<File> getFiles(String paths) {
        List<File> filesList = new ArrayList<File>();
        for (final String path : paths.split(File.pathSeparator)) {
            final File file = new File(path);
            if( file.isDirectory()) {
                recurse(filesList, file);
            }
            else {
                filesList.add(file);
            }
        }
        return filesList;
    }

    private static void recurse(List<File> filesList, File f) {
        File list[] = f.listFiles();
        for (File file : list) {
            if (file.isDirectory()) {
                recurse(filesList, file);
            }
            else {
                filesList.add(file);
            }
        }
    }

    public static List<String> getJarContent(String jarPath) throws IOException {
        List<String> content = new ArrayList<String>();
        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> e = jarFile.entries();
        while (e.hasMoreElements()) {
            JarEntry entry = (JarEntry)e.nextElement();
            String name = entry.getName();
            content.add(name);
        }
        return content;
    }

}
