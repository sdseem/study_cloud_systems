package com.example.study_cloud_systems.controllers;

import com.example.study_cloud_systems.dto.request.NumbersRequest;
import com.example.study_cloud_systems.dto.response.NumbersResponse;
import com.example.study_cloud_systems.services.NumbersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class NumbersController {
    private final NumbersService numbersService;

    @GetMapping(value = "test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }

    @PostMapping(value = "number")
    public ResponseEntity<NumbersResponse> incNumber(@RequestBody NumbersRequest requestBody) {
        Optional<Long> processedO = numbersService.processNumber(requestBody.num());
        if (processedO.isEmpty()) {
            log.error("Cannot process input number {}", requestBody.num());
            throw new IllegalArgumentException("Cannot process this number");
        }
        return ResponseEntity.ok(new NumbersResponse(processedO.get()));
    }
}
