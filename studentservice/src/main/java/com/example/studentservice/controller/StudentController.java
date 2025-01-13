package com.example.studentservice.controller;

import com.example.studentservice.dto.StudentDto;
import com.example.studentservice.dto.response.GetStudentDto;
import com.example.studentservice.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody StudentDto studentDto) {
        studentService.createStudent(studentDto);

        return ResponseEntity.ok("Student created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetStudentDto> getStudent(@PathVariable String id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@Valid @RequestBody StudentDto studentDto, @PathVariable String id) {
        studentService.updateStudent(id, studentDto);

        return ResponseEntity.ok("Student updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);

        return ResponseEntity.ok("Student deleted");
    }

}
