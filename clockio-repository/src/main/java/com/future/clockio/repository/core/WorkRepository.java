package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, String> {
}
