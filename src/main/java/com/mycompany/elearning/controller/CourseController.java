package com.mycompany.elearning.controller;

import com.mycompany.elearning.feature.PatternMatcher;
import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.model.Teacher;
import com.mycompany.elearning.service.CourseService;
import com.mycompany.elearning.service.TeacherLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CourseController {

    @Autowired
    private TeacherLoginService teacherLoginService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/student/courses")
    public String showStudentCourses(HttpServletRequest request){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        return "StudentHome";
    }

    @GetMapping("/teacher/courses")
    public String showTeacherCourses(HttpServletRequest request){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        return "TeacherHome";
    }

    @GetMapping("/course-form")
    public String showCourseForm(HttpSession session){
        if(session.getAttribute("userId")==null){
            return "redirect:/";
        }
        return "CourseForm";
    }

    @PostMapping("/course")
    public String createCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes, HttpServletRequest request){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        if(course.getName().equals("")||course.getName().equals(" ")){
            redirectAttributes.addFlashAttribute("coursemsg","Course name required");
            return "redirect:/course-form";
        }
        if(course.getCredit()>5||course.getCredit()<1){
            redirectAttributes.addFlashAttribute("coursemsg","Credit hours must be between 1-5");
            return "redirect:/course-form";
        }
        if(!PatternMatcher.checkCourseCodePattern(course.getCode())){
            redirectAttributes.addFlashAttribute("coursemsg","Course code invalid");
            return "redirect:/course-form";
        }

        Teacher teacher = teacherLoginService.getTeacherById((int)request.getSession().getAttribute("userId"));
        course.setTeacher(teacher);
        courseService.registerCourse(course);
        return "redirect:/teacher/courses";
    }
}
