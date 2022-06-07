package com.example.trymyself.algo.searchalgo;


import com.example.trymyself.dto.PointDto;
import com.example.trymyself.mapper.PointMapper;
import com.example.trymyself.model.AdjListGraph;
import com.example.trymyself.model.AdjListGraphItem;
import com.example.trymyself.model.Edge;
import com.example.trymyself.model.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DijkstraSearchAlgo extends AbstractSearchAlgo {

    private final AdjListGraph adjListGraph;
    private final PointMapper pointMapper;


    public List<PointDto> findShortestPath(Long srcNodeId, Long targetId) {

        Map<Long, Integer> nodeIdIdxMap = adjListGraph.getNodeIdIdxMap();

        List<AdjListGraphItem> data = adjListGraph.getData();


        Map<Long, Boolean> visitedNodeMarks = new HashMap<>();
        List<PointDto> visitedNodes = new ArrayList<>();


        int srcNodeIdx = nodeIdIdxMap.get(srcNodeId);
        PointDto srcPointDto = pointMapper.toDto(data.get(srcNodeIdx).getPoint());


        srcPointDto.setDistance(0.0);

        PriorityQueue<PointDto> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(srcPointDto);

        visitedNodeMarks.put(srcPointDto.getId(), true);
        visitedNodes.add(srcPointDto);


        while (!priorityQueue.isEmpty()) {
            PointDto curPointDto = priorityQueue.poll();

            //add
            AdjListGraphItem curItem = data.get(nodeIdIdxMap.get(curPointDto.getId()));
            //add
            List<Edge> curEdges = curItem.getEdges();

            for (Edge edgei : curEdges) {
                int neighBourIdx = edgei.getNeighBourIdx();
                PointDto neighBouri = pointMapper.toDto(data.get(neighBourIdx).getPoint());

                if (visitedNodeMarks.get(neighBouri.getId()) == null || visitedNodeMarks.get(neighBouri.getId()) == false) {

                    Double newDis = curPointDto.getDistance() + edgei.getWeight();

                    if (newDis < neighBouri.getDistance()) {
                        priorityQueue.remove(neighBouri);
                        neighBouri.setDistance(newDis);
                        neighBouri.setPrev(curPointDto);

                        priorityQueue.add(neighBouri);
                    }
                }

            }
            visitedNodeMarks.put(curPointDto.getId(), true);
            visitedNodes.add(curPointDto);


        }


        PointDto target = null;
        for (PointDto rsi : visitedNodes) {
            if (rsi.getId().equals(targetId)) {
                target = rsi;
                break;
            }
        }
        List<PointDto> path = new ArrayList<>();
        for (PointDto node = target; node != null; node = node.getPrev()) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;


    }


}
