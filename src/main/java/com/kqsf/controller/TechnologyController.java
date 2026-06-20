package com.kqsf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/technology")
public class TechnologyController {

    @GetMapping
    public String technologyHome() {
        return "site/technology/index";
    }

    @GetMapping("/ultra-freezing")
    public String ultraFreezing() {
        return "site/technology/ultra-freezing";
    }

    @GetMapping("/cold-chain")
    public String coldChain() {
        return "site/technology/cold-chain";
    }

    @GetMapping("/lng-infrastructure")
    public String lngInfrastructure() {
        return "site/technology/lng-infrastructure";
    }

    @GetMapping("/quick-super-freeze")
    public String quickSuperFreeze() {
        return "site/technology/quick-super-freeze";
    }

    @GetMapping("/qsf-system")
    public String qsfSystem() {
        return "site/technology/qsf-system";
    }

    @GetMapping("/quality-standard")
    public String qualityStandard() {
        return "site/technology/quality-standard";
    }

    @GetMapping("/applications")
    public String applications() {
        return "site/technology/applications";
    }
}
