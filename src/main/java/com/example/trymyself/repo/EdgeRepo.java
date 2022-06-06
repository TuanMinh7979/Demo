package com.example.trymyself.repo;

import com.example.trymyself.entities.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EdgeRepo extends JpaRepository<Edge, Long> {

    //for read out manytoone
    @Query(value = "select e from Edge e join fetch e.desNodeEntity where e.srcNodeEntity.id = :nodeId")
    Set<Edge> getEdgesBySrcNodeEntity(@Param("nodeId") Long nodeId);

    @Query(value = "select e from Edge e join fetch e.srcNodeEntity where e.desNodeEntity.id = :nodeId")
    Set<Edge> getEdgesByDesNodeEntity(@Param("nodeId") Long nodeId);





}
