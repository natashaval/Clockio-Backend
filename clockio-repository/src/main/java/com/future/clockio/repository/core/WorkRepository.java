package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Work;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkRepository extends MongoRepository<Work, String> {
}
