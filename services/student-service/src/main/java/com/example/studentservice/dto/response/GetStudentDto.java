package com.example.studentservice.dto.response;

import com.example.studentservice.dto.SchoolDto;
import com.example.studentservice.dto.StudentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetStudentDto {
    private StudentDto student;
    private SchoolDto school;
}
