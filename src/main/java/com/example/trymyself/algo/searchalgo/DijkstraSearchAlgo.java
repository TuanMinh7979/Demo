package com.example.trymyself.algo.searchalgo;

import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.repo.NodeEntityRepo;
import com.example.trymyself.service.NodeEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DijkstraSearchAlgo extends AbstractSearchAlgo {

    private final NodeEntityRepo nodeEntityRepo;
    private final NodeEntityService nodeEntityService;


    public NodeEntityDto getLowestDistanceEdge(Set<NodeEntityDto> unsettledNodes) {

        NodeEntityDto lowestDistanceNode = null;
        Double lowestDistance = Double.MAX_VALUE;
        for (NodeEntityDto node : unsettledNodes) {
            Double nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }


    public List<NodeEntityDto> findShortestPath(Long srcNodeId) {


        Set<NodeEntityDto> settledNodes = new HashSet<>();
        Set<NodeEntityDto> unsettledNodes = new HashSet<>();

        NodeEntityDto srcNode = nodeEntityService.getNodeEntityDto(srcNodeId);
        srcNode.setDistance(0.0);


        unsettledNodes.add(srcNode);

        while (unsettledNodes.size() != 0) {
            System.out.println("HEAR 5 MANY TIME");
            NodeEntityDto curNode = getLowestDistanceEdge(unsettledNodes);
            unsettledNodes.remove(curNode);
            for (Map.Entry<Long, Double> adjacencyPair :
                    curNode.getNeighbourDistanceMap().entrySet()) {
                System.out.println("HEAR FOR 2 MANY TIME");
                NodeEntityDto adjacentNode = nodeEntityService.getNodeEntityDto(adjacencyPair.getKey());
                Double edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {

                    calculateMinimumDistance(adjacentNode, edgeWeight, curNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(curNode);
        }

        return settledNodes.stream().collect(Collectors.toList());
    }

    private void calculateMinimumDistance(NodeEntityDto evaluationNode,
                                          Double edgeWeigh, NodeEntityDto sourceNode) {
        Double sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            System.out.println("HEAR 5 MANY TIME");
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            List<NodeEntityDto> shortestPath = sourceNode.getShortestPath();
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }


}
