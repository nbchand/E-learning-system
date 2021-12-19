package com.mycompany.elearning.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Teachers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String fname;
    @Column
    private String lname;
    @OneToMany(mappedBy = "teacher")
    @ToString.Exclude
    private List<Course> courses;
}
