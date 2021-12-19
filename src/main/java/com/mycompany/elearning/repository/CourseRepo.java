package com.mycompany.elearning.repository;

import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.model.Student;
import com.mycompany.elearning.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<Course,Integer> {

    public List<Course> findAllByStudentsNot(Student student);
    public List<Course> findAllByStudents(Student student);
    public List<Course> findAllByTeacher(Teacher teacher);
    public List<Course> findAllByIdIn(int arr[]);
}