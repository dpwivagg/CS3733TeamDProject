package edu.wpi.cs3733.programname.boundary;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import edu.wpi.cs3733.programname.ManageController;
import edu.wpi.cs3733.programname.commondata.Coordinate;
import edu.wpi.cs3733.programname.commondata.EdgeData;
import edu.wpi.cs3733.programname.commondata.NodeData;
import edu.wpi.cs3733.programname.pathfind.PathfindingController;
import edu.wpi.cs3733.programname.pathfind.PathfindingController.searchType;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static edu.wpi.cs3733.programname.commondata.Constants.CIRCILE_RADIUS;
import static edu.wpi.cs3733.programname.commondata.HelperFunction.*;
import static edu.wpi.cs3733.programname.commondata.HelperFunction.initNodeListImage;
import static javafx.scene.paint.Color.*;

public class MapAdminController extends UIController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private GridPane gridMapEdit;
    @FXML
    private TextField textNodeId;
    @FXML
    private TextField textNodeFloor;
    @FXML
    private TextField textNodeLocation;
    @FXML
    private TextField textNodeType;
    @FXML
    private TextField textNodeFullName;
    @FXML
    private TextField textNodeShortName;
    @FXML
    private TextField textNodeBuilding;
    @FXML
    private TextField textNodeTeamAssigned;

    @FXML
    private JFXButton btnDeleteNode;
    @FXML
    private JFXButton btnDeleteEdge;
    @FXML
    private JFXButton btnAddNode;
    @FXML
    private JFXButton btnAddEdge;
    @FXML
    private JFXButton btnEditNode;
    @FXML
    private JFXButton btnSubmitNodeEdit;    //TODO add this to UI in scenebuilder

    //FXML objects
    @FXML
    private StackPane drawingStack;
    @FXML
    private ScrollPane paneScroll;
    @FXML
    private Canvas drawingCanvas;
    @FXML
    private ImageView imgMap;
    @FXML
    private AnchorPane panningPane;

    //map switching objects
    @FXML
    private Button btnMapUp;
    @FXML
    private Button btnMapDwn;
    @FXML
    private Label lblCurrentFloor;
    @FXML
    private JFXCheckBox allNodeBox;
    @FXML
    private JFXCheckBox allEdgeBox;
    private int floor = 2;
    private ArrayList<Floor> floors = new ArrayList<>();
    private Floor currentFloor;
    private ArrayList<Building> buildings = new ArrayList<>();

    //zoom and pan objects
    @FXML
    private JFXButton btnZoomIn;
    @FXML
    private JFXButton btnZoomOut;

    @FXML
    private AnchorPane paneControls;
    @FXML
    private JFXHamburger burger;
    @FXML
    private JFXRadioButton DFS;
    @FXML
    private JFXRadioButton BFS;
    @FXML
    private JFXRadioButton Dijkstra;
    @FXML
    private JFXRadioButton ASTAR;
    @FXML
    private JFXRadioButton BestFirst;
    @FXML
    private JFXRadioButton Beam;
    @FXML
    private ToggleGroup pathfinding;


    //floor and building shit
    @FXML
    private JFXComboBox comboBuilding;
    @FXML
    private JFXComboBox comboFloors;
    @FXML
    private JFXButton btnFloorAdd;
    //Node Info Pane
    @FXML
    private AnchorPane nodeInfoPane;
    @FXML
    private JFXButton nodeInfoSetLocation;
    @FXML
    private JFXButton nodeInfoAdd;
    @FXML
    private JFXButton nodeInfoEdit;
    @FXML
    private JFXButton nodeInfoDelete;
    @FXML
    private JFXButton nodeInfoX;

    //button to return to main
    @FXML
    JFXButton returnMain;

    @FXML
    private AnchorPane editEdgePane;
    @FXML
    private JFXButton confirmEditEdge;
    @FXML
    private JFXButton cancleEditEdge;
    private TestingController mTestController;
    ManageController manager;
    private List<Shape> drawings = new ArrayList<>();
    private String nodeAction = "";
    private String edgeAction = "";
    private NodeData selectEdgeN1 = null;
    private NodeData selectEdgeN2 = null;

    private int prevClickX;
    private int prevClickY;
    //hamburger transitions
    private HamburgerSlideCloseTransition burgerTransition;
    private boolean controlsVisible = false;
    private FadeTransition controlsTransition;
    //zooming/panning
    private double currentScale;
    final double minWidth = 1500;
    final double maxWidth = 5000;

    //showing nodes
    private String selectingLocation = "";
    private boolean locationsSelected;
    private List<EdgeData> currentEdge = new ArrayList<>();
    private List<EdgeData> currentEdges2 = new ArrayList<>();
    private List<NodeData> currentNodes = new ArrayList<>();
    private List<NodeData> currentNodes2 = new ArrayList<>();
    private List<Shape> edgeDrawing = new ArrayList<>();
    private boolean addingEdge;

    private NodeData nodeToEdit;

    final private int originalMapRatioIndex = 3;

    ArrayList<Double> mapRatio = new ArrayList<>();
    private int currentMapRatioIndex;
    private EdgeData mEdge;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setmTestController(TestingController testController) {
        this.mTestController = testController;
    }

    public void initManager(ManageController manageController){
        currentMapRatioIndex =originalMapRatioIndex;
//        mapRatio.add(0.24);
        manager = manageController;
        mapRatio.add(0.318);
        mapRatio.add(0.35);
        mapRatio.add(0.39);
        mapRatio.add(0.43);
        mapRatio.add(0.48);
        mapRatio.add(0.55);
        mapRatio.add(0.60);
        burgerTransition = new HamburgerSlideCloseTransition(burger);
        burgerTransition.setRate(-1);

        controlsTransition = new FadeTransition(new Duration(500), paneControls);
        controlsTransition.setFromValue(0);
        controlsTransition.setToValue(1);
        paneControls.setVisible(controlsVisible);
        currentScale = mapRatio.get(currentMapRatioIndex);
        System.out.println("Scale: " + currentScale);
        imgMap.setFitWidth(maxWidth * currentScale);

        Floor basement2 = new Floor("Basement 2", "45 Francis", "file:floorMaps/Floor_-2.png");
        Floor basement1 = new Floor("Basement 1", "45 Francis", "file:floorMaps/Floor_-1.png");
        Floor ground = new Floor("Ground", "45 Francis", "file:floorMaps/Floor_0.png");
        Floor floor1 = new Floor("Floor 1", "45 Francis", "file:floorMaps/Floor_1.png");
        Floor floor2 = new Floor("Floor 2", "45 Francis", "file:floorMaps/Floor_2.png");
        Floor floor3 = new Floor("Floor 3", "45 Francis", "file:floorMaps/Floor_3.png");

        ArrayList<Floor> basicFloors = new ArrayList<>();
        basicFloors.add(basement2);
        basicFloors.add(basement1);
        basicFloors.add(ground);
        basicFloors.add(floor1);
        basicFloors.add(floor2);
        basicFloors.add(floor3);

        Building hospital = new Building("Hospital");
        System.out.println("before adding floors: " + hospital.getFloors());
        hospital.addAllFloors(basicFloors);
        System.out.println("after adding floors: " + hospital.getFloors());

        floors.addAll(hospital.getFloors());
        System.out.println("current floors: " + floors);

        ASTAR.setSelected(true);
        //buildings.add(hospital);
        floor = 4;

        ObservableList floorList = FXCollections.observableList(new ArrayList<>());
        floorList.addAll(floors);
        comboFloors.setItems(floorList);

        ObservableList buildingList = FXCollections.observableList(new ArrayList<>());
        buildingList.addAll(buildings);
        comboBuilding.setItems(buildingList);

        currentNodes = manager.queryNodeByFloorAndBuilding(convertFloor(floor), "Hospital");
        currentEdge = manager.getAllEdgeData();
        setCircleNodeListSizeAndLocation(setCircleNodeListController(initNodeListCircle(currentNodes),this),currentScale);;
        showNodeAndPath();
    }

    private void showNodeAndPath() {
        clearMain();
        clearEdge();
        clearNodes();
        System.out.println("In show node Path");
        currentNodes = manager.queryNodeByFloor(convertFloor(floor));
        if(allEdgeBox.isSelected()){
            currentEdge = manager.getAllEdgeData();
            //setNodeListImageVisibility(false,setNodeListController(setNodeListSizeAndLocation(initNodeListImage(nodes),currentScale),this.mTestController));  ;
            displayEdges(currentEdge);
        }
        if(allNodeBox.isSelected()){
            setCircleNodeListSizeAndLocation(setCircleNodeListController(initNodeListCircle(currentNodes),this),currentScale);
            showNodeList(currentNodes);
        }
//        setNodeListImageVisibility(true,setNodeListSizeAndLocation(initNodeListImage(currentNodes),currentScale));
//        showNodeList(currentNodes);

    }



    private void showNodeList (List<NodeData> nodeDataList){
        for(int i = 0;i <nodeDataList.size();i++){
            showNode(nodeDataList.get(i));
        }
    }

    private void showNodeList2(List<NodeData> nodeDataList) {
        for (int i = 0; i < nodeDataList.size(); i++) {
            showNode2(nodeDataList.get(i));
        }
    }
    private double mouseX;
    private double mouseY;
    private double imgX;
    private double imgY;
    EventHandler<MouseEvent> nodePressedEventHanlder =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mouseX = event.getX();
                    mouseY = event.getY();
                    imgX = ((ImageView)(event.getSource())).getX();
                    imgY = ((ImageView)(event.getSource())).getY();
                }
            };
    EventHandler<MouseEvent> nodeDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    double offsetX = event.getSceneX() - mouseX;
                    double offsetY = event.getSceneY() - mouseY;
                    double newTranslateX = imgX + offsetX;
                    double newTranslateY = imgY + offsetY;

                    ((ImageView)(event.getSource())).setTranslateX(newTranslateX);
                    ((ImageView)(event.getSource())).setTranslateY(newTranslateY);
                }
            };
    private void showNode(NodeData n) {
//        drawCircle(DBCToUIC(n.getXCoord(), currentScale), DBCToUIC(n.getYCoord(), currentScale));
//        setNodeDragHandler(n, nodeDraggedEventHandler);
//        setNodePressHandler(n, nodePressedEventHanlder);
        panningPane.getChildren().add(n.getCircle());
//        drawCircle();
    }


    private void drawCircle(int x, int y) {
        double radius = CIRCILE_RADIUS * currentScale;
        Circle c = new Circle(x, y, radius, RED);
        panningPane.getChildren().add(c);
        drawings.add(c);
    }

    private void showNode2(NodeData n) {
        currentNodes2.add(n);
        drawCircle2(DBCToUIC(n.getXCoord(), currentScale), DBCToUIC(n.getYCoord(), currentScale));
    }

    private void drawCircle2(int x, int y) {
        double radius = 10 * currentScale;
        Circle c = new Circle(x, y, radius, GREEN);
        panningPane.getChildren().add(c);
        edgeDrawing.add(c);
    }

    private void displayEdge(NodeData n1, NodeData n2) {

        //System.out.println("node one: " + n1 + "and two: " + n2);
        Line line = new Line(n1.getXCoord() * currentScale, n1.getYCoord() * currentScale, n2.getXCoord() * currentScale, n2.getYCoord() * currentScale);
        line.setStrokeWidth(8 * currentScale);
        line.setStroke(BLUE);
        panningPane.getChildren().add(line);
        drawings.add(line);
    }

    private void displayEdge2(NodeData n1, NodeData n2) {
        Line line = new Line(n1.getXCoord() * currentScale, n1.getYCoord() * currentScale, n2.getXCoord() * currentScale, n2.getYCoord() * currentScale);
        line.setStrokeWidth(12 * currentScale);
        line.setStroke(YELLOW);
        panningPane.getChildren().add(line);
        edgeDrawing.add(line);
    }

    private void displayEdges(List<EdgeData> edges) {
        for (EdgeData edge : edges) {
            NodeData node1 = getNode(edge.getStartNode());
            NodeData node2 = getNode(edge.getEndNode());
            if (node1 != null && node2 != null) {
                displayEdge(node1, node2);
            }
        }
    }

    private void clearEdgeDrawing() {
        if (edgeDrawing.size() > 0) {
            for (Shape shape : edgeDrawing) {
                System.out.println("success remove");
                panningPane.getChildren().remove(shape);
            }
            edgeDrawing = new ArrayList<>();
        }
    }

    private NodeData getNode(String nodeID) {
        for (NodeData nodeData : currentNodes) {
            if (nodeData.getNodeID().equals(nodeID)) {
                if (floors.get(floor + 2).getFloorName().equals(currentFloor.getFloorName()) && floors.get(floor + 2).getBuilding().equals(currentFloor.getBuilding())) {
                    return nodeData;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private void setNodeDataToInfoPane(NodeData nodeData) {
        textNodeId.setText(nodeData.getNodeID());
        textNodeBuilding.setText(nodeData.getBuilding());
        textNodeFloor.setText(nodeData.getFloor());
        textNodeFullName.setText(nodeData.getLongName());
        textNodeLocation.setText(nodeData.getLocation().toString());
        textNodeShortName.setText(nodeData.getShortName());
        textNodeTeamAssigned.setText(nodeData.getTeamAssigned());
        textNodeType.setText(nodeData.getNodeType());
    }

    /**
     * reads different mouse click and executes appropraite steps
     *
     * @param e the instance of a mouse click
     */
    public void onClickMap(MouseEvent e) {
        System.out.println("Mouse Clicked");
        //clearMain();
        int x = (int) e.getX();
        int y = (int) e.getY();
        List<NodeData> nodes = manager.queryNodeByFloorAndBuilding(convertFloor(floor), currentFloor.getBuilding());
        switch (selectingLocation) {
            case "":
                System.out.println("Get in findNodeData");
                NodeData mClickedNode = getClosestNode(nodes, x, y);
                break;
            case "selectLocation":
                switch (nodeAction) {
                    case "addNode":
                        textNodeLocation.setText(UICToDBC(x, currentScale) + "," + UICToDBC(y, currentScale));
                        break;
                    case "deleteNode":
                        mClickedNode = getClosestNode(nodes, x, y);
                        setNodeDataToInfoPane(mClickedNode);
                        break;
                    case "editNode":
                        mClickedNode = getClosestNode(nodes, x, y);
                        setNodeDataToInfoPane(mClickedNode);
                        break;
                }
                nodeInfoPane.setVisible(true);
                selectingLocation = "";
                break;
            case "selectEdge":
                NodeData mNode = getClosestNode(nodes, x, y);
                if (selectEdgeN1 == null) {
                    selectEdgeN1 = mNode;
                    showNode2(selectEdgeN1);
                } else if (selectEdgeN2 == null) {
                    selectEdgeN2 = mNode;
                    showNode2(selectEdgeN2);
                }
                if (selectEdgeN2 != null && selectEdgeN1 != null) {
                    displayEdge2(selectEdgeN1, selectEdgeN2);
                    if (edgeAction.equals("addEdge")) {
                        manager.addEdge(selectEdgeN1.getNodeID(), selectEdgeN2.getNodeID());
                    }
                    if (edgeAction.equals("deleteEdge")) {
                        String edgeId = getEdge(currentEdge, selectEdgeN1.getNodeID(), selectEdgeN2.getNodeID());
                        if (!edgeId.equals("")) {
                            manager.deleteEdge(edgeId);
                            System.out.println("Edge Exists");
                        }
                    }
                    selectingLocation = "";
                    showNodeAndPath();
                    selectEdgeN2 = selectEdgeN1 = null;
//                    setupBurger();
                }
                break;
        }
    }

    private String getEdge(List<EdgeData> edgeDataList, String node1, String node2) {
        for (EdgeData edgeData : edgeDataList) {
            if (edgeData.getStartNode().equals(node1) && edgeData.getEndNode().equals(node2)) {
                return edgeData.getEdgeID();
            }
            if (edgeData.getStartNode().equals(node2) && edgeData.getEndNode().equals(node1)) {
                return edgeData.getEdgeID();
            }
        }
        return "";
    }

    @SuppressWarnings("Duplicates")
    private NodeData getClosestNode(List<NodeData> nodeDataList, int mouseX, int mouseY) {
        int dbX = UICToDBC(mouseX, currentScale);
        int dbY = UICToDBC(mouseY, currentScale);
        int resultX = 0;
        int resultY = 0;
        String resultNodeId = "";
        NodeData mNode = null;
        double d = 0;
        for (NodeData node : nodeDataList) {
            int nodeX = node.getXCoord();
            int nodeY = node.getYCoord();
            double temp = Math.sqrt(Math.pow(dbX - nodeX, 2) + Math.pow(dbY - nodeY, 2));
            if (temp < d || d == 0) {
                d = temp;
                resultX = nodeX;
                resultY = nodeY;
                resultNodeId = node.getNodeID();
                mNode = node;
            }
        }
        return mNode;
    }

    @SuppressWarnings("Duplicates")
    public void clearMain() {
        if (drawings.size() > 0) {
            for (Shape shape : drawings) {
                panningPane.getChildren().remove(shape);
            }
            drawings = new ArrayList<>();
        }
        for(NodeData nodeData:currentNodes){
            panningPane.getChildren().remove(nodeData.getCircle());
        }
    }

    private void clearEdge() {
        currentEdge = new ArrayList<>();

    }

    private void clearNodes() {
        currentNodes = new ArrayList<>();
    }

    private void clearNodes2() {
        currentNodes2 = new ArrayList<>();
    }

    private void clearNodeInfoText() {
        textNodeId.setText("");
        textNodeBuilding.setText("");
        textNodeFloor.setText("");
        textNodeFullName.setText("");
        textNodeLocation.setText("");
        textNodeShortName.setText("");
        textNodeTeamAssigned.setText("");
        textNodeType.setText("");
    }

    //map switching methods
    public void addFloor() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/newFloor.fxml"
                ));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        stage.showAndWait();
        Floor newFloor = loader.<NewFloor>getController().getFloor();

        floors.add(newFloor);
        ObservableList fls = comboFloors.getItems();
        fls.add(newFloor);
        comboFloors.setItems(fls);
    }

    public void addBuilding() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/newBuilding.fxml"
                ));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        loader.<NewBuilding>getController().initManager(manager);
        stage.showAndWait();
        Building newBld = loader.<NewBuilding>getController().getBldg();
        buildings.add(newBld);
        ObservableList bldgs = comboBuilding.getItems();
        bldgs.add(newBld);
        comboBuilding.setItems(bldgs);
        setBuilding(newBld);
    }

    @SuppressWarnings("Duplicates")
    public void mapChange(ActionEvent e) {
        /*
        clearEdgeDrawing();
        if(e.getSource() == btnMapUp && floor < 3){
            floor ++;
            System.out.println("up to floor" + floor);
            setFloor();
            clearMain();
            clearEdge();
            clearNodes();
            showNodeAndPath();
        }
        else if (e.getSource() == btnMapDwn && floor > -2){
            floor --;
            System.out.println("down to floor" + floor);
            setFloor();
            clearMain();
            clearEdge();
            clearNodes();
            showNodeAndPath();
        }
        */
        setFloor();
    }

    public void setBuilding(Building newBld) {
        System.out.println("building: " + newBld);
        comboBuilding.setValue(newBld);
        setBuilding();

    }

    public void setBuilding() {
        clearNodes();
        clearEdge();
        Building newBld = (Building) (comboBuilding.getValue());
        floors = newBld.getFloors();

        ObservableList floorList = FXCollections.observableList(new ArrayList<>());
        floorList.addAll(floors);

//        try {
        comboFloors.setItems(floorList);
        comboFloors.setValue(floorList.get(0));
        setFloor((Floor) floorList.get(0));
//        } catch (Exception e) {
//            System.out.println("SCREAM");
//        }
    }

    public void setFloor(Floor newFloor) {
        comboFloors.setValue(newFloor);
        setFloor();
    }

    @SuppressWarnings("Duplicates")
    private void setFloor() {
        currentFloor = (Floor) (comboFloors.getValue());
        floor = floors.indexOf(currentFloor) - 2;
        System.out.println("floor: " + floor);

        String newUrl = currentFloor.getImgUrl();
        System.out.println("new image: " + newUrl);

        Image newImg = new Image(newUrl);
        System.out.println("about to be: " + newImg.getWidth());
        imgMap.setImage(newImg);
        showNodeAndPath();
        /*
        Image oldImg = imgMap.getImage();
        String oldUrl = oldImg.impl_getUrl();  //using a deprecated method for lack of a better solution currently
        System.out.println("old image: " + oldUrl);

        String newUrl = oldUrl.substring(0,oldUrl.indexOf("Floor_")) + "Floor_" + floor + ".png";
        System.out.println("new image: " + newUrl);

        File file = new File(newUrl);
        System.out.println("current map: " + file.toString());
        Image newImg = new Image(file.toString());
        imgMap.setImage(newImg);
        lblCurrentFloor.setText(convertFloor(floor));
        */
    }


    private void newNodeLocation() {
        selectingLocation = "nodeAdd";
        locationsSelected = false;
        gridMapEdit.setVisible(false);
    }


    public void nodeInfoXHandler() {
        nodeInfoPane.setVisible(false);
        clearNodeInfoText();
    }

    public ManageController getManager() {
        return this.manager;
    }

    public void disablePaneScroll() {
        this.paneScroll.setDisable(true);
        this.panningPane.setDisable(true);
    }

    public void enablePaneScroll() {
        this.paneScroll.setDisable(false);
        this.panningPane.setDisable(false);
    }

    public void addHandler(ActionEvent event) {
        if (event.getSource() == btnAddNode) {
            nodeInfoPane.setVisible(true);
            nodeInfoAdd.setVisible(true);
            nodeInfoDelete.setVisible(false);
            nodeInfoEdit.setVisible(false);
            nodeAction = "addNode";
        } else {
            selectingLocation = "selectEdge";
            edgeAction = "addEdge";
//            setupBurger();
//            editEdgePane.setVisible(true);
        }
    }

    public void deleteHandler(ActionEvent event) {
        if (event.getSource() == btnDeleteNode) {
            nodeInfoPane.setVisible(true);
            nodeInfoAdd.setVisible(false);
            nodeInfoDelete.setVisible(true);
            nodeInfoEdit.setVisible(false);
            nodeAction = "deleteNode";
        } else {
            selectingLocation = "selectEdge";
            edgeAction = "deleteEdge";
//            setupBurger();
//            editEdgePane.setVisible(true);
        }
    }

    public void editHandler() {
        nodeInfoPane.setVisible(true);
        nodeInfoAdd.setVisible(false);
        nodeInfoDelete.setVisible(false);
        nodeInfoEdit.setVisible(true);
        nodeAction = "editNode";
    }

    public void displayAddNodeConfirmation(String id, String name, Coordinate loc) {
        String message = "Node " + name + " (" + id + ")  was successfully added to" +
                " the map at location " + loc.toString();
    }

    public void nodeInfoHandler(ActionEvent event) {
        nodeInfoPane.setVisible(false);
        if (event.getSource() == nodeInfoSetLocation) {
            selectingLocation = "selectLocation";
        } else {
            String id = textNodeId.getText();
            String nodeType = textNodeType.getText();
            String location = textNodeLocation.getText();
            String[] locXY = location.split(",");
            Coordinate loc = new Coordinate(Integer.parseInt(locXY[0]), Integer.parseInt(locXY[1]));
            String longName = textNodeFullName.getText();
            String floor = textNodeFloor.getText();
            String shortName = textNodeShortName.getText();
            String building = textNodeBuilding.getText();               //figure out building based on Coordinate
            String teamAssigned = textNodeTeamAssigned.getText();           //figure out what to do with this field for new nodes
            if (event.getSource() == nodeInfoAdd) {
                NodeData newNode = new NodeData(id, loc, floor, building, nodeType, longName, shortName, teamAssigned);
                manager.addNode(newNode);
                displayAddNodeConfirmation(id, longName, loc);
                clearNodeInfoText();
                showNodeAndPath();
            }
            if (event.getSource() == nodeInfoDelete) {
                NodeData newNode = new NodeData(id, loc, floor, building, nodeType, longName, shortName, teamAssigned);
                manager.deleteNode(newNode);
                clearNodeInfoText();
                showNodeAndPath();
            }
            if (event.getSource() == nodeInfoEdit) {
                NodeData newNode = new NodeData(id, loc, floor, building, nodeType, longName, shortName, teamAssigned);
                manager.editNode(newNode);
                clearNodeInfoText();
                showNodeAndPath();
            }
        }

    }

    public void selectPFAlgorithm(ActionEvent e) {
        PathfindingController.searchType searchType = PathfindingController.searchType.DFS;
        Object mEvent = e.getSource();
        System.out.println("Source" + mEvent.toString());
        if (mEvent == DFS) {
            searchType = PathfindingController.searchType.DFS;
        }
        if (mEvent == BFS) {
            searchType = PathfindingController.searchType.BFS;
        }
        if (mEvent == Dijkstra) {
            searchType = PathfindingController.searchType.DIJKSTRA;
        }
        if (mEvent == ASTAR) {
            searchType = PathfindingController.searchType.ASTAR;
        }
        if (mEvent == Beam) {
            searchType = PathfindingController.searchType.BEST;
        }
        if (mEvent == BestFirst) {
            searchType = PathfindingController.searchType.BEAM;
        }
        mTestController.setSearchType(searchType);
    }

    public void openMenuHandler() {
        setupBurger();
    }

    public void setupBurger() {
        burgerTransition.setRate(burgerTransition.getRate() * -1);
        burgerTransition.play();

        controlsVisible = !controlsVisible;
        controlsTransition.play();
        paneControls.setVisible(controlsVisible);

        controlsTransition.setToValue(Math.abs(controlsTransition.getToValue() - 1));         //these two lines should make it fade out the next time you click
        controlsTransition.setFromValue(Math.abs(controlsTransition.getFromValue() - 1));     // but they doent work the way I want them to for some reason
    }

    public void displayDeleteNodeConfirmation(NodeData nodeToRemove) {
        gridMapEdit.setVisible(true);
    }

    @SuppressWarnings("Duplicates")
    public void zoomHandler(ActionEvent e) {
//        clearMain();

        if (e.getSource() == btnZoomOut) {
//            if(imgMap.getFitWidth() <= minWidth){
//                return;
//            }
            if (currentMapRatioIndex == 0) {
                return;
            }
            currentMapRatioIndex -= 1;
            currentScale = mapRatio.get(currentMapRatioIndex);
            imgMap.setFitWidth(maxWidth * currentScale);
        } else {
//            if(imgMap.getFitWidth() >= MAX_UI_WIDTH){
//                return;
//            }
            if (currentMapRatioIndex == (mapRatio.size() - 1)) {
                return;
            }
            currentMapRatioIndex += 1;
            currentScale = mapRatio.get(currentMapRatioIndex);
            imgMap.setFitWidth(maxWidth * currentScale);
        }
//        clearMain();
//        if (!(currentEdge == null) && !currentEdge.isEmpty()) {
//            System.out.println("case edge 1");
//            List<EdgeData> mEdges = currentEdge;
//            clearEdge();
//            displayEdges(mEdges);
//        }
//        if (!(currentNodes == null) && !currentNodes.isEmpty()) {
//            List<NodeData> mNodes = manager.queryNodeByFloorAndBuilding(convertFloor(floor), "45 Francis");
//            System.out.println("case node main, floor = " + floor);
//            clearNodes();
//            showNodeList(mNodes);
//            System.out.println(currentScale);
//        }
        showNodeAndPath();
        if (mEdge != null) {

            System.out.println("case edge 2");
            displayEdge2(selectEdgeN1, selectEdgeN2);
        }
        if (!(currentNodes2 == null) && !currentNodes2.isEmpty()) {
            List<NodeData> mNodes = currentNodes2;
            clearNodes2();
            showNodeList2(mNodes);
            System.out.println(currentScale);
        }
    }

    public void returnToMain(ActionEvent event) {
        Object mEvent = event.getSource();
        if (mEvent == returnMain) {
            // get a handle to the stage
            Stage stage = (Stage) returnMain.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
    }

    public ArrayList<Building> getBuildings(){
       return buildings;
    }

    public void sendBuildings(ArrayList<Building> curBuildings){
        System.out.println(buildings);
        buildings = curBuildings;
        System.out.println(buildings);
        ObservableList bldgs = comboBuilding.getItems();
        for (Building b : buildings) {
            if (!bldgs.contains(b))
                bldgs.add(b);
        }
        comboBuilding.setItems(bldgs);
        setBuilding((Building) bldgs.get(0));
    }

    public void allNodeButtonHandler(ActionEvent event){
        showNodeAndPath();
    }

    public void allEdgeButtonHandler(ActionEvent event){
        showNodeAndPath();
    }

    @Override
    public void passNodeData(NodeData nodeData) throws IOException {

    }

    @Override
    void passEdgeData(EdgeData edgeData) {

    }
}
