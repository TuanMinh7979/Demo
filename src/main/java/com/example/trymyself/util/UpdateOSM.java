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

    private List<InsertData> insertData = new ArrayList<>();

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
                if (nodei.hasChildNodes() && nodei.getChildNodes().getLength() > 3) {
                    System.out.println("___**" + nodei.getAttributes().getNamedItem("id").getTextContent());

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


                        //reuse

                        //data
                        String dataStreetName = String.valueOf(nearestWaypoint.get("name"));
                        ///data

                        //will del
                        if (dataStreetName.isEmpty()) {
                            dataStreetName = "nullname";
                        }
                        sb.append(addPointNodeId + ":" + dataNbNodeId + ":" + dataStreetName + "\n");
                        //will del

                        JSONArray location = (JSONArray) nearestWaypoint.get("location");
                        //data
                        String datalong = String.valueOf(location.get(0));
                        String datalat = String.valueOf(location.get(1));
                        ///data
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

            for (InsertData pairI : insertData) {

                Long nbNodeId = pairI.getNbNodeId();
                Long addPointNodeId = pairI.getAddPointNodeId();
                String streetName = pairI.getStreetName();

                Long wayId = getWayId(nbNodeId, streetName);
                Integer wayIdx = wayIdIdxMap.get(wayId);

                Node wayNode = osmWayNodes.get(wayIdx);
                NodeList wayChildNodeList = wayNode.getChildNodes();


                List<Node> ndNodeList = new ArrayList<>();


                int s = 0;

                List<Double> wayLonList = new ArrayList<>();
                for (int k = 0; k < wayChildNodeList.getLength(); k++) {
                    Node curWayChildNode = wayChildNodeList.item(k);
                    if (curWayChildNode.getNodeName().equals("nd")) {
                        ndNodeList.add(curWayChildNode);
                        Long curWayChildNodeId = Long.parseLong(curWayChildNode.getAttributes().getNamedItem("ref").getTextContent());
                        wayLonList.add(Double.parseDouble(osmPointNodes.get(pointIdIdxMap.get(curWayChildNodeId)).getAttributes().getNamedItem("lon").getTextContent()));
                    }
                }


                System.out.println("wayid la :" + wayId);

                Double firstLong = wayLonList.get(0);
                Double secondLong = wayLonList.get(1);

                Node addPointNode = osmPointNodes.get(pointIdIdxMap.get(addPointNodeId));
                Double addPointNodeLong = Double.parseDouble(addPointNode.getAttributes().getNamedItem("lon").getTextContent());

                Element nd = doc.createElement("nd");
                nd.setAttribute("ref", String.valueOf(addPointNodeId));
                nd.setAttribute("lon", String.valueOf(addPointNodeLong));
                wayLonList.add(addPointNodeLong);

                if (firstLong > secondLong) {
                    //decrease
                    Collections.sort(wayLonList, Comparator.reverseOrder());

                } else {
                    //increase
                    Collections.sort(wayLonList);

                }
                int addedEleIdx = wayLonList.indexOf(addPointNodeLong);
                Node posNode = ndNodeList.get(addedEleIdx);
                wayNode.insertBefore(nd, posNode);
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
                String strName = dataiarr[2];
                insertData.add(new InsertData(Long.parseLong(id1), Long.parseLong(id2), strName));
            }
            myReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getWayId(Long nbNodeId, String streetName) {
        String urlStr = "https://api.openstreetmap.org/api/0.6/node/" + nbNodeId + "/ways";
        URL url = null;
        try {
            url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document docWay = builder.parse(conn.getInputStream());

            NodeList osmWayNodes = docWay.getElementsByTagName("way");
            String wayIdStr = "";

            int ci = 0;
            for (int i = 0; i < osmWayNodes.getLength(); i++) {
                Node curWayNode = osmWayNodes.item(i);
                Node lastTagNode = curWayNode.getChildNodes().item(curWayNode.getChildNodes().getLength() - 2);
                ci++;
                if (!streetName.equals("nullname") && lastTagNode.getAttributes().getNamedItem("k").getTextContent().equals("name") && lastTagNode.getAttributes().getNamedItem("v").getTextContent().equals(streetName)) {
                    wayIdStr = curWayNode.getAttributes().getNamedItem("id").getTextContent();
                    return Long.parseLong(wayIdStr);
                }
                if (streetName.equals("nullname") && !lastTagNode.getAttributes().getNamedItem("k").equals("name")) {
                    wayIdStr = curWayNode.getAttributes().getNamedItem("id").getTextContent();
                    return Long.parseLong(wayIdStr);
                }

                if (ci == osmWayNodes.getLength() - 1) {
                    throw new RuntimeException("OTHER CASE OCCUR.......");
                }

            }


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
        obj.setupPlaceLocation();

//        obj.setupResource();

//        obj.addInOrderWay();

    }
}
