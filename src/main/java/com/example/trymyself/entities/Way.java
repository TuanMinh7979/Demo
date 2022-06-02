package com.example.trymyself.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ways")
@Getter
@Setter

@AllArgsConstructor
public class Way {
    @Id
    private long osmId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "way")
    private Set<NodeEntity> nodeEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "way")
    private Set<Edge> edges;

    private long srcNodeOsmId;
    private long desNodeOsmId;

    public Way(long osmId) {
        this.osmId = osmId;
    }

    public Way() {
    }
}
