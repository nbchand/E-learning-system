package com.mycompany.elearning.controller;

import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.service.CourseService;
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

    @Autowired
    private CourseService courseService;

    @GetMapping("/teacher/courses")
    public String showTeacherCourses(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        if(!request.getSession().getAttribute("user").equals("teacher")){
            return "redirect:/student/courses";
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
        if(!request.getSession().getAttribute("user").equals("teacher")){
            return "redirect:/student/courses";
        }
        return "CourseForm";
    }

    @DeleteMapping(value = "/teacher/course/{id}")
    @ResponseBody
    public String deleteCourse(@PathVariable int id, HttpServletRequest request) {
        if(request.getSession().getAttribute("userId")==null){
            return "failed";
        }
        if(!request.getSession().getAttribute("user").equals("teacher")){
            return "redirect:/student/courses";
        }
        courseService.removeCourse(id);
        return "success";
    }

    @GetMapping("/teacher/edit-course/{id}")
    public String showEditCoursePage(@PathVariable int id, Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        if(!request.getSession().getAttribute("user").equals("teacher")){
            return "redirect:/student/courses";
        }
        Course course = courseService.getCourseById(id);
        try{
            System.out.println(course);
        }catch (Exception e){
            return "redirect:/teacher/courses";
        }
        model.addAttribute("course", course);
        return "EditCourse";
    }
}
