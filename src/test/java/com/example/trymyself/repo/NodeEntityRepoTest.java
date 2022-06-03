package com.example.trymyself.repo;

import com.example.trymyself.entities.Edge;
import com.example.trymyself.entities.NodeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class NodeEntityRepoTest {

    @Autowired
    private NodeEntityRepo nodeEntityRepo;

    @Test
    public void test() {

        String s = "abced";
        s = s.substring(0, s.length());
//        NodeEntity nodeEntity = nodeEntityRepo.findById(1340701136L).get();
////        NodeEntity nodeEntity = nodeEntityRepo.getNodeEntityWithSrcOfEdgeSet(1038748282L);
//        System.out.println(nodeEntity.getId());
//        System.out.println(nodeEntity.getLat());
    }

}