package com.jmdalton0.lessonplans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    private static final String path = "classpath:/templates/lessons";

    public Map<String, List<Lesson>> get() {
        Map<String, List<Lesson>> data = new HashMap<>();

        try {
            List<String> groups = getGroups();
            for (String group : groups) {

                List<Lesson> lessons = getLessons(group)
                    .stream()
                    .map(lesson -> new Lesson(formatSlug(lesson), formatName(lesson)))
                    .collect(Collectors.toList());

                data.put(group, lessons);
            }
        } catch (Exception e) {
            throw new AppException();
        }

        return data;
    }

    private List<String> getGroups() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(path + "/*");
        List<String> groups = new ArrayList<>();
        for (Resource resource : resources) {
            groups.add(resource.getFilename().toString());
        }
        return groups;
    }

    private List<String> getLessons(String group) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(path + "/" + group + "/*");
        List<String> lessons = new ArrayList<>();
        for (Resource resource : resources) {
            lessons.add(resource.getFilename().toString());
        }
        return lessons;
    }

    private String formatSlug(String path) {
        return path.replace(".html", "");
    }

    private String formatName(String path) {
        return path
                .substring(1)
                .replace(".html", "")
                .replace('-', ' ');
    }

}
