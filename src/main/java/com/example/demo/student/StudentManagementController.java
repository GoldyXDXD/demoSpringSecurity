package com.example.demo.student;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(    //временно, пока список не привязан к БД
            new Student(1, "Nikita Borzenkov"),
            new Student(2, "Dungeon Master"),
            new Student(3, "Zxc DeadInside")
    );

    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    public void registerNewStudent(Student student) {
        System.out.println(student);
    }

    public void deleteStudents(Integer studentId) {
        System.out.println(studentId);
    }

    public void updateStudent(Integer studentId, Student student) {

    }
}
