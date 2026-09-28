package com.example.donatehub.service;

import com.example.donatehub.dto.DriveRequest;
import com.example.donatehub.dto.DriveSummaryResponse;
import com.example.donatehub.entity.Drive;
import com.example.donatehub.exception.BusinessRuleException;
import com.example.donatehub.exception.ResourceNotFoundException;
import com.example.donatehub.repository.DonatedItemRepository;
import com.example.donatehub.repository.DriveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriveService {

    private final DriveRepository driveRepository;
    private final DonatedItemRepository donatedItemRepository;

    public DriveService(DriveRepository driveRepository, DonatedItemRepository donatedItemRepository) {
        this.driveRepository = driveRepository;
        this.donatedItemRepository = donatedItemRepository;
    }

    public Drive createDrive(DriveRequest request) {
        checkDates(request);

        Drive drive = new Drive();
        copyToDrive(request, drive);
        return driveRepository.save(drive);
    }

    public List<Drive> getAllDrives() {
        return driveRepository.findAll();
    }

    public Drive getDriveById(Long id) {
        return driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drive not found with id " + id));
    }

    public Drive updateDrive(Long id, DriveRequest request) {
        Drive drive = getDriveById(id);
        checkDates(request);

        copyToDrive(request, drive);
        return driveRepository.save(drive);
    }

    public void deleteDrive(Long id) {
        Drive drive = getDriveById(id);

        // items belong to the drive, so deleting it would leave them without a drive
        if (donatedItemRepository.existsByDriveId(id)) {
            throw new BusinessRuleException("Cannot delete a drive that already has donated items");
        }
        driveRepository.delete(drive);
    }

    public DriveSummaryResponse getDriveSummary(Long id) {
        Drive drive = getDriveById(id);

        long collected = donatedItemRepository.getTotalCollected(id);
        long distributed = donatedItemRepository.getTotalDistributed(id);

        return new DriveSummaryResponse(drive.getId(), drive.getDriveName(), collected, distributed);
    }

    private void checkDates(DriveRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BusinessRuleException("End date cannot be before start date");
        }
    }

    private void copyToDrive(DriveRequest request, Drive drive) {
        drive.setDriveName(request.getDriveName());
        drive.setDescription(request.getDescription());
        drive.setStartDate(request.getStartDate());
        drive.setEndDate(request.getEndDate());
    }
}
