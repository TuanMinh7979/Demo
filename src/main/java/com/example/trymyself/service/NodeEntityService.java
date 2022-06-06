package com.example.trymyself.service;


import com.example.trymyself.dto.EdgeDto;
import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.entities.Edge;
import com.example.trymyself.entities.NodeEntity;
import com.example.trymyself.mapper.EdgeMapper;
import com.example.trymyself.mapper.NodeEntityMapper;
import com.example.trymyself.repo.EdgeRepo;
import com.example.trymyself.repo.NodeEntityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NodeEntityService {
    private final NodeEntityRepo nodeEntityRepo;
    private final EdgeRepo edgeRepo;
    private final NodeEntityMapper nodeEntityMapper;

    private final EdgeMapper edgeMapper;


    public List<EdgeDto> getEdgeDtos(Long nodeEntityId) {
        List<Edge> edgeBySrc = edgeRepo.getEdgesBySrcNodeEntity(nodeEntityId).stream().collect(Collectors.toList());
        List<Edge> egdeByDes = edgeRepo.getEdgesByDesNodeEntity(nodeEntityId).stream().collect(Collectors.toList());
        List<Edge> allEdge = new ArrayList();
        allEdge.addAll(edgeBySrc);
        allEdge.addAll(egdeByDes);

        List<EdgeDto> edgeDtos = new ArrayList<>();
        for (Edge e : allEdge) {
            EdgeDto edto = edgeMapper.toDto(nodeEntityId, e);
            edgeDtos.add(edto);
        }

        return edgeDtos;

    }

    public NodeEntityDto getDto(Long nodeId) {
        return nodeEntityMapper.toDto(nodeEntityRepo.findById(nodeId).get());
    }


}
