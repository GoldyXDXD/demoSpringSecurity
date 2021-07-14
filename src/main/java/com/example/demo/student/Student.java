package com.example.demo.student;

import javax.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;;

    @Column(name = "Name")
    private String studentName;

    public Student() {
    }

    public Student(String studentName) {
        this.studentName = studentName;
    }

    public Long getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + id +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
