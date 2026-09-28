package com.example.donatehub.service;

import com.example.donatehub.dto.DonorRequest;
import com.example.donatehub.entity.Donor;
import com.example.donatehub.exception.ResourceNotFoundException;
import com.example.donatehub.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public Donor createDonor(DonorRequest request) {
        Donor donor = new Donor();
        donor.setName(request.getName());
        donor.setPhone(request.getPhone());
        donor.setEmail(request.getEmail());
        return donorRepository.save(donor);
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id " + id));
    }
}
