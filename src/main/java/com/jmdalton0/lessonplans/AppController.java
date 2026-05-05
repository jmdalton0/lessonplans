package com.jmdalton0.lessonplans;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AppController {

    @GetMapping
    public String index() {
        return "redirect:all";
    }

    @GetMapping("/all")
    public String showAll() {
        return "all";
    }

    @GetMapping("/weather")
    public String showWeather() {
        return "weather";
    }

    @GetMapping("/airspace")
    public String showAirspace() {
        return "airspace";
    }

    @GetMapping("/human-factors")
    public String showHumanFactors() {
        return "human-factors";
    }
    
}
