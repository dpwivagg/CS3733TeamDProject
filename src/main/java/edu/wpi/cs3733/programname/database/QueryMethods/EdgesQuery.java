package edu.wpi.cs3733.programname.database.QueryMethods;

import edu.wpi.cs3733.programname.commondata.EdgeData;
import edu.wpi.cs3733.programname.database.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EdgesQuery {
    private DBConnection dbConnection;
    public EdgesQuery(DBConnection dbConnection){this.dbConnection = dbConnection;}
    public List<EdgeData> getAllEdgeInfo() {
        EdgeData queryResult = null;
        List<EdgeData> allEdges = new ArrayList<EdgeData>();

        String edgeID = "";
        String startNode = "";
        String endNode = "";

        try {
            String sql = "SELECT * FROM Edges";
            Statement stmt = dbConnection.getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sql);

            while(result.next()) {
                edgeID = result.getString("edgeID");
                startNode = result.getString("startNode");
                endNode = result.getString("endNode");

                queryResult = new EdgeData(edgeID, startNode, endNode);
                allEdges.add(queryResult);
            }
        }
        catch (SQLException e) {
            System.out.println("Query Edge Failed!");
            e.printStackTrace();
        }
        return allEdges;
    }



    public EdgeData queryEdgeByID(String eID) {
        EdgeData queryResult = null;

        String edgeID = "";
        String startNode = "";
        String endNode = "";

        try {
            String sql = "SELECT * FROM Edges WHERE edgeID = " + "'" + eID + "'";
            Statement stmt = dbConnection.getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sql);

            while(result.next()) {
                edgeID = result.getString("edgeID");
                startNode = result.getString("startNode");
                endNode = result.getString("endNode");
            }
        }
        catch (SQLException e) {
            System.out.println("Query Edge Failed!");
            e.printStackTrace();
        }
        queryResult = new EdgeData(edgeID, startNode, endNode);
        return queryResult;
    }

    public List<EdgeData> queryEdgeByFloor(String floor){
        EdgeData queryResult = null;
        List<EdgeData> allEdges = new ArrayList<EdgeData>();
        String edgeID = "";
        String startNode = "";
        String endNode = "";

        try {
            String sql = "SELECT * FROM Edges WHERE floor = " + "'" + floor + "'";
            Statement stmt = dbConnection.getConnection().createStatement();
            ResultSet result = stmt.executeQuery(sql);

            while(result.next()) {
                edgeID = result.getString("edgeID");
                startNode = result.getString("startNode");
                endNode = result.getString("endNode");

                queryResult = new EdgeData(edgeID, startNode, endNode);
                allEdges.add(queryResult);
            }
        }
        catch (SQLException e) {
            System.out.println("Query Edge Failed!");
            e.printStackTrace();
        }
        return allEdges;
    }
}
