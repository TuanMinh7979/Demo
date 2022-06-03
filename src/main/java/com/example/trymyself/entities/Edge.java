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
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "des_node_id")
    NodeEntity desNodeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "src_node_id")
    NodeEntity srcNodeEntity;

    Double distance;


//    public Long getNeighbour(Long id) {
//        if (id == this.srcNodeEntity.getId()) {
//            return this.desNodeEntity.getId();
//        } else if (id == this.srcNodeEntity.getId()) {
//            return this.desNodeEntity.getId();
//        } else {
//            return null;
//        }//End if else
//    }//End getNeighbour


}
