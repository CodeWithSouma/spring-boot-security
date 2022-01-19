package com.codewithsouma.springbootsecurity.student;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Student {
    private final int studentId;
    private final String studentName;
}
