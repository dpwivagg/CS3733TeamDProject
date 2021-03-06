package edu.wpi.cs3733.programname.database.ModificationMethods;

import edu.wpi.cs3733.programname.ManageController;
import edu.wpi.cs3733.programname.commondata.NodeData;
import edu.wpi.cs3733.programname.database.CsvWriter;
import edu.wpi.cs3733.programname.database.DBConnection;
import java.sql.SQLException;

public class NodesMethod {

    private DBConnection dbConnection ;
    private CsvWriter wrt;
    public NodesMethod(DBConnection dbConnection){
        this.dbConnection = dbConnection;
        this.wrt = new CsvWriter();}

    public void insertNode(NodeData data) {
        String nodeID = data.getNodeID();
        int xcoord = data.getXCoord();
        int ycoord = data.getYCoord();
        String floor = data.getFloor();
        String building = data.getBuilding();
        String nodeType = data.getNodeType();
        String longName = data.getLongName();
        String shortName = data.getShortName();
        String teamAssigned = data.getTeamAssigned();
        String str;

        try {

            str = "INSERT INTO Nodes VALUES('" + nodeID + "', " + xcoord + ", " + ycoord + ", '" + floor + "', '" +
                    building + "', '" + nodeType + "', '" + longName + "', '" + shortName + "', '" + teamAssigned + "')";

            System.out.println(str);
            dbConnection.executeUpdate(str);
            this.wrt.writeNodes(dbConnection.getConnection());
        } catch (SQLException e) {
            System.out.println("Insert Node Failed!");
            e.printStackTrace();
        }
    }


    public void modifyNode(NodeData data) {
        String nodeID = data.getNodeID();
        int xcoord = data.getXCoord();
        int ycoord = data.getYCoord();
        String floor = data.getFloor();
        String building = data.getBuilding();
        String nodeType = data.getNodeType();
        String longName = data.getLongName();
        String shortName = data.getShortName();
        String teamAssigned = data.getTeamAssigned();
        String str;
        try {

            str = "UPDATE Nodes SET xcoord = " + xcoord + ", ycoord = " + ycoord + ", floor = '" + floor +
                    "', building = '" + building + "', nodeType = '" + nodeType + "', longName = '" + longName + "', shortName = '"
                    + shortName + "', teamAssigned = '" + teamAssigned + "' WHERE nodeID = '" + nodeID + "'";

            System.out.println(str);
            dbConnection.executeUpdate(str);
            this.wrt.writeNodes(dbConnection.getConnection());

        } catch (SQLException e) {
            System.out.println("Edit Node Failed!");
            e.printStackTrace();
        }
    }


    public void removeNode(NodeData data) {
        String nodeID = data.getNodeID();
        String str;

        try {
            str = "DELETE FROM Nodes WHERE nodeID = '" + nodeID + "'";

            System.out.println(str);
            dbConnection.executeUpdate(str);
            this.wrt.writeNodes(dbConnection.getConnection());
        } catch (SQLException e) {
            System.out.println("Delete Node Failed!");
            e.printStackTrace();
        }
    }

    public void updateBuilding(String building) {

        try {
            String str = "alter table Nodes " +
                    "ADD CONSTRAINT Nodes_building FOREIGN KEY " + building + "";

            System.out.println(str);
            dbConnection.executeUpdate(str);
            this.wrt.writeNodes(dbConnection.getConnection());
        } catch (SQLException e) {
            System.out.println("adding building constraint failed");
            e.printStackTrace();
        }
    }

}
