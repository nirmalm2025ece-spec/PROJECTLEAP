package com.example.donatehub.dto;

import java.time.LocalDate;

public class DistributionResponse {

    private Long id;
    private Long itemId;
    private Long recipientId;
    private String recipientName;
    private LocalDate distributedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public LocalDate getDistributedDate() {
        return distributedDate;
    }

    public void setDistributedDate(LocalDate distributedDate) {
        this.distributedDate = distributedDate;
    }
}
