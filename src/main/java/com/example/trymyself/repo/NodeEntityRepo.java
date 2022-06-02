package com.example.trymyself.repo;

import com.example.trymyself.entities.NodeEntity;
import com.example.trymyself.entities.Way;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeEntityRepo extends JpaRepository<NodeEntity, Long> {

}
