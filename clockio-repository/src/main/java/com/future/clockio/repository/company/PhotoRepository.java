package com.future.clockio.repository.company;

import com.future.clockio.entity.company.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, String> {

  List<Photo> findByEmployee_IdAndMainPhoto(String id, boolean main);
}
