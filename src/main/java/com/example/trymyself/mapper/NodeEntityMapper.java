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
    public NodeEntityDto toFullDto(NodeEntity model, List<Edge> dep) {
        NodeEntityDto dto = new NodeEntityDto();
        Map<Long, Double> neighbourDistanceMap = new HashMap<>();
//        System.out.println("************************&*&*&*&*&*&*&*&*&*&*&*&******************" + model.getId());
//        System.out.println("______________" + dep.size());


        for (Edge edgei : dep) {

            long curId = model.getId();
            long desNodeId = edgei.getDesNodeEntity().getId();
            long srcNodeId = edgei.getSrcNodeEntity().getId();
            //Long Long must use equal
            if (curId == desNodeId) {
                neighbourDistanceMap.put(srcNodeId, edgei.getDistance());
            }
            if (curId == srcNodeId) {
                neighbourDistanceMap.put(desNodeId, edgei.getDistance());
            }
        }

        dto.setId(model.getId());
        dto.setLon(model.getLon());
        dto.setLat(model.getLat());
        dto.setNeighbourDistanceMap(neighbourDistanceMap);


        return dto;
    }

    public NodeEntityDto toDto(NodeEntity model) {
        NodeEntityDto dto = new NodeEntityDto();

        dto.setId(model.getId());
        dto.setLon(model.getLon());
        dto.setLat(model.getLat());
        return dto;
    }

}

