package com.example.trymyself.entities;

import com.example.trymyself.util.Haversine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "edges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "way_id")
    private Way way;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "des_node_id")
    NodeEntity desNodeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "src_node_id")
    NodeEntity srcNodeEntity;

    Double distance = null;
    boolean visited;


    public NodeEntity getNeighbour(NodeEntity v) {
        if (v == this.srcNodeEntity) {
            return this.desNodeEntity;
        } else if (v == this.srcNodeEntity) {
            return this.desNodeEntity;
        } else {
            return null;
        }//End if else
    }//End getNeighbour

    public Double calculateDistance(NodeEntity source, NodeEntity target) {
        return Haversine.distance(Double.parseDouble(source.getLat()), Double.parseDouble(source.getLon()),
                Double.parseDouble(target.getLat()), Double.parseDouble(target.getLon()));
    }

    public Edge(Way way, NodeEntity desNodeEntity, NodeEntity srcNodeEntity) {
        this.way = way;
        this.desNodeEntity = desNodeEntity;
        this.srcNodeEntity = srcNodeEntity;
    }
}
