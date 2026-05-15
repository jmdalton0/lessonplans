package com.jmdalton0.lessonplans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

@Service
public class AppService {

    private static final String path = "classpath*:/templates/lessons/**";

    public Map<String, List<Lesson>> get() {
        Map<String, List<Lesson>> data = new HashMap<>();

        try {

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(path);

            for (Resource resource : resources) {

                String uri = resource.getURI().toString();
                String[] segments = uri.split("/");

                String group = null;
                Lesson lesson = null;

                for (int i = 0; i < segments.length; i++) {

                    String segment = segments[i];

                    if (segment.startsWith("$")) {
                        group = segment.substring(1);
                    }
                    
                    if (i == segments.length - 1) {
                        lesson = new Lesson(formatSlug(segment), formatName(segment));
                    }
                }

                if (group != null && lesson != null && uri.endsWith(".html")) {

                    if (!data.containsKey(group)) {
                        data.put(group, new ArrayList<>());
                    }

                    data.get(group).add(lesson);
                }
            }
        } catch (Exception e) {
            throw new AppException();
        }

        return data;
    }

    public String formatTitle(String slug) {
        StringBuilder sb = new StringBuilder();

        boolean nextIsCap = true;
        for (int i = 2; i < slug.length(); i++) {
            char c = slug.charAt(i);
            if (c == '-') {
                nextIsCap = true;
                sb.append(' ');
            } else if (nextIsCap && Character.isLetter(c)) {
                nextIsCap = false;
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String formatSlug(String filename) {
        return filename.replace(".html", "");
    }

    private String formatName(String filename) {
        return filename 
                .substring(1)
                .replace(".html", "")
                .replace('-', ' ');
    }

}
