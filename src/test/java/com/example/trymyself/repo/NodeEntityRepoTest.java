package com.example.trymyself.repo;

import com.example.trymyself.entities.NodeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class NodeEntityRepoTest {

    @Autowired
    private NodeEntityRepo nodeEntityRepo;

    @Test
    public void test() {
//        Set<NodeEntity> rs= nodeEntityRepo.getBunchOfNodeEntity(Arrays.asList(88978131L, 88984173L, 540441905L));

//        for(NodeEntity n: rs){
//            System.out.println(n.getSrcOfEdges());
//        }

    }

}