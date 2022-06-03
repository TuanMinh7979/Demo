package com.example.trymyself.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NodeEntityDto {

    private Long id;
    private String lat;
    private String lon;

    //addtional for searching
    boolean visited;
    private Long prev;
    private Map<Long, Double> neighborDistanceMap = new HashMap<>();

    private Double distance;

    private List<Long> shortestPath = new ArrayList<>();


//    List<EdgeDto>
}
