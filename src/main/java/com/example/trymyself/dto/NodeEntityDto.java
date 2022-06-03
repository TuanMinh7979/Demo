package com.example.trymyself.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NodeEntityDto implements Comparable<NodeEntityDto> {

    private Long id;
    private String lat;
    private String lon;

    //addtional for searching
    @JsonIgnore
    boolean visited = false;

    @JsonIgnore
    private NodeEntityDto prev;

    @JsonIgnore
    private Map<Long, Double> neighbourDistanceMap = new HashMap<>();

    //    @JsonIgnore
    private Double distance = Double.MAX_VALUE;

    @Override
    public int compareTo(NodeEntityDto o) {
        return Double.compare(this.distance, o.getDistance());
    }
}


//    List<EdgeDto>

