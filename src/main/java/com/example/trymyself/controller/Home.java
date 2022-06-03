package com.example.trymyself.controller;

import com.example.trymyself.algo.searchalgo.DijkstraSearchAlgo;
import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.util.OsmToDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class Home {

    private final OsmToDB osmToDB;
    private final DijkstraSearchAlgo dijkstraSearchAlgo;

    @RequestMapping("settup")
    @ResponseBody
    public String index() {
        osmToDB.setupData();
        return "sc";
    }


    @GetMapping("short-path")
    @ResponseBody
    public List<NodeEntityDto> getShortestPath(
            @RequestParam(value = "srcnodeid") Long srcNodeId) {


        return dijkstraSearchAlgo.findShortestPath(srcNodeId);

    }
}

