package com.example.donatehub.controller;

import com.example.donatehub.dto.DistributionRequest;
import com.example.donatehub.dto.DistributionResponse;
import com.example.donatehub.service.DistributionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distributions")
public class DistributionController {

    private final DistributionService distributionService;

    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }

    @PostMapping
    public ResponseEntity<DistributionResponse> distributeItem(@Valid @RequestBody DistributionRequest request) {
        DistributionResponse response = distributionService.distributeItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<DistributionResponse>> getAllDistributions() {
        return ResponseEntity.ok(distributionService.getAllDistributions());
    }
}
