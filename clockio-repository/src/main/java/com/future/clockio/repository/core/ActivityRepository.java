package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
//  List<Activity> findTop5ByEmployee_IdOrderByStartTimeDesc(UUID employeeId);
  Page<Activity> findAllByEmployee_IdOrderByDateDesc(UUID employeeId, Pageable pageable);
}
