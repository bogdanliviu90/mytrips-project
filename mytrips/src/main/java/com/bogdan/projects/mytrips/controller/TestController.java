package com.bogdan.projects.mytrips.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/testpage")
public class TestController {

    @GetMapping(path = "")
    public String printTestPage(Model model) {
        return "login";
    }
}
