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

    public Map<Integer, List<Lesson>> get() {
        Map<Integer, List<Lesson>> data = new HashMap<>();

        try {

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(path);

            for (Resource resource : resources) {

                String uri = resource.getURI().toString();
                String[] segments = uri.split("/");

                if (isLesson(segments)) {
					int groupId = getGroupId(segments);
					Lesson lesson = getLesson(segments);

					if (!data.containsKey(groupId)) {
						data.put(groupId, new ArrayList<>());
					}

					data.get(groupId).add(lesson);
                }
            }
        } catch (Exception e) {
            throw new AppException();
        }

        return data;
    }

    private Boolean isLesson(String[] segments) {
		String lastSegment = segments[segments.length - 1];
		if (!lastSegment.contains("$") && lastSegment.endsWith(".html")) {
			return true;
		}
		return false;
    }

    private int getGroupId(String[] segments) {
		String groupSegment = segments[segments.length - 2];
		return Character.getNumericValue(groupSegment.charAt(1));
    }

    private Lesson getLesson(String[] segments) {
		String groupSegment = segments[segments.length - 2];
		String lessonSegment = segments[segments.length - 1];

		String groupSlug = formatGroupSlug(groupSegment);
		String groupName = getNameFromSlug(groupSlug);
		String lessonSlug = formatLessonSlug(lessonSegment);
		String lessonName = getNameFromSlug(lessonSlug);

		return new Lesson(groupSlug, groupName, lessonSlug, lessonName);
    }

    private String formatGroupSlug(String filename) {
        return filename.replace("$", "");
    }

    private String formatLessonSlug(String filename) {
        return filename.replace(".html", "");
    }

    public String getNameFromSlug(String slug) {
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

}
