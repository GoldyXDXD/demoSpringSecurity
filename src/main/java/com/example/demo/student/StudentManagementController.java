package com.example.demo.student;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private final static List<Student> STUDENTS = Arrays.asList(    //временно, пока список не привязан к БД
            new Student(1, "Nikita Borzenkov"),
            new Student(2, "Dungeon Master"),
            new Student(3, "Zxc DeadInside")
    );

    @GetMapping
    public List<Student> getAllStudents() {
        System.out.println("All students:");
        return STUDENTS;
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(student + " registered");
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudents(@PathVariable("studentId") Integer studentId) {
        System.out.println("Student " + studentId + " deleted");
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println("Updated " + student + " " + studentId);
    }
}
