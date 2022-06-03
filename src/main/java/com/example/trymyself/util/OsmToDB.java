package com.example.trymyself.util;

import com.example.trymyself.entities.Edge;
import com.example.trymyself.entities.NodeEntity;
import com.example.trymyself.repo.NodeEntityRepo;
import com.example.trymyself.repo.EdgeRepo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component

public class OsmToDB {
    //    private MultipartFile osmFile;
    @Autowired
    private EdgeRepo edgeRepo;

    @Autowired
    private NodeEntityRepo nodeEntityRepo;


    public void setupData() {
        try {

            SAXReader reader = new SAXReader();
            Document document = reader.read(new File("D:\\COCCOC\\supermini.osm"));

            Element root = document.getRootElement();

            List<Node> nodeEntityNodes = document.selectNodes("//node");
            setupNodeEntites(nodeEntityNodes);

            List<Node> wayNodes = document.selectNodes("//way");
            setupEdges(wayNodes);


        } catch (DocumentException e) {
            e.printStackTrace();
        }//End try catch

    }


    public void setupNodeEntites(List<Node> nodeEntityNodes) {
        for (Node nodeEntityNodei : nodeEntityNodes) {
            NodeEntity newNodei = new NodeEntity();
            newNodei.setId(Long.valueOf(nodeEntityNodei.valueOf("@id")));
            newNodei.setLat(nodeEntityNodei.valueOf("@lat"));
            newNodei.setLon(nodeEntityNodei.valueOf("@lon"));
            nodeEntityRepo.save(newNodei);
        }

    }


    public void setupEdges(List<Node> wayNodes) {
        for (Node wayNodei : wayNodes) {


            if (wayNodei.hasContent()) {

                List<Node> nodeEntityNodes = wayNodei.selectNodes("nd");


                for (int i = 0; i < nodeEntityNodes.size() - 1; i++) {
//                    System.out.println(ndi.valueOf("@ref"));


                    Node node1 = nodeEntityNodes.get(i);
                    NodeEntity nodeEntity1 = nodeEntityRepo.getNodeEntityWithSrcOfEdgeSet(Long.valueOf(node1.valueOf("@ref")));

                    Node node2 = nodeEntityNodes.get(i + 1);
                    NodeEntity nodeEntity2 = nodeEntityRepo.getNodeEntityWithDesOfEdgeSet(Long.valueOf(node2.valueOf("@ref")));

                    Edge savedEdge = setAEdge(nodeEntity1, nodeEntity2);

                    nodeEntity1.addSrcOfEdges(savedEdge);

                    nodeEntity2.addDesOfEdges(savedEdge);
//                    nodeEntity1.getSrcOfEdges().add(savedEdge);
//                    nodeEntity2.getDesOfEdges().add(savedEdge);

                    nodeEntityRepo.save(nodeEntity1);
                    nodeEntityRepo.save(nodeEntity2);
                }
            }

        }


    }

    public Edge setAEdge(NodeEntity srcN, NodeEntity desN) {
        Edge newEdge = new Edge();
        newEdge.setSrcNodeEntity(srcN);
        newEdge.setDesNodeEntity(desN);
        double startLat = Double.parseDouble(srcN.getLat());
        double startLon = Double.parseDouble(srcN.getLon());

        double endLat = Double.parseDouble(desN.getLat());
        double endLon = Double.parseDouble(desN.getLon());

        newEdge.setDistance(Haversine.distance(startLat, startLon, endLat, endLon));

        return edgeRepo.save(newEdge);

    }


}
