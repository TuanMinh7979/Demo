package com.example.trymyself.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NodeEntity {
    @Id
    private long osmId;
    private String lat;
    private String lon;
    boolean visited;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "desNodeEntity")
    private Set<Edge> desOfEdges = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "srcNodeEntity")
    private Set<Edge> srcOfEdges = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "way_id")
    private Way way;


}
