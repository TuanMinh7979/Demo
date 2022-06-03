package com.example.trymyself.repo;

import com.example.trymyself.entities.NodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface NodeEntityRepo extends JpaRepository<NodeEntity, Long> {


    //for writein of onetomany
    @Query(value = "select n from NodeEntity n left join fetch n.srcOfEdges where n.id = :id")
    NodeEntity getNodeEntityWithSrcOfEdgeSet(@Param("id") Long id);

    //for writein of onetomany
    @Query(value = "select n from NodeEntity n left join fetch n.desOfEdges where n.id = :id ")
    NodeEntity getNodeEntityWithDesOfEdgeSet(@Param("id") Long id);


}
