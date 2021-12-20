package com.mycompany.elearning.controller;

import com.mycompany.elearning.model.Student;
import com.mycompany.elearning.pojo.UserPojo;
import com.mycompany.elearning.service.StudentLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class StudentLoginController {
    @Autowired
    private StudentLoginService studentLoginService;

    @GetMapping("/login/student")
    public String displayStudentLogin(){
        return "StudentLogin";
    }

    @PostMapping("/login/student")
    public String loginStudent(@ModelAttribute UserPojo user, RedirectAttributes redirectAttributes, HttpServletRequest request){
        Student student = studentLoginService.getStudentByEmail(user.getEmail());
        if(student==null){
            redirectAttributes.addFlashAttribute("loginmessage","No user found");
            return "redirect:/login/student";
        }
        if(!DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(student.getPassword())){
            redirectAttributes.addFlashAttribute("loginmessage","Incorrect password");
            return "redirect:/login/student";
        }

        request.getSession().setAttribute("userId",student.getId());
        request.getSession().setAttribute("user","student");
        request.getSession().setMaxInactiveInterval(1000);
        return "redirect:/student/courses";
    }
}
