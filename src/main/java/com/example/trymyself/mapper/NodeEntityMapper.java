package com.example.trymyself.mapper;

import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.entities.Edge;
import com.example.trymyself.entities.NodeEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NodeEntityMapper {
    public NodeEntityDto toDto(NodeEntity model, List<Edge> dep) {
        NodeEntityDto dto = new NodeEntityDto();
        Map<Long, Double> neighbourDistanceMap = new HashMap<>();
//        System.out.println("************************&*&*&*&*&*&*&*&*&*&*&*&******************" + model.getId());
//        System.out.println("______________" + dep.size());
        for (Edge edgei : dep) {
            neighbourDistanceMap.put(edgei.getDesNodeEntity().getId() == model.getId() ?
                    edgei.getSrcNodeEntity().getId()
                    : edgei.getDesNodeEntity().getId(), edgei.getDistance());
        }
        dto.setId(model.getId());
        dto.setLon(model.getLon());
        dto.setLat(model.getLat());
        dto.setNeighbourDistanceMap(neighbourDistanceMap);
        return dto;
    }
}
