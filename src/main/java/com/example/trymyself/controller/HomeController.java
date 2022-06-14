package com.example.trymyself.controller;

import com.example.trymyself.algo.searchalgo.DijkstraSearchAlgo;
import com.example.trymyself.dto.PointDto;
import com.example.trymyself.model.AdjListGraph;
import com.example.trymyself.util.OSMToCSV;
import com.example.trymyself.util.UpdateOSM;
import com.example.trymyself.util.UploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class HomeController {


    private final DijkstraSearchAlgo dijkstraSearchAlgo;
    private final AdjListGraph adjListGraph;
    private final UploadUtil uploadUtil;
    private final UpdateOSM updateOSM;
    private final OSMToCSV osmToCSV;



    @RequestMapping("")
    public String index(Model model,
                        @RequestParam(value = "srcnodeid", required = false) Long srcNodeId,
                        @RequestParam(value = "desnodeid", required = false) Long desNodeId
    ) {

        return "home/index";
    }


    @GetMapping("admin")
    public String index() {
        return "admin/index";
    }

    @PostMapping("admin")
    @ResponseBody
    public String addNewOSMXmlFile(@RequestParam("file") MultipartFile file) {
        File savedFile = uploadUtil.handelUploadFile(file);
        String osmFilePath = savedFile.getPath();
        updateOSM.updateOsmXmlData(osmFilePath);
        String dir = System.getProperty("user.dir");
        String csvStrPath = dir + "\\src\\main\\webapp\\resource\\csv\\data.csv";
        osmToCSV.toCSVData(osmFilePath, csvStrPath);

        return "success";
    }

    @GetMapping("admin/setup-graph")
    @ResponseBody
    public String setupGraph() {
        String dir = System.getProperty("user.dir");
        String csvStrPath = dir + "\\src\\main\\webapp\\resource\\csv\\data.csv";
        adjListGraph.setupGraph(csvStrPath);
        return "Setup graph success";
    }


    @GetMapping("api/search")
    @ResponseBody
    public List<PointDto> getShortestPath(
            @RequestParam(value = "srcnodeid") Long srcNodeId,
            @RequestParam(value = "desnodeid") Long desNodeId) {

        return dijkstraSearchAlgo.findShortestPath(srcNodeId, desNodeId);
    }


}

