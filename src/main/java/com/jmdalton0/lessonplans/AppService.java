package com.jmdalton0.lessonplans;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class AppService {

    private Path root = Paths.get("src/main/resources/templates/lessons/");

    public Map<String, List<Lesson>> get() throws Exception {
        Map<String, List<Lesson>> data = new HashMap<>();

        List<Path> groups = getGroups();
        for (Path group : groups) {

            List<Lesson> lessons = getLessons(group)
                .stream()
                .map(path -> new Lesson(formatSlug(path), formatName(path)))
                .collect(Collectors.toList());

            data.put(formatGroup(group), lessons);
        }

        return data;
    }

    private List<Path> getGroups() throws Exception {
        try (Stream<Path> paths = Files.walk(root)) {
            return paths
                .skip(1)
                .filter(Files::isDirectory)
                .toList();
        }
    }

    private List<Path> getLessons(Path group) throws Exception {
        try (Stream<Path> paths = Files.walk(group)) {
            return paths
                .skip(1)
                .filter(Files::isRegularFile)
                .toList();
        }
    }

    private String formatGroup(Path path) {
        return path.getFileName().toString();
    }

    private String formatSlug(Path path) {
        return path
            .getFileName()
            .toString()
            .replace(".html", "");
    }

    private String formatName(Path path) {
        return path
            .getFileName()
            .toString()
            .substring(1)
            .replace(".html", "")
            .replace('-', ' ');
    }

}
