package com.example.donatehub.repository;

import com.example.donatehub.entity.DonatedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonatedItemRepository extends JpaRepository<DonatedItem, Long> {

    // items still in stock
    List<DonatedItem> findByDistributedFalse();

    // used before deleting a drive
    boolean existsByDriveId(Long driveId);

    // sum of quantity (not row count) for one drive
    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM DonatedItem i WHERE i.drive.id = :driveId")
    Long getTotalCollected(@Param("driveId") Long driveId);

    @Query("SELECT COALESCE(SUM(i.quantity), 0) FROM DonatedItem i "
            + "WHERE i.drive.id = :driveId AND i.distributed = true")
    Long getTotalDistributed(@Param("driveId") Long driveId);
}
