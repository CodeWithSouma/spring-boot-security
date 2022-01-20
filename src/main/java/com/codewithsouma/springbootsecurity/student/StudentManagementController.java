package com.codewithsouma.springbootsecurity.student;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {
    private static final List<Student> STUDENTS = List.of(
            new Student(1,"Souma"),
            new Student(2,"Soumik"),
            new Student(3,"Arijit")
    );

    @GetMapping
    public List<Student> getAllStudents(){
        return STUDENTS;
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        System.out.println(student);
    }

    @DeleteMapping(path = "/{studentId}")
    public void deleteStudent(@PathVariable("studentId") int studentId){
        System.out.println(studentId);
    }

    @PutMapping(path = "/{studentId}")
    public void updateStudent( @PathVariable("studentId") int studentId, @RequestBody Student student){
        System.out.println(String.format("%s %s",studentId,student));
    }
}
