package com.example.donatehub.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DistributionRequest {

    @NotNull(message = "Item id is required")
    private Long itemId;

    @NotNull(message = "Recipient id is required")
    private Long recipientId;

    @NotNull(message = "Distributed date is required")
    private LocalDate distributedDate;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public LocalDate getDistributedDate() {
        return distributedDate;
    }

    public void setDistributedDate(LocalDate distributedDate) {
        this.distributedDate = distributedDate;
    }
}
