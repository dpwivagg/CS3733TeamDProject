import edu.wpi.cs3733.programname.commondata.Coordinate;
import edu.wpi.cs3733.programname.commondata.Edge;
import edu.wpi.cs3733.programname.commondata.NodeData;
import edu.wpi.cs3733.programname.pathfind.entity.NoPathException;
import edu.wpi.cs3733.programname.pathfind.entity.StarNode;
import edu.wpi.cs3733.programname.pathfind.entity.DFS;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DFSTest {

    NodeData node1 = new NodeData("1", new Coordinate(2, 2),"2","Basic", "Lobby One", "L1");
    NodeData node2 = new NodeData("2", new Coordinate(2, 4),"2","Basic", "Hallway One", "H1");
    NodeData node3 = new NodeData("3", new Coordinate(4, 4),"2","Basic", "Hallway Two", "H2");
    NodeData node4 = new NodeData("4", new Coordinate(6, 4),"2", "Basic", "Hallway Three", "H3");
    NodeData node5 = new NodeData("5", new Coordinate(4, 6),"2","Basic", "Connector One", "C1");
    NodeData node6 = new NodeData("6", new Coordinate(6, 6),"2", "Basic", "Room One", "R1");
    NodeData node7 = new NodeData("7", new Coordinate(4, 8),"2", "Basic", "Connector Two", "C2");
    NodeData node8 = new NodeData("8", new Coordinate(6, 8),"2", "Basic", "Hallway Four", "H4");
    NodeData node9 = new NodeData("9", new Coordinate(8, 8),"2","Basic", "Room Two", "R2");

    Edge edge1 = new Edge("1", "2", "E1", null);
    Edge edge2 = new Edge("2", "3", "E2", null);
    Edge edge3 = new Edge("3", "4", "E3", null);
    Edge edge4 = new Edge("3", "5", "E4", null);
    Edge edge5 = new Edge("5", "6", "E5", null);
    Edge edge6 = new Edge("5", "7", "E6", null);
    Edge edge7 = new Edge("7", "8", "E7", null);
    Edge edge8 = new Edge("8", "9", "E8", null);
//    Edge surprise = new Edge("6","8","ES");
    // TODO: add new edges to try to throw the program off, see if we are finding most efficient path, etc.

    LinkedList<NodeData> allNodes = new LinkedList<>(Arrays.asList(node1, node2, node3, node4, node5, node6, node7, node8, node9));
    LinkedList<Edge> allEdges = new LinkedList<>(Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6, edge7, edge8));

    StarNode star1 = new StarNode(node1);
    StarNode star2 = new StarNode(node2);
    StarNode star3 = new StarNode(node3);
    StarNode star4 = new StarNode(node4);
    StarNode star5 = new StarNode(node5);
    StarNode star6 = new StarNode(node6);
    StarNode star7 = new StarNode(node7);
    StarNode star8 = new StarNode(node8);
    StarNode star9 = new StarNode(node9);

    public DFSTest() {}

    @Test
    // This is a simple test. We have nodes 1-4 which are all connected by only one edge each (a straight line of nodes)
    // If we can get from node 1 to node 4, we are on the right track
    public void StraightPath() throws NoPathException {
        DFS Path = new DFS(allNodes, allEdges,"1", "4");
        LinkedList<StarNode> finalOrder = new LinkedList<StarNode>(Arrays.asList(star4, star3, star2, star1));
        List<NodeData> DFSReturn = Path.getFinalList();
        for(int i = 0; i < DFSReturn.size(); i++) Assert.assertEquals(finalOrder.get(i).getId(),
                DFSReturn.get(i).getId());
    }

    @Test
    // We are using nodes 1-4 in a row again, but starting in the middle and trying to get to the far end
    public void IntermedPath() throws NoPathException {
        DFS Path = new DFS(allNodes, allEdges,"3", "1");
        LinkedList<StarNode> finalOrder = new LinkedList<StarNode>(Arrays.asList(star1, star2, star3));
        List<NodeData> DFSReturn = Path.getFinalList();
        for(int i = 0; i < DFSReturn.size(); i++) Assert.assertEquals(finalOrder.get(i).getId(),
                DFSReturn.get(i).getId());
    }

    @Test
    // Let's start at the far end of the tree and try to get to the first node
    public void LongPath() throws NoPathException {
        DFS Path = new DFS(allNodes, allEdges,"8", "1");
        LinkedList<StarNode> finalOrder = new LinkedList<StarNode>(Arrays.asList(star1, star2, star3, star5, star7, star8));
        List<NodeData> DFSReturn = Path.getFinalList();
        for(int i = 0; i < DFSReturn.size(); i++) Assert.assertEquals(finalOrder.get(i).getId(),
                DFSReturn.get(i).getId());
    }

    @Test
    // Trying to travel around the C part of the hallway
    public void CPath() throws NoPathException {
        DFS Path = new DFS(allNodes, allEdges,"6", "4");
        LinkedList<StarNode> finalOrder = new LinkedList<StarNode>(Arrays.asList(star4, star3, star5, star6));
        List<NodeData> DFSReturn = Path.getFinalList();
        for(int i = 0; i < DFSReturn.size(); i++) Assert.assertEquals(finalOrder.get(i).getId(),
                DFSReturn.get(i).getId());
    }

    @Test
    // Can we do a super simple path?
    public void OneStepPath() throws NoPathException {
        DFS Path = new DFS(allNodes, allEdges,"9", "8");
        LinkedList<StarNode> finalOrder = new LinkedList<StarNode>(Arrays.asList(star8, star9));
        List<NodeData> DFSReturn = Path.getFinalList();
        for(int i = 0; i < DFSReturn.size(); i++) Assert.assertEquals(finalOrder.get(i).getId(),
                DFSReturn.get(i).getId());
    }

    @Test
    // Failure case: when we go from one node to itself
    public void ZeroStepPath() throws NoPathException {
        DFS Path = new DFS(allNodes, allEdges,"1", "1");
        LinkedList<StarNode> finalOrder = new LinkedList<StarNode>(Arrays.asList(star1));
        List<NodeData> DFSReturn = Path.getFinalList();
        for(int i = 0; i < DFSReturn.size(); i++) Assert.assertEquals(finalOrder.get(i).getId(),
                DFSReturn.get(i).getId());
    }

    @Test(expected = NoPathException.class)
    // Failure case: the path does not exist (There are no edges leading to that node)
    public void NonexistantPath() throws NoPathException {
        allNodes.add(new NodeData("10", new Coordinate(15, 15),"2", "Disconnected", "Outside", "O"));
        DFS Path = new DFS(allNodes, allEdges, "1", "10");
        LinkedList<StarNode> finalOrder = new LinkedList<StarNode>(Arrays.asList(star1));
        List<NodeData> DFSReturn = Path.getFinalList();
        for(int i = 0; i < DFSReturn.size(); i++) Assert.assertEquals(finalOrder.get(i).getId(),
                DFSReturn.get(i).getId());
    }

    @Test(expected = NullPointerException.class)
    // Failure case: the path does not exist (The node does not exist)
    // TODO: Catch a different exception in the future
    public void NonexistantNode() throws NoPathException {
        DFS Path = new DFS(allNodes, allEdges,"1", "10");
        LinkedList<StarNode> finalOrder = new LinkedList<StarNode>(Arrays.asList(star1));
        List<NodeData> DFSReturn = Path.getFinalList();
        for(int i = 0; i < DFSReturn.size(); i++) Assert.assertEquals(finalOrder.get(i).getId(),
                DFSReturn.get(i).getId());
    }
}
