package com.example.trymyself.repo;

import com.example.trymyself.entities.Way;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WayRepo extends JpaRepository<Way, Long> {
}
