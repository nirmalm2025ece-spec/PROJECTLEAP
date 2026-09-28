package com.example.donatehub.controller;

import com.example.donatehub.dto.DonorRequest;
import com.example.donatehub.entity.Donor;
import com.example.donatehub.service.DonorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping
    public ResponseEntity<Donor> createDonor(@Valid @RequestBody DonorRequest request) {
        Donor donor = donorService.createDonor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(donor);
    }

    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonors() {
        return ResponseEntity.ok(donorService.getAllDonors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.getDonorById(id));
    }
}
