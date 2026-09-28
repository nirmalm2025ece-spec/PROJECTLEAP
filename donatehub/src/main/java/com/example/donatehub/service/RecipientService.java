package com.example.donatehub.service;

import com.example.donatehub.dto.RecipientRequest;
import com.example.donatehub.entity.Recipient;
import com.example.donatehub.exception.ResourceNotFoundException;
import com.example.donatehub.repository.RecipientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public Recipient createRecipient(RecipientRequest request) {
        Recipient recipient = new Recipient();
        recipient.setOrganizationName(request.getOrganizationName());
        recipient.setContactPerson(request.getContactPerson());
        recipient.setPhone(request.getPhone());
        recipient.setAddress(request.getAddress());
        return recipientRepository.save(recipient);
    }

    public List<Recipient> getAllRecipients() {
        return recipientRepository.findAll();
    }

    public Recipient getRecipientById(Long id) {
        return recipientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found with id " + id));
    }
}
