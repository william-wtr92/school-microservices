package com.example.studentservice.service.impl;

import com.example.studentservice.dto.SchoolDto;
import com.example.studentservice.dto.StudentDto;
import com.example.studentservice.dto.response.GetStudentDto;
import com.example.studentservice.entity.Student;
import com.example.studentservice.mapper.StudentMapper;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private StudentMapper studentMapper;
    private WebClient webClient;

    @Override
    public void createStudent(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        studentRepository.save(student);
    }

    @Override
    public GetStudentDto getStudent(String id) {
        Student student = studentRepository.findById(id).orElse(null);

        SchoolDto school = webClient.get()
                .uri("/api/school/{id}", student.getSchoolId())
                .retrieve()
                .bodyToMono(SchoolDto.class)
                .block();


        StudentDto studentDto = studentMapper.toDto(student);

        return new GetStudentDto(studentDto, school);
    }

    @Override
    public void updateStudent(String id, StudentDto studentDto) {
        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent == null) {
            return;
        }

        Student student = studentMapper.toEntity(studentDto);
        student.setId(existingStudent.getId());

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
}
