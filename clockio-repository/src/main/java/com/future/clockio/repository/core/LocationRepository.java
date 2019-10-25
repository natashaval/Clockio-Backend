package com.future.clockio.repository.core;

import com.future.clockio.entity.core.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {

}
