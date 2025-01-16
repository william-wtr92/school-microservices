package com.example.studentservice.controller;

import com.example.studentservice.dto.StudentDto;
import com.example.studentservice.dto.response.GetStudentDto;
import com.example.studentservice.service.StudentService;
import com.example.studentservice.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<StudentDto>> createStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto student = studentService.createStudent(studentDto);

        ApiResponse<StudentDto> response = new ApiResponse<>("Student created", HttpStatus.CREATED.value(), student, true);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetStudentDto>> getStudent(@PathVariable String id) {
       GetStudentDto student = studentService.getStudent(id);

       ApiResponse<GetStudentDto> response = new ApiResponse<>("Student retrieved", HttpStatus.OK.value(), student, true);

       return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateStudent(@Valid @RequestBody StudentDto studentDto, @PathVariable String id) {
        studentService.updateStudent(id, studentDto);

        return ResponseEntity.ok().body(new ApiResponse<>("Student updated", HttpStatus.OK.value(), null, true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);

        return ResponseEntity.ok().body(new ApiResponse<>("Student deleted", HttpStatus.OK.value(), null, true));
    }

}
