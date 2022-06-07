package com.example.trymyself.model;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
@Getter
@Setter

public class AdjListGraph {
    //nodeid => index in list
    private Map<Long, Integer> nodeIdIdxMap = new HashMap<>();

    private List<AdjListGraphItem> data = new ArrayList<>();


    private String csvFilePath;

    public void setupGraph() throws IOException {

        CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFilePath))
                .build();
        List<String[]> csvBody = csvReader.readAll();
        for (String[] csvLine : csvBody) {
            nodeIdIdxMap.put(Long.valueOf(csvLine[1]), Integer.parseInt(csvLine[0]));

        }

        for (String[] csvLine : csvBody) {
            Point point = new Point();
            point.setId(Long.valueOf(csvLine[1]));
            point.setLat(csvLine[2]);
            point.setLon(csvLine[3]);


            String fileEdgesStr = csvLine[4];
            List<Edge> edgesData = new ArrayList<>();
            List<String> fileEdges = new ArrayList<>(Arrays.asList(fileEdgesStr.split(" ")));
            for (String edgei : fileEdges) {
                String[] edgePair = edgei.split(":");
                Edge newEdge = new Edge();
                newEdge.setNeighBourIdx(Integer.valueOf(edgePair[0]));
                newEdge.setWeight(Double.valueOf(edgePair[1]));
                edgesData.add(newEdge);
            }

            data.add(new AdjListGraphItem(point, edgesData));


        }
        csvReader.close();
    }

}


