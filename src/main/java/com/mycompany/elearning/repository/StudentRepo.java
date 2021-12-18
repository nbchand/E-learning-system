package com.mycompany.elearning.repository;

import com.mycompany.elearning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    public Student findByEmail(String email);
    public boolean existsByEmail(String email);
}