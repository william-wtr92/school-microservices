package com.example.schoolservice.controller;


import com.example.schoolservice.dto.SchoolDto;
import com.example.schoolservice.service.SchoolService;
import com.example.schoolservice.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createSchool(@Valid @RequestBody SchoolDto schoolDto) {
        schoolService.createSchool(schoolDto);

        return ResponseEntity.ok().body(new ApiResponse<>("School created successfully", HttpStatus.CREATED.value(), true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SchoolDto>> getSchool(@PathVariable Long id) {
        SchoolDto schoolDto = schoolService.getSchool(id);

        ApiResponse<SchoolDto> response = new ApiResponse<>(
                "School fetched successfully",
                HttpStatus.OK.value(),
                schoolDto,
                true
        );

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateSchool(@Valid @RequestBody SchoolDto schoolDto, @PathVariable Long id) {
        schoolService.updateSchool(id, schoolDto);
        return ResponseEntity.ok().body(new ApiResponse<>("School updated successfully", HttpStatus.OK.value(), true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok().body(new ApiResponse<>("School deleted successfully", HttpStatus.OK.value(), true));
    }
}
