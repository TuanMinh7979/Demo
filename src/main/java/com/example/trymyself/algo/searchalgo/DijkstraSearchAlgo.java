package com.example.trymyself.algo.searchalgo;

import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.service.NodeEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DijkstraSearchAlgo extends AbstractSearchAlgo {

    private final NodeEntityService nodeEntityService;


    public List<NodeEntityDto> findShortestPath(Long srcNodeId, Long targetId) {
        Map<Long, Boolean> visitedNodeMarks = new HashMap<>();
        List<NodeEntityDto> visitedNodes = new ArrayList<>();

        NodeEntityDto srcNode = nodeEntityService.getNodeEntityDto(srcNodeId);
        srcNode.setDistance(0.0);
        PriorityQueue<NodeEntityDto> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(srcNode);

        visitedNodeMarks.put(srcNodeId, true);
        visitedNodes.add(srcNode);

        while (!priorityQueue.isEmpty()) {
            NodeEntityDto curNode = priorityQueue.poll();
            for (Long nbiId : curNode.getNeighbourDistanceMap().keySet()) {

                if (visitedNodeMarks.get(nbiId) == null || visitedNodeMarks.get(nbiId) == false) {
                    NodeEntityDto nbi = nodeEntityService.getNodeEntityDto(nbiId);
                    Double newDis = curNode.getDistance() + curNode.getNeighbourDistanceMap().get(nbiId);

                    if (newDis < nbi.getDistance()) {
                        priorityQueue.remove(nbi);
                        nbi.setDistance(newDis);
                        nbi.setPrev(curNode);
                        priorityQueue.add(nbi);
                    }
                }

            }
            visitedNodeMarks.put(curNode.getId(), true);
            visitedNodes.add(curNode);
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
