package com.example.trymyself.algo.searchalgo;

import com.example.trymyself.dto.EdgeDto;
import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.mapper.NodeEntityMapper;
import com.example.trymyself.repo.NodeEntityRepo;
import com.example.trymyself.service.NodeEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DijkstraSearchAlgo extends AbstractSearchAlgo {

    private final NodeEntityRepo nodeEntityRepo;

    private final NodeEntityMapper nodeEntityMapper;
    private final NodeEntityService nodeEntityService;

    public List<NodeEntityDto> findShortestPath(Long srcNodeId, Long targetId) {


        Map<Long, Boolean> visitedNodeMarks = new HashMap<>();
        List<NodeEntityDto> visitedNodes = new ArrayList<>();

        NodeEntityDto srcNode = nodeEntityService.getDto(srcNodeId);
        srcNode.setDistance(0.0);
        PriorityQueue<NodeEntityDto> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(srcNode);

        visitedNodeMarks.put(srcNodeId, true);
        visitedNodes.add(srcNode);

        int i = 0;
        while (!priorityQueue.isEmpty()) {
            NodeEntityDto curNode = priorityQueue.poll();

            List<EdgeDto> edges = nodeEntityService.getEdgeDtos(curNode.getId());
            for (EdgeDto edgeDtoi : edges) {
                NodeEntityDto neighBour = edgeDtoi.getNeighbour();
                if (visitedNodeMarks.get(neighBour.getId()) == null || visitedNodeMarks.get(neighBour.getId()) == false) {

                    Double newDis = curNode.getDistance() + edgeDtoi.getWeight();

                    if (newDis < neighBour.getDistance()) {
                        priorityQueue.remove(neighBour);
                        neighBour.setDistance(newDis);
                        neighBour.setPrev(curNode);
                        priorityQueue.add(neighBour);
                    }
                }

            }
            visitedNodeMarks.put(curNode.getId(), true);
            visitedNodes.add(curNode);

            i++;
            System.out.println("_______________________CURNOW LA______i = " + i);
        }


        NodeEntityDto target = null;
        for (NodeEntityDto rsi : visitedNodes) {
            if (rsi.getId().equals(targetId)) {
                target = rsi;
                break;
            }
        }
        List<NodeEntityDto> path = new ArrayList<>();
        for (NodeEntityDto node = target; node != null; node = node.getPrev()) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;


    }


}
