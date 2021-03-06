package com.mycompany.elearning.service;

import com.mycompany.elearning.model.Student;
import com.mycompany.elearning.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentSignupService {
    @Autowired
    private StudentRepo studentRepo;

    public boolean isEmailTaken(String email){
        return studentRepo.existsByEmail(email);
    }
    public void saveStudent(Student student){
        studentRepo.save(student);
    }
}
