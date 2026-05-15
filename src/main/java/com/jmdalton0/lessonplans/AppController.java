package com.jmdalton0.lessonplans;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AppController {

    private AppService service;
    
    public AppController(AppService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("data", service.get());
        return "index";
    }

    @GetMapping("/lessons/{group}/{slug}")
    public String showLesson(
        @PathVariable String group,
        @PathVariable String slug,
        Model model
    ) {
        model.addAttribute("title", service.formatTitle(slug));
        return "lessons/$" + group + "/" + slug;
    }

}
