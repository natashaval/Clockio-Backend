package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, String> {
}
