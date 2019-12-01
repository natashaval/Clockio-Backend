package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
  List<Location> findAllByEmployee_IdAndCreatedAtBetween(UUID employeeId, Date startDate,
                                                         Date endDate);
}
