package com.example.donatehub.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

// Records which item went to which recipient and when.
// A separate table is needed because "distributed = true" alone
// does not tell us WHERE the item was given.
@Entity
@Table(name = "distributions")
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unique = true: one item can appear in only one distribution
    @OneToOne
    @JoinColumn(name = "item_id", nullable = false, unique = true)
    private DonatedItem item;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Recipient recipient;

    @Column(nullable = false)
    private LocalDate distributedDate;

    public Distribution() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DonatedItem getItem() {
        return item;
    }

    public void setItem(DonatedItem item) {
        this.item = item;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public LocalDate getDistributedDate() {
        return distributedDate;
    }

    public void setDistributedDate(LocalDate distributedDate) {
        this.distributedDate = distributedDate;
    }
}
