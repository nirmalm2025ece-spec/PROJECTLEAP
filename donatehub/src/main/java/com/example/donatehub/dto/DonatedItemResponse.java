package com.example.donatehub.dto;

import com.example.donatehub.entity.Category;
import com.example.donatehub.entity.Condition;

import java.time.LocalDate;

public class DonatedItemResponse {

    private Long id;
    private Category category;
    private Condition condition;
    private int quantity;
    private LocalDate donatedDate;
    private boolean distributed;
    private Long driveId;
    private String driveName;
    private Long donorId;
    private String donorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDonatedDate() {
        return donatedDate;
    }

    public void setDonatedDate(LocalDate donatedDate) {
        this.donatedDate = donatedDate;
    }

    public boolean isDistributed() {
        return distributed;
    }

    public void setDistributed(boolean distributed) {
        this.distributed = distributed;
    }

    public Long getDriveId() {
        return driveId;
    }

    public void setDriveId(Long driveId) {
        this.driveId = driveId;
    }

    public String getDriveName() {
        return driveName;
    }

    public void setDriveName(String driveName) {
        this.driveName = driveName;
    }

    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
}
