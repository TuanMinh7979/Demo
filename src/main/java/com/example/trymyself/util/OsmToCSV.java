package com.example.trymyself.util;

import com.example.trymyself.entities.Edge;
import com.example.trymyself.entities.NodeEntity;
import com.example.trymyself.repo.EdgeRepo;
import com.example.trymyself.repo.NodeEntityRepo;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class OsmToCSV {
    //    private MultipartFile osmFile;


    public void setupData() throws IOException {
        try {

            SAXReader reader = new SAXReader();
            Document document = reader.read(new File("D:\\COCCOC\\map.osm"));

            List<Node> osmNodeNodes = document.selectNodes("//node");
            List<Node> osmWayNodes = document.selectNodes("//way");
            setUpNodes(osmNodeNodes);
            setUpEdges(osmNodeNodes, osmWayNodes);


        } catch (DocumentException e) {
            e.printStackTrace();
        }//End try catch

    }


    public void setUpNodes(List<Node> osmNodeNodes) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter("D:\\COCCOC\\map.csv"));

        for (int i = 0; i < osmNodeNodes.size(); i++) {
            Node node = osmNodeNodes.get(i);


//
            List<String> list = new ArrayList<>();


            list.add(String.valueOf(i));
            list.add(node.valueOf("@id"));
            list.add(node.valueOf("@lat"));
            list.add(node.valueOf("@lon"));
            list.add(" ");

            String[] array = list.toArray(new String[0]);
            writer.writeNext(array);


        }
        writer.close();
    }

    //
    public void setUpEdges(List<Node> osmNodeNodes, List<Node> osmWayNodes) throws IOException {

        HashMap<Long, Integer> nodeIdIdxMap = new HashMap<>();
        for (int i = 0; i < osmNodeNodes.size(); i++) {
            Node node = osmNodeNodes.get(i);
            nodeIdIdxMap.put(Long.valueOf(node.valueOf("@id")), i);
        }

        CSVReader csvReader = new CSVReaderBuilder(new FileReader("D:\\COCCOC\\map.csv"))
                .build();
        List<String[]> csvBody = csvReader.readAll();
        CSVWriter writer = new CSVWriter(new FileWriter("D:\\COCCOC\\map.csv"), ',');

  
        for (Node wayi : osmWayNodes) {

            if (wayi.hasContent()) {

                List<Node> wayPointNodes = wayi.selectNodes("nd");


                for (int i = 0; i < wayPointNodes.size() - 1; i++) {


                    Node node1 = wayPointNodes.get(i);
                    int node1MapIdx = nodeIdIdxMap.get(Long.valueOf(node1.valueOf("@ref")));


                    Node node2 = wayPointNodes.get(i + 1);
                    int node2MapIdx = nodeIdIdxMap.get(Long.valueOf(node2.valueOf("@ref")));


                    Node node1OsmNode = osmNodeNodes.get(node1MapIdx);
                    Node node2OsmNode = osmNodeNodes.get(node2MapIdx);
                    Double weight = getWeight(
                            node1OsmNode.valueOf("@lat"),
                            node1OsmNode.valueOf("@lon"),
                            node2OsmNode.valueOf("@lat"),
                            node2OsmNode.valueOf("@lon"));


                    //read file :


                    System.out.println(csvBody.get(node1MapIdx).toString());
                    String oldNode1FileEdges = csvBody.get(node1MapIdx)[4];

                    System.out.println(csvBody.get(node2MapIdx).toString());
                    String oldNode2FileEdges = csvBody.get(node2MapIdx)[4];

                    String add1 = " " + node2MapIdx + ":" + weight;
                    String add2 = " " + node1MapIdx + ":" + weight;
                    oldNode1FileEdges += add1;
                    oldNode2FileEdges += add2;

                    csvBody.get(node1MapIdx)[4] = oldNode1FileEdges.trim();
                    csvBody.get(node2MapIdx)[4] = oldNode2FileEdges.trim();


                }
            }
        }
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();


        csvReader.close();


    }


    public Double getWeight(String srcLat, String srcLon, String desLat, String desLon) {

        double startLat = Double.parseDouble(srcLat);
        double startLon = Double.parseDouble(srcLon);

        double endLat = Double.parseDouble(desLat);
        double endLon = Double.parseDouble(desLon);

        return Haversine.distance(startLat, startLon, endLat, endLon);
    }

    public static void main(String[] args) {
        OsmToCSV o = new OsmToCSV();
        try {
            o.setupData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}