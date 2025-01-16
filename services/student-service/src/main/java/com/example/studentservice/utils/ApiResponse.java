package com.example.studentservice.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String message;
    private int status;
    private T data;
    private boolean success;

    public ApiResponse(String message, int status, boolean success) {
        this.message = message;
        this.status = status;
        this.success = success;
    }
}
