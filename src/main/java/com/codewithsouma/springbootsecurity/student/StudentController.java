package com.codewithsouma.springbootsecurity.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private static final List<Student> STUDENTS = List.of(
            new Student(1,"Souma"),
            new Student(2,"Soumik"),
            new Student(3,"Arijit")
    );

    @GetMapping(path = "/{studentId}")
    public Student getStudent(@PathVariable("studentId") int studentId){
        Student student = STUDENTS.stream()
                .filter(s -> s.getStudentId() == studentId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Student id " + studentId + " does not exists"));

        return student;
    }
}
