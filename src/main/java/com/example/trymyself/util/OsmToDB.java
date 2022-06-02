package com.example.trymyself.util;

import com.example.trymyself.entities.Edge;
import com.example.trymyself.entities.NodeEntity;
import com.example.trymyself.entities.Way;
import com.example.trymyself.repo.NodeEntityRepo;
import com.example.trymyself.repo.WayRepo;
import lombok.NoArgsConstructor;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Component

public class OsmToDB {
    //    private MultipartFile osmFile;
    @Autowired
    private WayRepo wayRepo;

    @Autowired
    private NodeEntityRepo nodeEntityRepo;

    public OsmToDB() {
    }

    public void setupData() {
        try {

            SAXReader reader = new SAXReader();
            Document document = reader.read(new File("D:\\COCCOC\\CanThoCenter.osm"));

            Element root = document.getRootElement();
            System.out.println("_______________________");
            System.out.println("Root element :" + root.getName());

            List<Node> nodeEntityNodes = document.selectNodes("//node");
            System.out.println("nodes sz: " + nodeEntityNodes.size());
            setupNodeEntites(nodeEntityNodes);

            List<Node> ways = document.selectNodes("//way");
            System.out.println("way size " + ways.size());
            setupWays(ways);


        } catch (DocumentException e) {
            e.printStackTrace();
        }//End try catch

    }


    public void setupNodeEntites(List<Node> nodeEntityNodes) {
        for (Node nodeEntityNodei : nodeEntityNodes) {
            NodeEntity newNodei = new NodeEntity();
            newNodei.setOsmId(Long.parseLong(nodeEntityNodei.valueOf("@ref")));
            newNodei.setLat(nodeEntityNodei.valueOf("@lat"));
            newNodei.setLon(nodeEntityNodei.valueOf("@lon"));
            nodeEntityRepo.save(newNodei);
        }

    }

    public void setupWays() {
        List<NodeEntity> nodeEntities = nodeEntityRepo.findAll();
        for (NodeEntity nodeEntityi : nodeEntities) {

        }

    }


    public void setupWays(List<Node> wayNodes) {
        for (Node wayNodei : wayNodes) {

            long osmId = Long.parseLong(wayNodei.valueOf("@id"));
            Way newWay = new Way(osmId);
            newWay = wayRepo.save(newWay);

            if (wayNodei.hasContent()) {

                List<Node> nds = wayNodei.selectNodes("nd");

                Node curNode = null;
                long osmNodeEntityId = 1L;
                NodeEntity oldNodeEnity = null;

                curNode = nds.get(0);
                osmNodeEntityId = Long.parseLong(curNode.valueOf("@ref"));
                oldNodeEnity = nodeEntityRepo.findById(osmNodeEntityId).get();
                oldNodeEnity.setWay(newWay);
                nodeEntityRepo.save(oldNodeEnity);

                newWay.setSrcNodeOsmId(oldNodeEnity.getOsmId());

                for (int i = 1; i < nds.size() - 1; i++) {
//                    System.out.println(ndi.valueOf("@ref"));
                    curNode = nds.get(i);
                    osmNodeEntityId = Long.parseLong(curNode.valueOf("@ref"));
                    oldNodeEnity = nodeEntityRepo.findById(osmNodeEntityId).get();
                    oldNodeEnity.setWay(newWay);
                    nodeEntityRepo.save(oldNodeEnity);
                }

                curNode = nds.get(nds.size() - 1);
                osmNodeEntityId = Long.parseLong(curNode.valueOf("@ref"));
                oldNodeEnity = nodeEntityRepo.findById(osmNodeEntityId).get();
                oldNodeEnity.setWay(newWay);
                nodeEntityRepo.save(oldNodeEnity);

                newWay.setDesNodeOsmId(oldNodeEnity.getOsmId());


            }

            wayRepo.save(newWay);
        }


    }

    public void setupEdge(Way way, NodeEntity srcN, NodeEntity desN  ) {
        Edge newEdge = new Edge();
        newEdge.setSrcNodeEntity(srcN);
        newEdge.setDesNodeEntity();
    }


    public static void main(String[] args) {
        OsmToDB obj = new OsmToDB();
        obj.setupData();
    }


}
