package com.example.demo.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private final static List<Student> STUDENTS = Arrays.asList(    //временно, пока список не привязан к БД
            new Student("Nikita Borzenkov"),
            new Student("Dungeon Master"),
            new Student("Zxc DeadInside")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    public List<Student> getAllStudents() {
        System.out.println("All students:");
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT_WRITE')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(student + " registered");
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('STUDENT_WRITE')")
    public void deleteStudents(@PathVariable("studentId") Integer studentId) {
        System.out.println("Student " + studentId + " deleted");
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('STUDENT_READ')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
        System.out.println("Updated " + student + " " + studentId);
    }
}
