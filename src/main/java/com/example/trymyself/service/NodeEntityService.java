package com.example.trymyself.service;


import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.entities.Edge;
import com.example.trymyself.entities.NodeEntity;
import com.example.trymyself.mapper.NodeEntityMapper;
import com.example.trymyself.repo.EdgeRepo;
import com.example.trymyself.repo.NodeEntityRepo;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NodeEntityService {
    private final NodeEntityRepo nodeEntityRepo;
    private final EdgeRepo edgeRepo;

    private final NodeEntityMapper nodeEntityMapper;

    //for searching
    public NodeEntityDto getNodeEntityDto(Long nodeEntityId) {
        NodeEntity nodeEntity = nodeEntityRepo.findById(nodeEntityId).get();
        List<Edge> edges = edgeRepo.getEdgesByNodeEntity(nodeEntityId);



        return nodeEntityMapper.toDto(nodeEntity, edges);

    }

}
