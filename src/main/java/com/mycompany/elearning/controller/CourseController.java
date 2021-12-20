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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CourseController {

    @Autowired
    private TeacherLoginService teacherLoginService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/course")
    public String createCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes, HttpServletRequest request){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        if(!request.getSession().getAttribute("user").equals("teacher")){
            return "redirect:/student/courses";
        }
        if(course.getName().equals("")||course.getName().equals(" ")){
            redirectAttributes.addFlashAttribute("coursemsg","Course name required");
            return "redirect:/teacher/course-form";
        }
        if(course.getCredit()>5||course.getCredit()<1){
            redirectAttributes.addFlashAttribute("coursemsg","Credit hours must be between 1-5");
            return "redirect:/teacher/course-form";
        }
        if(!PatternMatcher.checkCourseCodePattern(course.getCode())){
            redirectAttributes.addFlashAttribute("coursemsg","Course code invalid (Format example: CMP 112)");
            return "redirect:/teacher/course-form";
        }

        Teacher teacher = teacherLoginService.getTeacherById((int)request.getSession().getAttribute("userId"));
        course.setTeacher(teacher);
        courseService.registerCourse(course);
        return "redirect:/teacher/courses";
    }

    @PostMapping("/edit/course/{id}")
    public String editCourse(@PathVariable int id, @ModelAttribute Course course, RedirectAttributes redirectAttributes, HttpServletRequest request){
        if(request.getSession().getAttribute("userId")==null){
            return "redirect:/";
        }
        if(!request.getSession().getAttribute("user").equals("teacher")){
            return "redirect:/student/courses";
        }
        if(course.getName().equals("")||course.getName().equals(" ")){
            redirectAttributes.addFlashAttribute("coursemsg","Course name required");
            return "redirect:/teacher/edit-course/"+id;
        }
        if(course.getCredit()>5||course.getCredit()<1){
            redirectAttributes.addFlashAttribute("coursemsg","Credit hours must be between 1-5");
            return "redirect:/teacher/edit-course/"+id;
        }
        if(!PatternMatcher.checkCourseCodePattern(course.getCode())){
            redirectAttributes.addFlashAttribute("coursemsg","Course code invalid (Format example: CMP 112)");
            return "redirect:/teacher/edit-course/"+id;
        }
        Course course2 = courseService.getCourseById(id);
        course2.setName(course.getName());
        course2.setCode(course.getCode());
        course2.setCredit(course.getCredit());

        //since our course was already registred it will now get updated
        courseService.registerCourse(course2);
        return "redirect:/teacher/courses";
    }
}
