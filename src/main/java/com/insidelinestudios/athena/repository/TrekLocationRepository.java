package com.insidelinestudios.athena.repository;

import com.insidelinestudios.athena.model.TrekLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrekLocationRepository extends JpaRepository<TrekLocation, Long> {
}
