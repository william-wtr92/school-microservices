package com.example.schoolservice.mapper;

import com.example.schoolservice.dto.SchoolDto;
import com.example.schoolservice.entity.School;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    SchoolDto toDto(School school);
    School toEntity(SchoolDto schoolDto);
}
