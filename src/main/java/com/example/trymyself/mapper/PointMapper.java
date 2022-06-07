package com.example.trymyself.mapper;

import com.example.trymyself.dto.PointDto;
import com.example.trymyself.model.Point;
import org.springframework.stereotype.Component;

@Component
public class PointMapper {
    public PointDto toDto(Point model) {

        PointDto pointDto = new PointDto();
        pointDto.setId(model.getId());
        pointDto.setLat(model.getLat());
        pointDto.setLon(model.getLon());
        return pointDto;
    }
}
