package com.mycompany.elearning.service;

import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.model.Student;
import com.mycompany.elearning.repository.CourseRepo;
import com.mycompany.elearning.repository.StudentRepo;
import com.mycompany.elearning.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private TeacherRepo teacherRepo;

    public void registerCourse(Course course){
        courseRepo.save(course);
    }

    public List<Course> getCoursesByIds(int arr[]){
        return courseRepo.findAllByIdIn(arr);
    }

    public void removeCourse(int courseId){
        Course course = courseRepo.getById(courseId);
        List<Student> students = studentRepo.findAllByCourses(course);
        for(Student student: students){
            studentCourseService.removeCourse(courseId,student.getId());
        }
        courseRepo.delete(course);
    }
}
