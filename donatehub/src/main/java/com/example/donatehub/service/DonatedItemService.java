package com.example.donatehub.service;

import com.example.donatehub.dto.DonatedItemRequest;
import com.example.donatehub.dto.DonatedItemResponse;
import com.example.donatehub.entity.DonatedItem;
import com.example.donatehub.entity.Donor;
import com.example.donatehub.entity.Drive;
import com.example.donatehub.exception.ResourceNotFoundException;
import com.example.donatehub.repository.DonatedItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonatedItemService {

    private final DonatedItemRepository donatedItemRepository;
    private final DriveService driveService;
    private final DonorService donorService;

    public DonatedItemService(DonatedItemRepository donatedItemRepository,
                              DriveService driveService,
                              DonorService donorService) {
        this.donatedItemRepository = donatedItemRepository;
        this.driveService = driveService;
        this.donorService = donorService;
    }

    public DonatedItemResponse addItem(DonatedItemRequest request) {
        // both of these throw "not found" if the id is wrong
        Drive drive = driveService.getDriveById(request.getDriveId());
        Donor donor = donorService.getDonorById(request.getDonorId());

        DonatedItem item = new DonatedItem();
        item.setCategory(request.getCategory());
        item.setCondition(request.getCondition());
        item.setQuantity(request.getQuantity());
        item.setDonatedDate(request.getDonatedDate());
        item.setDrive(drive);
        item.setDonor(donor);
        item.setDistributed(false); // always false for a new item

        return toResponse(donatedItemRepository.save(item));
    }

    public List<DonatedItemResponse> getAllItems() {
        return donatedItemRepository.findAll().stream().map(this::toResponse).toList();
    }

    public DonatedItemResponse getItemById(Long id) {
        return toResponse(findItem(id));
    }

    public List<DonatedItemResponse> getUndistributedItems() {
        return donatedItemRepository.findByDistributedFalse().stream().map(this::toResponse).toList();
    }

    // also used by DistributionService
    public DonatedItem findItem(Long id) {
        return donatedItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donated item not found"));
    }

    private DonatedItemResponse toResponse(DonatedItem item) {
        DonatedItemResponse response = new DonatedItemResponse();
        response.setId(item.getId());
        response.setCategory(item.getCategory());
        response.setCondition(item.getCondition());
        response.setQuantity(item.getQuantity());
        response.setDonatedDate(item.getDonatedDate());
        response.setDistributed(item.isDistributed());
        response.setDriveId(item.getDrive().getId());
        response.setDriveName(item.getDrive().getDriveName());
        response.setDonorId(item.getDonor().getId());
        response.setDonorName(item.getDonor().getName());
        return response;
    }
}
