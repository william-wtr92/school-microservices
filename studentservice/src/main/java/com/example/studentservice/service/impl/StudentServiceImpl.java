package com.example.studentservice.service.impl;

import com.example.studentservice.dto.SchoolDto;
import com.example.studentservice.dto.StudentDto;
import com.example.studentservice.dto.response.GetStudentDto;
import com.example.studentservice.entity.Student;
import com.example.studentservice.mapper.StudentMapper;
import com.example.studentservice.repository.StudentRepository;
import com.example.studentservice.service.StudentService;
import com.example.studentservice.utils.SchoolServiceEndpoints;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final WebClient webClient;
    private final String schoolServiceUrl;

    public StudentServiceImpl(StudentRepository studentRepository,
                              StudentMapper studentMapper,
                              WebClient webClient,
                              @Value("${school-service.url}") String schoolServiceUrl) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.webClient = webClient;
        this.schoolServiceUrl = schoolServiceUrl;
    }

    @Override
    public void createStudent(StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        studentRepository.save(student);
    }

    @Override
    public GetStudentDto getStudent(String id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

        String url = schoolServiceUrl + SchoolServiceEndpoints.GET_SCHOOL_BY_ID;
        System.out.println("Calling school-service with URL: " + url);

        try {
            SchoolDto school = webClient.get()
                    .uri(url, student.getSchoolId())
                    .retrieve()
                    .bodyToMono(SchoolDto.class)
                    .block();

            StudentDto studentDto = studentMapper.toDto(student);
            return new GetStudentDto(studentDto, school);

        } catch (Exception e) {
            System.out.println("Error calling school-service: " + e.getMessage());
            throw e;
        }
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
