package com.example.trymyself.entities;

import com.example.trymyself.converter.ListLongToStringConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "nodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NodeEntity {
    @Id
    private Long id;
    private String lat;
    private String lon;
    boolean visited;


    private Long prev;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "desNodeEntity")
    private Set<Edge> desOfEdges = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "srcNodeEntity")
    private Set<Edge> srcOfEdges = new HashSet<>();


    public Set<Edge> addSrcOfEdges(Edge edge) {
        this.srcOfEdges.add(edge);
        return this.srcOfEdges;
    }

    public Set<Edge> removeSrcOfEdges(Edge edge) {
        this.srcOfEdges.remove(edge);
        return this.srcOfEdges;
    }

    public Set<Edge> addDesOfEdges(Edge edge) {
        this.desOfEdges.add(edge);
        return this.desOfEdges;
    }

    public Set<Edge> removeDesOfEdges(Edge edge) {
        this.desOfEdges.remove(edge);
        return this.desOfEdges;
    }


}
