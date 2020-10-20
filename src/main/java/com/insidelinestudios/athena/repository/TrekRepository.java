package com.insidelinestudios.athena.repository;

import com.insidelinestudios.athena.model.Trek;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrekRepository extends JpaRepository<Trek, Long> {
}
