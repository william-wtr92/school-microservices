package com.example.schoolservice.service.impl;

import com.example.schoolservice.dto.SchoolDto;
import com.example.schoolservice.entity.School;
import com.example.schoolservice.mapper.SchoolMapper;
import com.example.schoolservice.repository.SchoolRepository;
import com.example.schoolservice.service.SchoolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private SchoolRepository schoolRepository;
    private SchoolMapper schoolMapper;

    @Override
    public void createSchool(SchoolDto schoolDto) {
        School school = schoolMapper.toEntity(schoolDto);
        schoolRepository.save(school);
    }

    @Override
    public SchoolDto getSchool(Long id) {
        return schoolMapper.toDto(schoolRepository.findById(id).orElse(null));
    }

    @Override
    public void updateSchool(Long id, SchoolDto schoolDto) {
        School existingSchool = schoolRepository.findById(id).orElse(null);

        if (existingSchool == null) {
            return;
        }

        School school = schoolMapper.toEntity(schoolDto);
        school.setId(existingSchool.getId());

        schoolRepository.save(school);
    }

    @Override
    public void deleteSchool(Long id) {
        schoolRepository.deleteById(id);
    }
}
