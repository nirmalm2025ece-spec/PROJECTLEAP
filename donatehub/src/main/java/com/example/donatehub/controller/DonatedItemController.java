package com.example.donatehub.controller;

import com.example.donatehub.dto.DonatedItemRequest;
import com.example.donatehub.dto.DonatedItemResponse;
import com.example.donatehub.service.DonatedItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class DonatedItemController {

    private final DonatedItemService donatedItemService;

    public DonatedItemController(DonatedItemService donatedItemService) {
        this.donatedItemService = donatedItemService;
    }

    @PostMapping
    public ResponseEntity<DonatedItemResponse> addItem(@Valid @RequestBody DonatedItemRequest request) {
        DonatedItemResponse item = donatedItemService.addItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @GetMapping
    public ResponseEntity<List<DonatedItemResponse>> getAllItems() {
        return ResponseEntity.ok(donatedItemService.getAllItems());
    }

    // fixed path, so Spring picks this before "/{id}"
    @GetMapping("/undistributed")
    public ResponseEntity<List<DonatedItemResponse>> getUndistributedItems() {
        return ResponseEntity.ok(donatedItemService.getUndistributedItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonatedItemResponse> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(donatedItemService.getItemById(id));
    }
}
