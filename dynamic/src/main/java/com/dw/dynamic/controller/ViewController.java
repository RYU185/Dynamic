package com.dw.dynamic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/dynamic/index.html")
    public String index() {
        return "forward:/index.html";
    }
}
