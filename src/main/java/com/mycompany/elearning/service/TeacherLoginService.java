package com.mycompany.elearning.service;

import com.mycompany.elearning.model.Teacher;
import com.mycompany.elearning.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherLoginService {
    @Autowired
    private TeacherRepo teacherRepo;

    public Teacher getTeacherByEmail(String email){
        return teacherRepo.findByEmail(email);
    }
}
