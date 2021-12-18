package com.mycompany.elearning.controller;

import com.mycompany.elearning.model.Teacher;
import com.mycompany.elearning.pojo.UserPojo;
import com.mycompany.elearning.service.TeacherLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TeacherLoginController {

    @Autowired
    private TeacherLoginService teacherLoginService;

    @GetMapping("/login/teacher")
    public String displayTeacherLogin(){
        return "TeacherLogin";
    }

    @PostMapping("/login/teacher")
    public String loginTeacher(@ModelAttribute UserPojo user, RedirectAttributes redirectAttributes, HttpServletRequest request){
        Teacher teacher = teacherLoginService.getTeacherByEmail(user.getEmail());
        if(teacher==null){
            redirectAttributes.addFlashAttribute("loginmessage","No user found");
            return "redirect:/login/teacher";
        }
        if(!DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(teacher.getPassword())){
            redirectAttributes.addFlashAttribute("loginmessage","Incorrect password");
            return "redirect:/login/teacher";
        }

        request.getSession().setAttribute("userId",teacher.getId());
        request.getSession().setMaxInactiveInterval(1000);
        return "redirect:/teacher/courses";
    }
}
