package com.example.trymyself.controller;

import com.example.trymyself.algo.searchalgo.DijkstraSearchAlgo;
import com.example.trymyself.dto.PointDto;
import com.example.trymyself.model.AdjListGraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class Home {


    private final DijkstraSearchAlgo dijkstraSearchAlgo;
    private final AdjListGraph adjListGraph;


    @RequestMapping("")
    public String index(@RequestParam(value = "srcnodeid", required = false) Long srcNodeId,
                        @RequestParam(value = "desnodeid", required = false) Long desNodeId) {

        return "home/index";
    }

    @RequestMapping("setup-graph")
    @ResponseBody
    public String createGraph() throws IOException {
        adjListGraph.setCsvFilePath("D:\\COCCOC\\map.csv");
        adjListGraph.setupGraph();
        return "sc";
    }


    @GetMapping("api/search")
    @ResponseBody
    public List<PointDto> getShortestPath(
            @RequestParam(value = "srcnodeid") Long srcNodeId,
            @RequestParam(value = "desnodeid") Long desNodeId) {

        return dijkstraSearchAlgo.findShortestPath(srcNodeId, desNodeId);


    }


}

