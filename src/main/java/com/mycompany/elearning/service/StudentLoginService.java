package com.mycompany.elearning.service;

import com.mycompany.elearning.model.Student;
import com.mycompany.elearning.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentLoginService {
    @Autowired
    private StudentRepo studentRepo;

    public Student getStudentByEmail(String email){
        return studentRepo.findByEmail(email);
    }
    public Student getStudentById(int id){
        return studentRepo.getById(id);
    }
}
