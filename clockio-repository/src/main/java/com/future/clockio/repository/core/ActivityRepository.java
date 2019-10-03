package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<Activity, String> {
}
