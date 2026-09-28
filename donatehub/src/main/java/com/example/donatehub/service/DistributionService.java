package com.example.donatehub.service;

import com.example.donatehub.dto.DistributionRequest;
import com.example.donatehub.dto.DistributionResponse;
import com.example.donatehub.entity.DonatedItem;
import com.example.donatehub.entity.Distribution;
import com.example.donatehub.entity.Recipient;
import com.example.donatehub.exception.BusinessRuleException;
import com.example.donatehub.repository.DistributionRepository;
import com.example.donatehub.repository.DonatedItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistributionService {

    private final DistributionRepository distributionRepository;
    private final DonatedItemRepository donatedItemRepository;
    private final DonatedItemService donatedItemService;
    private final RecipientService recipientService;

    public DistributionService(DistributionRepository distributionRepository,
                               DonatedItemRepository donatedItemRepository,
                               DonatedItemService donatedItemService,
                               RecipientService recipientService) {
        this.distributionRepository = distributionRepository;
        this.donatedItemRepository = donatedItemRepository;
        this.donatedItemService = donatedItemService;
        this.recipientService = recipientService;
    }

    // @Transactional so the distribution and the "distributed = true" update
    // are saved together or not at all
    @Transactional
    public DistributionResponse distributeItem(DistributionRequest request) {
        // 1. item must exist (404 if not)
        DonatedItem item = donatedItemService.findItem(request.getItemId());

        // 2. recipient must exist (404 if not)
        Recipient recipient = recipientService.getRecipientById(request.getRecipientId());

        // 3. an item cannot be given away twice
        if (item.isDistributed()) {
            throw new BusinessRuleException("This item has already been distributed");
        }

        // 4. cannot give away something before it was collected
        if (request.getDistributedDate().isBefore(item.getDonatedDate())) {
            throw new BusinessRuleException("Distributed date cannot be before the donated date");
        }

        // all checks passed - now change the database
        Distribution distribution = new Distribution();
        distribution.setItem(item);
        distribution.setRecipient(recipient);
        distribution.setDistributedDate(request.getDistributedDate());
        Distribution saved = distributionRepository.save(distribution);

        item.setDistributed(true);
        donatedItemRepository.save(item);

        return toResponse(saved);
    }

    public List<DistributionResponse> getAllDistributions() {
        return distributionRepository.findAll().stream().map(this::toResponse).toList();
    }

    private DistributionResponse toResponse(Distribution distribution) {
        DistributionResponse response = new DistributionResponse();
        response.setId(distribution.getId());
        response.setItemId(distribution.getItem().getId());
        response.setRecipientId(distribution.getRecipient().getId());
        response.setRecipientName(distribution.getRecipient().getOrganizationName());
        response.setDistributedDate(distribution.getDistributedDate());
        return response;
    }
}
