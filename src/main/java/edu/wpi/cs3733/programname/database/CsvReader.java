package edu.wpi.cs3733.programname.database;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import edu.wpi.cs3733.programname.commondata.*;


public class CsvReader {

    /**
     * empty constructor for CsvReader
     */
    public CsvReader() {
    }


    // NODES MapDnodes.csv

    /**
     * @param conn
     * @return
     */

//    public ArrayList<NodeData> getListOfNodes(Connection conn) {
//        ArrayList<NodeData> nodeList = new ArrayList<NodeData>();
//        String[] csvNodes = {"A", "B", "C", "D", "E", "F","G", "H", "I", "W"};
//
//        try {
//            for (String letter: csvNodes) {
//                File csv = new File("Map" + letter + "Nodes.csv");
//                Scanner inputStream = new Scanner(csv);
//
//                // Ignores first line in csv file i.e. header row
//                inputStream.nextLine();
//
//                // Reads all lines in the file
//                while (inputStream.hasNextLine()) {
//                    // Reads current row and converts to a string
//                    String data = inputStream.nextLine();
//
//                    // Seperates the string into fields and stores into an array
//                    String[] values = data.split(",");
//
//                    // Converts int fields from strings to integers
//                    int x = Integer.parseInt(values[1]);
//                    int y = Integer.parseInt(values[2]);
//                    Coordinate location = new Coordinate(x, y);
//                    NodeData nodeObject = new NodeData(values[0], location, values[3], values[4], values[5], values[6], values[7], values[8]);
//                    nodeList.add(nodeObject);
//
//
//                } // end while
//
//
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        }
//        return nodeList;
//    }// end readNodes



// READS ALL TEAM FILES AND ALL FILES
//    public ArrayList<NodeData> getListOfNodes(Connection conn) {
//        ArrayList<NodeData> nodeList = new ArrayList<NodeData>();
//
//
//        try {
//            File File = new File("CsvNodes/AllMapNodes.csv");
//
//            Scanner inputStream = new Scanner(File);
//
//            // Ignores first line in csv file i.e. header row
//            inputStream.nextLine();
//
//            // Reads all lines in the file
//            while (inputStream.hasNextLine()) {
//                // Reads current row and converts to a string
//                String data = inputStream.nextLine();
//
//                // Seperates the string into fields and stores into an array
//                String[] values = data.split(",");
//
//                // Converts int fields from strings to integers
//                int x = Integer.parseInt(values[1]);
//                int y = Integer.parseInt(values[2]);
//                Coordinate location = new Coordinate(x, y);
//                NodeData nodeObject = new NodeData(values[0], location, values[3], values[4], values[5], values[6], values[7], values[8]);
//                nodeList.add(nodeObject);
//
//
//            } // end while
//
//
//
//
//        } catch (FileNotFoundException e) {
//            try {
//                File[] csvNodes = new File("CsvNodes/").listFiles();
//                for (File csv : csvNodes) {
//                    Scanner inputStream = new Scanner(csv);
//
//                    // Ignores first line in csv file i.e. header row
//                    inputStream.nextLine();
//
//                    // Reads all lines in the file
//                    while (inputStream.hasNextLine()) {
//                        // Reads current row and converts to a string
//                        String data = inputStream.nextLine();
//
//                        // Seperates the string into fields and stores into an array
//                        String[] values = data.split(",");
//
//                        // Converts int fields from strings to integers
//                        int x = Integer.parseInt(values[1]);
//                        int y = Integer.parseInt(values[2]);
//                        Coordinate location = new Coordinate(x, y);
//                        NodeData nodeObject = new NodeData(values[0], location, values[3], values[4], values[5], values[6], values[7], values[8]);
//                        nodeList.add(nodeObject);
//
//
//                    } // end while
//
//                }
//
//            } catch (FileNotFoundException w) {
//                w.printStackTrace();
//            }
//
//        }
//        return nodeList;
//    }// end readNodes





    public ArrayList<NodeData> getListOfNodes(Connection conn) throws IOException{
        ArrayList<NodeData> nodeList = new ArrayList<NodeData>();

        try {
            InputStream in = new FileInputStream(new File("csv/CsvNodes/AllMapNodes.csv").getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            reader.readLine();
            // Reads all lines in the file
            while ((line = reader.readLine()) != null) {
                // Reads current row and converts to a string


                // Seperates the string into fields and stores into an array
                String[] values = line.split(",");

                // Converts int fields from strings to integers
                int x = Integer.parseInt(values[1]);
                int y = Integer.parseInt(values[2]);
                Coordinate location = new Coordinate(x, y);
                NodeData nodeObject = new NodeData(values[0], location, values[3], values[4], values[5], values[6], values[7], values[8]);
                nodeList.add(nodeObject);


            } // end while

        }
        catch (IOException e) {


//            String[] csvNodes = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "W"};
//
//            try {
//                for (String letter : csvNodes) {
//                    InputStream csv = new FileInputStream(new File(this.getClass().getClassLoader().getResource("csv/CsvNodes/Map" + letter + "nodes.csv").getPath()));
//                    Scanner inputStream = new Scanner(csv);
//
//                    // Ignores first line in csv file i.e. header row
//                    inputStream.nextLine();
//
//                    // Reads all lines in the file
//                    while (inputStream.hasNextLine()) {
//                        // Reads current row and converts to a string
//                        String data = inputStream.nextLine();
//
//                        // Seperates the string into fields and stores into an array
//                        String[] values = data.split(",");
//
//                        // Converts int fields from strings to integers
//                        int x = Integer.parseInt(values[1]);
//                        int y = Integer.parseInt(values[2]);
//                        Coordinate location = new Coordinate(x, y);
//                        NodeData nodeObject = new NodeData(values[0], location, values[3], values[4], values[5], values[6], values[7], values[8]);
//                        nodeList.add(nodeObject);
//
//
//                    } // end while
//
//
//                }
//
//            } catch (FileNotFoundException w) {
//                w.printStackTrace();
//
//            }

        }
        return nodeList;
    }// end readNodes







//    public ArrayList<NodeData> getListOfNodes(Connection conn) {
//        ArrayList<NodeData> nodeList = new ArrayList<NodeData>();
//
//        try {
//
//                File csv = new File("AllMapNodes.csv");
//                Scanner inputStream = new Scanner(csv);
//
//                // Ignores first line in csv file i.e. header row
//                inputStream.nextLine();
//
//                // Reads all lines in the file
//                while (inputStream.hasNextLine()) {
//                    // Reads current row and converts to a string
//                    String data = inputStream.nextLine();
//
//                    // Seperates the string into fields and stores into an array
//                    String[] values = data.split(",");
//
//                    // Converts int fields from strings to integers
//                    int x = Integer.parseInt(values[1]);
//                    int y = Integer.parseInt(values[2]);
//                    Coordinate location = new Coordinate(x, y);
//                    NodeData nodeObject = new NodeData(values[0], location, values[3], values[4], values[5], values[6], values[7], values[8]);
//                    nodeList.add(nodeObject);
//
//
//                } // end while
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        }
//        return nodeList;
//    }// end readNodes


    // Insert Nodes into DB
    public void insertNodes(Connection conn, ArrayList<NodeData> nodesList) {
        try {
            int i;
            int count = nodesList.size();
            PreparedStatement pst = conn.prepareStatement("INSERT INTO Nodes(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName, teamAssigned)" +
                    "VALUES (?,?,?,?,?,?,?,?,?)");

            int j = 0;

            for (i = 0; i < count; i++) {
                pst.setString(1, nodesList.get(i).getNodeID());
                pst.setInt(2, nodesList.get(i).getXCoord());
                pst.setInt(3, nodesList.get(i).getYCoord());
                pst.setString(4, nodesList.get(i).getFloor());
                pst.setString(5, nodesList.get(i).getBuilding());
                pst.setString(6, nodesList.get(i).getNodeType());
                pst.setString(7, nodesList.get(i).getLongName());
                pst.setString(8, nodesList.get(i).getShortName());
                pst.setString(9, nodesList.get(i).getTeamAssigned());
                pst.executeUpdate();
                j++;

            }

            System.out.println("Number of node rows: " + j);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // end insertNodes


//    // EDGES
//    public ArrayList<EdgeData> getListOfEdges(Connection conn) {
//        // ArrayLists stores data values is proper columns
//        ArrayList<EdgeData> edgeList = new ArrayList<EdgeData>();
//        String[] csvEdges = {"A", "B", "C", "D", "E", "F","G", "H", "I", "W"};
//
//        try {
//                for (String letter: csvEdges) {
//                    File csv = new File("Map" + letter + "edges.csv");
//                    Scanner inputStream = new Scanner(csv);
//
//                    // Ignores first line in csv file i.e. header row
//                    inputStream.nextLine();
//
//                    // Reads all lines in the file
//                    while (inputStream.hasNextLine()) {
//                        // Reads current row and converts to a string
//                        String data = inputStream.nextLine();
//
//                        // Seperates the string into fields and stores into an array
//                        String[] values = data.split(",");
//
//                        EdgeData edgeObject = new EdgeData(values[0], values[1], values[2]);
//                        edgeList.add(edgeObject);
//
//                    } // end while
//
//                }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        }
//        return edgeList;
//    } // end readEdges

    // EDGES
    public ArrayList<EdgeData> getListOfEdges(Connection conn){
        // ArrayLists stores data values is proper columns
        ArrayList<EdgeData> edgeList = new ArrayList<EdgeData>();


        try {
            InputStream in = new FileInputStream(new File("csv/CsvEdges/AllMapEdges.csv").getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            reader.readLine();
            // Reads all lines in the file
            while ((line = reader.readLine()) != null) {
                // Reads current row and converts to a string


                // Seperates the string into fields and stores into an array
                String[] values = line.split(",");

                EdgeData edgeObject = new EdgeData(values[0], values[1], values[2]);
                edgeList.add(edgeObject);

            } // end while


        }
        catch (IOException e) {
//            String[] csvEdges = {"A", "B", "C", "D", "E", "F","G", "H", "I", "W"};
//
//            try {
//                    for (String letter: csvEdges) {
//                        InputStream csv = new FileInputStream(new File("/csv/CsvEdges/" + "Map" + letter + "edges.csv"));
//                        Scanner inputStream = new Scanner(csv);
//
//                        // Ignores first line in csv file i.e. header row
//                        inputStream.nextLine();
//
//                        // Reads all lines in the file
//                        while (inputStream.hasNextLine()) {
//                            // Reads current row and converts to a string
//                            String data = inputStream.nextLine();
//
//                            // Seperates the string into fields and stores into an array
//                            String[] values = data.split(",");
//
//                            EdgeData edgeObject = new EdgeData(values[0], values[1], values[2]);
//                            edgeList.add(edgeObject);
//
//                        } // end while
//
//                    }
//
//            } catch (FileNotFoundException w) {
//                w.printStackTrace();
//
//            }


        }
        return edgeList;
    } // end readEdges



//    // EDGES
//    public ArrayList<EdgeData> getListOfEdges(Connection conn) {
//        // ArrayLists stores data values is proper columns
//        ArrayList<EdgeData> edgeList = new ArrayList<EdgeData>();
//
//        try {
//                File csv = new File("AllMapEdges.csv");
//                Scanner inputStream = new Scanner(csv);
//
//                // Ignores first line in csv file i.e. header row
//                inputStream.nextLine();
//
//                // Reads all lines in the file
//                while (inputStream.hasNextLine()) {
//                    // Reads current row and converts to a string
//                    String data = inputStream.nextLine();
//
//                    // Seperates the string into fields and stores into an array
//                    String[] values = data.split(",");
//
//                    EdgeData edgeObject = new EdgeData(values[0], values[1], values[2]);
//                    edgeList.add(edgeObject);
//
//                } // end while
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        }
//        return edgeList;
//    } // end readEdges

    public void insertEdges(Connection conn, ArrayList<EdgeData> edgesList) {
        // edgeID is unique and used to count the number of lines read in the file minus the header
        int i;
        int count = edgesList.size();

        try {
            // SQL Insert
            PreparedStatement pst = conn.prepareStatement("INSERT INTO Edges(edgeID, startNode, endNode)" +
                    "VALUES (?,?,?)");

            // Loops and increments to insert all data from the file into the edges table
            for (i = 0; i < count; i++) {
                pst.setString(1, edgesList.get(i).getEdgeID());
                pst.setString(2, edgesList.get(i).getStartNode());
                pst.setString(3, edgesList.get(i).getEndNode());
                pst.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // end insertNodes




    // EMPLOYEES
    public ArrayList<Employee> getListOfEmployees(Connection conn) throws IOException{

        ArrayList<Employee> employeeList = new ArrayList<Employee>();


        InputStream in = new FileInputStream(new File("csv/CsvTables/Employees.csv").getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        reader.readLine();
        // Reads all lines in the file
        while ((line = reader.readLine()) != null) {
            // Reads current row and converts to a string


            // Seperates the string into fields and stores into an array
            String[] values = line.split(",");

                boolean sysAdmin;
                if(Integer.parseInt(values[5]) == 1) {
                    sysAdmin = true;
                }
                else {
                    sysAdmin = false;
                }

                Employee employeeObject = new Employee(values[0], values[1], values[2], values[3], values[4], sysAdmin, values[6], values[7]);
                employeeList.add(employeeObject);

            } // end while
        return employeeList;
    }




    public void insertEmployees(Connection conn, ArrayList<Employee> employeeList) {

        int i;
        int count = employeeList.size();

        try {
            // SQL Insert
            PreparedStatement pst = conn.prepareStatement("INSERT INTO Employees(username, password, firstName, middleName, lastName, sysAdmin, serviceType, email)" +
                    "VALUES (?,?,?,?,?,?,?,?)");


            for (i = 0; i < count; i++) {
                pst.setString(1, employeeList.get(i).getUsername());
                pst.setString(2, employeeList.get(i).getPassword());
                pst.setString(3, employeeList.get(i).getFirstName());
                pst.setString(4, employeeList.get(i).getMiddleName());
                pst.setString(5, employeeList.get(i).getLastName());
                pst.setBoolean(6, employeeList.get(i).getSysAdmin());
                pst.setString(7, employeeList.get(i).getServiceType());
                pst.setString(8, employeeList.get(i).getEmail());
                pst.executeUpdate();
                System.out.println(employeeList.get(i).getUsername() + " added*******");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






    // SERVICEREQUESTS
    public ArrayList<ServiceRequest> getListOfServiceRequests(Connection conn) throws IOException{

        ArrayList<ServiceRequest> srList = new ArrayList<ServiceRequest>();


        InputStream in = new FileInputStream(new File("csv/CsvTables/AllServiceRequests.csv").getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        reader.readLine();
        // Reads all lines in the file
        while ((line = reader.readLine()) != null) {
            // Reads current row and converts to a string


            // Seperates the string into fields and stores into an array
            String[] values = line.split(",");

                int serviceID = Integer.parseInt(values[0]);

                ServiceRequest srObject = new ServiceRequest(serviceID, values[1], values[2], values[3], values[4], values[5]);
                srList.add(srObject);

            } // end while
        return srList;
    }






    public void insertServiceRequests(Connection conn, ArrayList<ServiceRequest> srList) {

        int i;
        int count = srList.size();

        try {
            // SQL Insert
            PreparedStatement pst = conn.prepareStatement("INSERT INTO ServiceRequests(serviceID, sender, serviceType, location1, location2, description)" +
                    "VALUES (?,?,?,?,?,?)");


            for (i = 0; i < count; i++) {
                pst.setInt(1, srList.get(i).getServiceID());
                pst.setString(2, srList.get(i).getSender());
                pst.setString(3, srList.get(i).getServiceType());
                pst.setString(4, srList.get(i).getLocation1());
                pst.setString(5, srList.get(i).getLocation2());
                pst.setString(6, srList.get(i).getDescription());
                pst.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}