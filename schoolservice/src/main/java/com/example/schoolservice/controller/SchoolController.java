package com.example.schoolservice.controller;


import com.example.schoolservice.dto.SchoolDto;
import com.example.schoolservice.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @PostMapping
    public ResponseEntity<String> createSchool(@Valid @RequestBody SchoolDto schoolDto) {
        schoolService.createSchool(schoolDto);
        return ResponseEntity.ok("School created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDto> getSchool(@PathVariable Long id) {
        return ResponseEntity.ok(schoolService.getSchool(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSchool(@Valid @RequestBody SchoolDto schoolDto, @PathVariable Long id) {
        schoolService.updateSchool(id, schoolDto);
        return ResponseEntity.ok("School updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok("School deleted");
    }
}
