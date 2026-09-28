package com.example.donatehub.controller;

import com.example.donatehub.dto.RecipientRequest;
import com.example.donatehub.entity.Recipient;
import com.example.donatehub.service.RecipientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipients")
public class RecipientController {

    private final RecipientService recipientService;

    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    @PostMapping
    public ResponseEntity<Recipient> createRecipient(@Valid @RequestBody RecipientRequest request) {
        Recipient recipient = recipientService.createRecipient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipient);
    }

    @GetMapping
    public ResponseEntity<List<Recipient>> getAllRecipients() {
        return ResponseEntity.ok(recipientService.getAllRecipients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipient> getRecipientById(@PathVariable Long id) {
        return ResponseEntity.ok(recipientService.getRecipientById(id));
    }
}
