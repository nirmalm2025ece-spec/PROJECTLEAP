package com.example.donatehub.controller;

import com.example.donatehub.dto.DriveRequest;
import com.example.donatehub.dto.DriveSummaryResponse;
import com.example.donatehub.entity.Drive;
import com.example.donatehub.service.DriveService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
public class DriveController {

    private final DriveService driveService;

    public DriveController(DriveService driveService) {
        this.driveService = driveService;
    }

    @PostMapping
    public ResponseEntity<Drive> createDrive(@Valid @RequestBody DriveRequest request) {
        Drive drive = driveService.createDrive(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(drive);
    }

    @GetMapping
    public ResponseEntity<List<Drive>> getAllDrives() {
        return ResponseEntity.ok(driveService.getAllDrives());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Drive> getDriveById(@PathVariable Long id) {
        return ResponseEntity.ok(driveService.getDriveById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Drive> updateDrive(@PathVariable Long id, @Valid @RequestBody DriveRequest request) {
        return ResponseEntity.ok(driveService.updateDrive(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrive(@PathVariable Long id) {
        driveService.deleteDrive(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<DriveSummaryResponse> getDriveSummary(@PathVariable Long id) {
        return ResponseEntity.ok(driveService.getDriveSummary(id));
    }
}
