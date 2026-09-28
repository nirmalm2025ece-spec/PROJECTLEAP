package com.example.donatehub.dto;

import com.example.donatehub.entity.Category;
import com.example.donatehub.entity.Condition;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

// Note: there is no "distributed" field here on purpose.
// A new item is always saved as not distributed.
public class DonatedItemRequest {

    @NotNull(message = "Category is required (CLOTHES or BOOKS)")
    private Category category;

    @NotNull(message = "Condition is required (NEW, GOOD or WORN)")
    private Condition condition;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Donated date is required")
    private LocalDate donatedDate;

    @NotNull(message = "Drive id is required")
    private Long driveId;

    @NotNull(message = "Donor id is required")
    private Long donorId;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDonatedDate() {
        return donatedDate;
    }

    public void setDonatedDate(LocalDate donatedDate) {
        this.donatedDate = donatedDate;
    }

    public Long getDriveId() {
        return driveId;
    }

    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }

    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }
}
