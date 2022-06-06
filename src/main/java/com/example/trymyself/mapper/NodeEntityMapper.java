package com.example.trymyself.mapper;

import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.entities.Edge;
import com.example.trymyself.entities.NodeEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodeEntityMapper {

    public NodeEntityDto toDto(NodeEntity model) {
        NodeEntityDto dto = new NodeEntityDto();
        dto.setId(model.getId());
        dto.setLon(model.getLon());
        dto.setLat(model.getLat());
        return dto;
    }


}

