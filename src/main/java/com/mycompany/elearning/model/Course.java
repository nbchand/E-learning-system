package com.mycompany.elearning.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private float credit;
    @ManyToOne
    @JoinColumn(name="teacher_id", nullable=false)
    @ToString.Exclude
    private Teacher teacher;
    @ManyToMany(mappedBy = "courses")
    @ToString.Exclude
    private List<Student> students;
}
