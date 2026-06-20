package com.kqsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/technology")
public class TechnologyController {

    @GetMapping
    public String technologyHome() {
        return "technology/index";
    }
}
