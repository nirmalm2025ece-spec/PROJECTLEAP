package com.example.donatehub.dto;

public class DriveSummaryResponse {

    private Long driveId;
    private String driveName;
    private long totalItemsCollected;
    private long totalItemsDistributed;
    private long totalItemsRemaining;

    public DriveSummaryResponse(Long driveId, String driveName, long collected, long distributed) {
        this.driveId = driveId;
        this.driveName = driveName;
        this.totalItemsCollected = collected;
        this.totalItemsDistributed = distributed;
        this.totalItemsRemaining = collected - distributed;
    }

    public Long getDriveId() {
        return driveId;
    }

    public String getDriveName() {
        return driveName;
    }

    public long getTotalItemsCollected() {
        return totalItemsCollected;
    }

    public long getTotalItemsDistributed() {
        return totalItemsDistributed;
    }

    public long getTotalItemsRemaining() {
        return totalItemsRemaining;
    }
}
