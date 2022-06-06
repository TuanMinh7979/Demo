package com.example.trymyself.repo;

import com.example.trymyself.entities.Edge;
import com.example.trymyself.entities.NodeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class EdgeRepoTest {

    @Autowired
    private EdgeRepo edgeRepo;

    @Test
    public void test() {
        Set<Edge> rs = edgeRepo.getEdgesBySrcNodeEntity(5177656451L);
//        }
        for (Edge rsi : rs) {
            NodeEntity desNode = rsi.getDesNodeEntity();
            System.out.println(desNode.getLat() + "   " + desNode.getLon());
        }

    }

}