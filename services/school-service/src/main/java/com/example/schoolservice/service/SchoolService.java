package com.example.schoolservice.service;

import com.example.schoolservice.dto.SchoolDto;

public interface SchoolService {
    void createSchool(SchoolDto schoolDto);
    SchoolDto getSchool(Long id);
    void updateSchool(Long id, SchoolDto schoolDto);
    void deleteSchool(Long id);

}
