package com.example.trymyself.controller;

import com.example.trymyself.algo.searchalgo.DijkstraSearchAlgo;
import com.example.trymyself.dto.NodeEntityDto;
import com.example.trymyself.repo.NodeEntityRepo;
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
    private final NodeEntityRepo nodeEntityRepo;
    private final DijkstraSearchAlgo dijkstraSearchAlgo;


    @RequestMapping("")
    public String index(@RequestParam(value = "srcnodeid", required = false) Long srcNodeId,
                        @RequestParam(value = "desnodeid", required = false) Long desNodeId) {

        return "home/index";
    }

    @RequestMapping("setuposm")
    @ResponseBody
    public String setupOSM() {
        osmToDB.setupData();
        return "sc";
    }


    @GetMapping("api/search")
    @ResponseBody
    public List<NodeEntityDto> getShortestPath(
            @RequestParam(value = "srcnodeid") Long srcNodeId,
            @RequestParam(value = "desnodeid") Long desNodeId) {

        if (srcNodeId <= 888 && desNodeId <= 888) {
            srcNodeId = nodeEntityRepo.getNodeEntityBySeid(srcNodeId).getId();
            desNodeId = nodeEntityRepo.getNodeEntityBySeid(desNodeId).getId();
        } else if (srcNodeId <= 888) {
            srcNodeId = nodeEntityRepo.getNodeEntityBySeid(srcNodeId).getId();
        } else {
            desNodeId = nodeEntityRepo.getNodeEntityBySeid(desNodeId).getId();
        }

        return dijkstraSearchAlgo.findShortestPath(srcNodeId, desNodeId);

    }


}

