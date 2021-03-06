package com.mycompany.elearning.service;

import com.mycompany.elearning.model.Teacher;
import com.mycompany.elearning.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherSignupService {
    @Autowired
    private TeacherRepo teacherRepo;

    public boolean isEmailTaken(String email){
        return teacherRepo.existsByEmail(email);
    }
    public void saveTeacher(Teacher teacher){
        teacherRepo.save(teacher);
    }
}
