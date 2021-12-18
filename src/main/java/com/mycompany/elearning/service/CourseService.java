package com.mycompany.elearning.service;

import com.mycompany.elearning.model.Course;
import com.mycompany.elearning.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courseRepo;

    public void registerCourse(Course course){
        courseRepo.save(course);
    }
}
