package com.example.trymyself.dto;

import com.example.trymyself.model.Point;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PointDto implements Comparable<PointDto> {


    private Long id;
    private String lat;
    private String lon;

    @JsonIgnore
    boolean visited = false;

    @JsonIgnore
    private PointDto prev;


    private Double distance = Double.MAX_VALUE;


    @Override
    public int compareTo(PointDto o) {
        return Double.compare(this.distance, o.getDistance());
    }
}
