package com.myblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParamController {
    @GetMapping("/param")
    public String param(@RequestParam String one,@RequestParam String two) {
        return one+two;
    }
}
