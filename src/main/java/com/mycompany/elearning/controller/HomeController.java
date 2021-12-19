package com.mycompany.elearning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @GetMapping("/")
    public String displayHome(){
        return "Index";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }
}
