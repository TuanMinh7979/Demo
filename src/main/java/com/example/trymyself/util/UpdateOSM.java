package com.example.trymyself.util;


import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.*;
import java.util.*;

public class UpdateOSM {
    private List<Node> osmPointNodes = new ArrayList<>();
    private Map<Long, Integer> pointIdIdxMap = new HashMap<>();

    private List<Node> osmWayNodes = new ArrayList<>();
    private Map<Long, Integer> wayIdIdxMap = new HashMap<>();

    private List<AddAndNbNodeId> addAndNbNodeIds = new ArrayList<>();

    private DocumentBuilderFactory dbf = null;
    private DocumentBuilder db = null;
    private Document doc = null;

    public void setupResource() {
        try {
            dbf = DocumentBuilderFactory.newInstance();

            InputStream is = new FileInputStream("D:\\COCCOC\\smtvkoriRs.xml");

            db = dbf.newDocumentBuilder();

            doc = db.parse(is);


            NodeList osmPointNodeList = doc.getElementsByTagName("node");

            for (int i = 0; i < osmPointNodeList.getLength(); i++) {
                Node curPointNode = osmPointNodeList.item(i);

                this.osmPointNodes.add(curPointNode);
                Long curWayNodeId = Long.parseLong(curPointNode.getAttributes().getNamedItem("id").getTextContent());
                this.pointIdIdxMap.put(curWayNodeId, i);
            }


            NodeList osmWayNodeList = doc.getElementsByTagName("way");

            for (int i = 0; i < osmWayNodeList.getLength(); i++) {
                Node curWayNode = osmWayNodeList.item(i);

                this.osmWayNodes.add(curWayNode);
                Long curWayNodeId = Long.parseLong(curWayNode.getAttributes().getNamedItem("id").getTextContent());
                this.wayIdIdxMap.put(curWayNodeId, i);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void setupPlaceLocation() {
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            InputStream is = new FileInputStream("D:\\COCCOC\\smtvkori.xml");

            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(is);


            NodeList osmPointNodeList = doc.getElementsByTagName("node");


            //reuse
            //will del
            StringBuilder sb = new StringBuilder();
            //will del
            //reuse
            for (int i = 0; i < 100; i++) {
                Node nodei = osmPointNodeList.item(i);
                if (nodei.hasChildNodes()) {
                    String oldlat = nodei.getAttributes().getNamedItem("lat").getTextContent();
                    String oldlong = nodei.getAttributes().getNamedItem("lon").getTextContent();
                    String urlStr = "http://router.project-osrm.org/nearest/v1/driving/" + oldlong + "," + oldlat + "?number=1";
                    URL url = null;
                    try {
                        url = new URL(urlStr);
                        System.out.println(url);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        StringBuilder jsonStrBuilder = new StringBuilder();
                        if (conn.getResponseCode() == 200) {
                            Scanner scan = new Scanner(url.openStream());
                            while (scan.hasNext()) {
                                String temp = scan.nextLine();
                                jsonStrBuilder.append(temp);
                            }
                        }

                        String jsonStr = jsonStrBuilder.toString();

                        // GET DATA
                        JSONObject rsJsonObj = new JSONObject(jsonStr);

                        JSONArray waypoints = (JSONArray) rsJsonObj.get("waypoints");
                        JSONObject nearestWaypoint = (JSONObject) waypoints.get(0);

                        JSONArray nwNodes = (JSONArray) nearestWaypoint.get("nodes");

                        //data
                        Long dataNbNodeId = null;
                        ///data
                        for (int j = 0; j < nwNodes.length(); j++) {
                            Long curNbNodeId = Long.valueOf(String.valueOf(nwNodes.get(j)));
                            if (curNbNodeId > 0) {
                                dataNbNodeId = curNbNodeId;
                                break;
                            }
                        }
                        //reuse
                        Long addPointNodeId = Long.parseLong(nodei.getAttributes().getNamedItem("id").getTextContent());
//                        addAndNbNodeIds.add(new AddAndNbNodeId(addPointNodeId, dataNbNodeId));
                        //will del
                        sb.append(addPointNodeId + ":" + dataNbNodeId + "\n");
                        //will del

                        //reuse

                        //data
                        String dataStreetName = String.valueOf(nearestWaypoint.get("name"));
                        ///data
                        JSONArray location = (JSONArray) nearestWaypoint.get("location");
                        //data
                        String datalong = String.valueOf(location.get(0));
                        String datalat = String.valueOf(location.get(1));
                        ///data

                        System.out.println("***" + dataNbNodeId + " " + "streetname: " + dataStreetName + " " + datalong + " " + datalat);

                        //GET DATA


                        //MODIFY LOCATION

                        nodei.getAttributes().getNamedItem("lat").setTextContent(datalat);
                        nodei.getAttributes().getNamedItem("lon").setTextContent(datalong);

                        //MODIFY LOCATION


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                }


            }
            //will del
            writeToFile(sb.toString());
            //will del

            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.STANDALONE, "no");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult("D:\\COCCOC\\smtvkoriRs.xml");

            tf.transform(source, result);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void addInOrderWay() {

        readFromFile();
//        addAndNbNodeIds is full after above statement


        try {
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

//            InputStream is = new FileInputStream("D:\\COCCOC\\smtvkoriRs.xml");

//            DocumentBuilder db = dbf.newDocumentBuilder();

//            Document doc1 = db.parse(is);


            //ADD IN ORDERING WAY NODE
            Map<Long, InsertedNumPair> wayInsertedNumMap = new HashMap<>();
            for (AddAndNbNodeId pairI : addAndNbNodeIds) {

                Long nbNodeId = pairI.getNbNodeId();
                Long addPointNodeId = pairI.getAddPointNodeId();


                Long wayId = getWayId(nbNodeId);

                System.out.println("WAY ID HERE: " + wayId);
                Integer wayIdx = wayIdIdxMap.get(wayId);

                Node wayNode = osmWayNodes.get(wayIdx);
                NodeList wayChildNodeList = wayNode.getChildNodes();


                List<Node> ndNodeList = new ArrayList<>();
                for (int k = 0; k < wayChildNodeList.getLength(); k++) {
                    Node curWayChildNode = wayChildNodeList.item(k);
                    if (curWayChildNode.getNodeName().equals("nd")) {
                        ndNodeList.add(curWayChildNode);
                    }
                }
                //

                //
                for (int h = 0; h < ndNodeList.size(); h++) {
                    Node curNdNode = ndNodeList.get(h);
                    Long curNdNodeId = Long.parseLong(curNdNode.getAttributes().getNamedItem("ref").getTextContent());


                    if (curNdNodeId.equals(nbNodeId)) {
                        int adjNbNdNodeIdx = h > 0 ? h - 1 : -1;
                        if (adjNbNdNodeIdx != -1) {
                            Node adjNdNode = ndNodeList.get(adjNbNdNodeIdx);
                            Long adjNdNodeId = Long.parseLong(adjNdNode.getAttributes().getNamedItem("ref").getTextContent());

                            Node nbPointNode = osmPointNodes.get(pointIdIdxMap.get(curNdNodeId));
                            Node adjNbPointNode = osmPointNodes.get(pointIdIdxMap.get(adjNdNodeId));

                            Double nbPointNodeLong = Double.parseDouble(nbPointNode.getAttributes().getNamedItem("lon").getTextContent());
                            Double adjNbPointNodeLong = Double.parseDouble(adjNbPointNode.getAttributes().getNamedItem("lon").getTextContent());

                            Node addPointNode = osmPointNodes.get(pointIdIdxMap.get(addPointNodeId));
                            Double addPointNodeLong = Double.parseDouble(addPointNode.getAttributes().getNamedItem("lon").getTextContent());
                            if (adjNbPointNodeLong > nbPointNodeLong) {
                                //decrease

                                if (addPointNodeLong > nbPointNodeLong) {
                                    //dat tren
                                    System.out.println("Giam dan -Dat tren");
                                } else {
                                    //dat duoi
                                    System.out.println("Giam dan-Dat duoi");


                                    Element nd = doc.createElement("nd");

                                    nd.setAttribute("ref", String.valueOf(addPointNodeId));


                                    Node posNode = ndNodeList.get(h + 1);
                                    wayNode.insertBefore(nd, posNode);

                                    //
                                    if (wayInsertedNumMap.get(wayId) != null) {
                                        wayInsertedNumMap.put(wayId,
                                                wayInsertedNumMap.get(wayId).inBottomInsNum());
                                    } else {
                                        wayInsertedNumMap.put(wayId,
                                                new InsertedNumPair(0, 1));
                                    }
                                    //

                                }


                            } else {
                                //increase
                                if (addPointNodeLong < nbPointNodeLong) {
                                    //dat tren
                                    System.out.println("Tang dan -Dat tren");
                                } else {
                                    //dat duoi
                                    System.out.println("Tang dan -Dat duoi");
                                }

                            }
                            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                        } else {

                            System.out.println("out of index nhe");
                        }

                    }
                }
            }


            Transformer tf1 = TransformerFactory.newInstance().newTransformer();
            tf1.setOutputProperty(OutputKeys.INDENT, "yes");
            tf1.setOutputProperty(OutputKeys.STANDALONE, "no");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult("D:\\COCCOC\\smtvkoriRs123.xml");

            tf1.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }


        //ADD IN ORDERING WAY NODE
    }

    public void writeToFile(String data) throws IOException {
        File output = new File("D:\\COCCOC\\smtvkoriRsPair.xml");
        FileWriter writer = new FileWriter(output);

        writer.write(data);
        writer.flush();
        writer.close();
    }

    public void readFromFile() {
        try {
            File myObj = new File("D:\\COCCOC\\smtvkoriRsPair.xml");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String dataarr = myReader.nextLine();
                String[] dataiarr = dataarr.split(":");
                String id1 = dataiarr[0];
                String id2 = dataiarr[1];
                addAndNbNodeIds.add(new AddAndNbNodeId(Long.parseLong(id1), Long.parseLong(id2)));
            }
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getWayId(Long nbNodeId) {
        String urlStr = "https://api.openstreetmap.org/api/0.6/node/" + nbNodeId + "/ways";
        URL url = null;
        try {
            url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document docWay = builder.parse(conn.getInputStream());

            NodeList osmWayNodes = docWay.getElementsByTagName("way");
            String wayIdStr = osmWayNodes.item(0).getAttributes().getNamedItem("id").getTextContent();

            return Long.parseLong(wayIdStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void main(String[] args) throws Exception {
        UpdateOSM obj = new UpdateOSM();
        obj.setupResource();
//        obj.setupPlaceLocation();
        obj.addInOrderWay();

    }
}
