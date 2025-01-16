package com.example.studentservice.service;

import com.example.studentservice.dto.StudentDto;
import com.example.studentservice.dto.response.GetStudentDto;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);
    GetStudentDto getStudent(String id);
    void updateStudent(String id, StudentDto studentDto);
    void deleteStudent(String id);
}
