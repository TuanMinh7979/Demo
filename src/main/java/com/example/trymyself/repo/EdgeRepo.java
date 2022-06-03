package com.example.trymyself.repo;

import com.example.trymyself.entities.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EdgeRepo extends JpaRepository<Edge, Long> {

    //for read out manytoone
    @Query(value = "select e from Edge e where e.srcNodeEntity.id = :nodeId or e.desNodeEntity.id = :nodeId")
    List<Edge> getEdgesByNodeEntity(@Param("nodeId") Long nodeId);

}
