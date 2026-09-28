package com.example.donatehub.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "donated_items")
public class DonatedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    // "condition" is a reserved word in MySQL, so the column gets a different name
    @Enumerated(EnumType.STRING)
    @Column(name = "item_condition", nullable = false)
    private Condition condition;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDate donatedDate;

    // false until the item is given to a recipient
    @Column(nullable = false)
    private boolean distributed = false;

    @ManyToOne
    @JoinColumn(name = "drive_id", nullable = false)
    private Drive drive;

    @ManyToOne
    @JoinColumn(name = "donor_id", nullable = false)
    private Donor donor;

    public DonatedItem() {
    }

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

    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }
}
