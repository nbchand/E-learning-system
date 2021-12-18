package com.mycompany.elearning.controller;

import com.mycompany.elearning.feature.PatternMatcher;
import com.mycompany.elearning.model.Student;
import com.mycompany.elearning.service.StudentLoginService;
import com.mycompany.elearning.service.StudentSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentSignupController {

    @Autowired
    private StudentSignupService studentSignupService;
    @Autowired
    private StudentLoginService studentLoginService;

    @GetMapping("/signup/student")
    public String displayStudentSignup(){
        return "StudentSignup";
    }

    @PostMapping("/signup/student")
    public String registerTeacher(@ModelAttribute Student student, RedirectAttributes redirectAttributes){
        if(studentSignupService.isEmailTaken(student.getEmail())){
            redirectAttributes.addFlashAttribute("signupmessage","Account already exists");
            return "redirect:/signup/student";
        }
        if(!PatternMatcher.checkEmailPattern(student.getEmail())){
            redirectAttributes.addFlashAttribute("signupmessage","Invalid email");
            return "redirect:/signup/student";
        }
        if(!PatternMatcher.checkNamePattern(student.getFname())){
            redirectAttributes.addFlashAttribute("signupmessage","Firstname invalid");
            return "redirect:/signup/student";
        }
        if(!PatternMatcher.checkNamePattern(student.getLname())){
            redirectAttributes.addFlashAttribute("signupmessage","Lastname invalid");
            return "redirect:/signup/student";
        }
        if(!PatternMatcher.checkPasswordPattern(student.getPassword())){
            redirectAttributes.addFlashAttribute("signupmessage","Password invalid");
            return "redirect:/signup/student";
        }

        student.setPassword(DigestUtils.md5DigestAsHex(student.getPassword().getBytes()));
        studentSignupService.saveStudent(student);

        redirectAttributes.addFlashAttribute("loginmessage","Signup successful");
        return "redirect:/login/student";
    }
}
