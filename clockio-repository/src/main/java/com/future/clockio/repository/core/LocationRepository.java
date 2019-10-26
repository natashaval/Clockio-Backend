package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {

}
