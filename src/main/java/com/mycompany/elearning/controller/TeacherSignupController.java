package com.mycompany.elearning.controller;

import com.mycompany.elearning.feature.PatternMatcher;
import com.mycompany.elearning.model.Teacher;
import com.mycompany.elearning.service.TeacherLoginService;
import com.mycompany.elearning.service.TeacherSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TeacherSignupController {
    @Autowired
    private TeacherSignupService teacherSignupService;
    @Autowired
    private TeacherLoginService teacherLoginService;

    @GetMapping("/signup/teacher")
    public String displayTeacherSignup(){
        return "TeacherSignup";
    }

    @PostMapping("/signup/teacher")
    public String registerTeacher(@ModelAttribute Teacher teacher, RedirectAttributes redirectAttributes){
        if(teacherSignupService.isEmailTaken(teacher.getEmail())){
            redirectAttributes.addFlashAttribute("signupmessage","Account already exists");
            return "redirect:/signup/teacher";
        }
        if(!PatternMatcher.checkEmailPattern(teacher.getEmail())){
            redirectAttributes.addFlashAttribute("signupmessage","Invalid email");
            return "redirect:/signup/teacher";
        }
        if(!PatternMatcher.checkNamePattern(teacher.getFname())){
            redirectAttributes.addFlashAttribute("signupmessage","Firstname invalid");
            return "redirect:/signup/teacher";
        }
        if(!PatternMatcher.checkNamePattern(teacher.getLname())){
            redirectAttributes.addFlashAttribute("signupmessage","Lastname invalid");
            return "redirect:/signup/teacher";
        }
        if(!PatternMatcher.checkPasswordPattern(teacher.getPassword())){
            redirectAttributes.addFlashAttribute("signupmessage","Password invalid");
            return "redirect:/signup/teacher";
        }

        teacher.setPassword(DigestUtils.md5DigestAsHex(teacher.getPassword().getBytes()));
        teacherSignupService.saveTeacher(teacher);

        redirectAttributes.addFlashAttribute("loginmessage","Signup successful");
        return "redirect:/login/teacher";
    }
}
