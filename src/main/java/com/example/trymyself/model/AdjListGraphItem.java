package com.example.trymyself.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class AdjListGraphItem {
    private Point point;
    private List<Edge> edges;
}
