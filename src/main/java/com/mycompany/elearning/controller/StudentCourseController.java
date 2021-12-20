package com.mycompany.elearning.controller;

import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.model.Student;
import com.mycompany.elearning.service.CourseService;
import com.mycompany.elearning.service.StudentCourseService;
import com.mycompany.elearning.service.StudentLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class StudentCourseController {

    @Autowired
    private StudentLoginService studentLoginService;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/student/courses")
    public String showStudentCourses(Model model, HttpServletRequest request){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        if(!request.getSession().getAttribute("user").equals("student")){
            return "redirect:/teacher/courses";
        }
        Student student = studentLoginService.getStudentById((int)request.getSession().getAttribute("userId"));
        model.addAttribute("courses",student.getCourses());
        return "StudentHome";
    }

    @GetMapping("/student/available-courses")
    public String showAvailableCourses(HttpServletRequest request, Model model){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        if(!request.getSession().getAttribute("user").equals("student")){
            return "redirect:/teacher/courses";
        }
        List<Course> courses = studentCourseService.getCoursesForStudent((int)request.getSession().getAttribute("userId"));
        if(courses.isEmpty()){
            return "AvailableCourses";
        }
        model.addAttribute("courses",courses);
        return "AvailableCourses";
    }

    @PostMapping("/student/enroll")
    @ResponseBody
    public String enrollStudent(@RequestBody int courseIds[], HttpServletRequest request){

        if(request.getSession().getAttribute("userId")==null){
            return "unavailable";
        }
        if(!request.getSession().getAttribute("user").equals("student")){
            return "teacher";
        }
        if(courseIds.length==0){
            return "Select atleast one course";
        }

        int studentId = (int)request.getSession().getAttribute("userId");
        studentCourseService.enrollInCourse(courseIds,studentId);
        return "success";
    }

    @DeleteMapping(value = "/student/course/{id}")
    @ResponseBody
    public String leaveCourse(@PathVariable int id,HttpServletRequest request) {
        if(request.getSession().getAttribute("userId")==null){
            return "failed";
        }
        if(!request.getSession().getAttribute("user").equals("student")){
            return "teacher";
        }
        studentCourseService.removeCourse(id,(int)request.getSession().getAttribute("userId"));
        return "success";
    }
}
