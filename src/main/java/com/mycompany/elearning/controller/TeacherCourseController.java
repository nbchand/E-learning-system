package com.mycompany.elearning.controller;

import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.service.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TeacherCourseController {

    @Autowired
    private TeacherCourseService teacherCourseService;

    @GetMapping("/teacher/courses")
    public String showTeacherCourses(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        List<Course> courses = teacherCourseService.getTeacherCourses((int)request.getSession().getAttribute("userId"));
        if(courses.isEmpty()){
            return "TeacherHome";
        }
        model.addAttribute("courses",courses);
        return "TeacherHome";
    }

    @GetMapping("/teacher/course-form")
    public String showCourseForm(HttpServletRequest request){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        return "CourseForm";
    }

    @DeleteMapping(value = "/teacher/course/{id}")
    @ResponseBody
    public String deleteCourse(@PathVariable int id, HttpServletRequest request) {
        if(request.getSession().getAttribute("userId")==null){
            return "failed";
        }
        teacherCourseService.removeCourse(id,(int)request.getSession().getAttribute("userId"));
        return "success";
    }
}
