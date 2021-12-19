package com.mycompany.elearning.service;

import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.model.Student;
import com.mycompany.elearning.model.Teacher;
import com.mycompany.elearning.repository.CourseRepo;
import com.mycompany.elearning.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherCourseService {

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private CourseRepo courseRepo;

    public List<Course> getTeacherCourses(int teacherId){
        return courseRepo.findAllByTeacher(teacherRepo.getById(teacherId));
    }

    public void removeCourse(int courseId,int teacherID){
        Course course = courseRepo.getById(courseId);
        Teacher teacher = teacherRepo.getById(teacherID);
        List<Course> courses = teacher.getCourses();
        courses.remove(course);
        teacher.setCourses(courses);
        System.out.println("Hello");
        teacherRepo.save(teacher);
        System.out.println("Hello2");
    }
}
