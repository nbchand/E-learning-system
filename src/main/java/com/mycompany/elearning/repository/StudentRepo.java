package com.mycompany.elearning.repository;

import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    public Student findByEmail(String email);
    public boolean existsByEmail(String email);
    public List<Student> findAllByCourses(Course course);
}