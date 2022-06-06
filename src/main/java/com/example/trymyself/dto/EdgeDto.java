package com.example.trymyself.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class EdgeDto {
    private Long id;

    private Double weight;

    private NodeEntityDto neighbour;
}
