package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, UUID> {
}
