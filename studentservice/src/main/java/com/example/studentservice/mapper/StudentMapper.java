package com.example.studentservice.mapper;

import com.example.studentservice.dto.StudentDto;
import com.example.studentservice.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentDto studentDto);
    StudentDto toDto(Student student);
}
