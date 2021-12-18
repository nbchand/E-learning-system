package com.mycompany.elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {
    @GetMapping("/")
    public String displayHome(){
        return "index";
    }
}
