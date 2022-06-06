package com.example.trymyself.mapper;

import com.example.trymyself.dto.EdgeDto;
import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.entities.Edge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EdgeMapper {
    @Autowired
    private NodeEntityMapper nodeEntityMapper;

    public EdgeDto toDto(Long fromNodeId, Edge edge) {
        NodeEntityDto neighBourDto = new NodeEntityDto();

        if (edge.getDesNodeEntity().getId().equals(fromNodeId)) {
            neighBourDto = nodeEntityMapper.toDto(edge.getSrcNodeEntity());
        }

        if (edge.getSrcNodeEntity().getId().equals(fromNodeId)) {
            neighBourDto = nodeEntityMapper.toDto(edge.getDesNodeEntity());

        }
        EdgeDto dto = new EdgeDto();
        dto.setWeight(edge.getWeight());
        dto.setId(edge.getId());
        dto.setNeighbour(neighBourDto);
        return dto;
    }

}
