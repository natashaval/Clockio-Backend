package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
  Page<Activity> findAllByEmployee_IdOrderByDateDesc(UUID employeeId, Pageable pageable);

  @Query(value = "select * from activity a where a.employee_id=?1 and CAST(a.date as DATE) = ?2 " +
          "order by a.date desc", nativeQuery = true)
  List<Activity> findActivityTodayByEmployee(UUID employeeId, Date date);

  Page<Activity> findAllByEmployee_IdAndDateBetween(UUID employeeId,
                                                    @Temporal(TemporalType.DATE) Date start,
                                                    @Temporal(TemporalType.DATE) Date end,
                                                    Pageable pageable);
}
