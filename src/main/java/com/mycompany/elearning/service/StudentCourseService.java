package com.mycompany.elearning.service;

import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.model.Student;
import com.mycompany.elearning.repository.CourseRepo;
import com.mycompany.elearning.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService {
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    public List<Course> getCoursesForStudent(int studentId){
        Student student = studentRepo.getById(studentId);
        /*
         * Here the method findAllByStudents() returns all the rows from courses table except those rows which don't contain the provided student.
         * But we have an intermediate table 'student_courses' which stores student_id and not our 'courses' table
         * as a result when we call the findAllByStudents() method it won't return those rows which have no students or which have student_id=null
         * Hence, we need to get two list; first list which contains all the courses with no students and second list which contains all the courses
         * which don't contain the provided student.
         * */
        List<Course> courses = courseRepo.findAllByStudents(null);
        courses.addAll(courseRepo.findAllByStudentsNot(student));

        return courses;
    }

    public void enrollInCourse(int courseIds[],int studentId){
        Student student = studentRepo.getById(studentId);
        List<Course> courses = courseRepo.findAllByIdIn(courseIds);
        List<Course> studentCourses = student.getCourses();
        studentCourses.addAll(courses);
        student.setCourses(studentCourses);
        studentRepo.save(student);
    }

    public void removeCourse(int courseId,int studentId){
        Course course = courseRepo.getById(courseId);
        Student student = studentRepo.getById(studentId);
        List<Course> courses = student.getCourses();
        courses.remove(course);
        student.setCourses(courses);
        studentRepo.save(student);
    }
}
