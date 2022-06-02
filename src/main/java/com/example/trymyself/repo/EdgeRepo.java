package com.example.trymyself.repo;

import com.example.trymyself.entities.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdgeRepo extends JpaRepository<Edge, Long> {
}
