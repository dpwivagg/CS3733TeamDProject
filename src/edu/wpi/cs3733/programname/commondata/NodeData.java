package edu.wpi.cs3733.programname.commondata;

public class NodeData {

    private String id;
    private Coordinate location;
    private String floor;
    private String type;
    private String longName;
    private String shortName;


    public NodeData(String id, Coordinate location, String floor,
                    String type, String longName, String shortName) {
        this.id = id;
        this.location = location;
        this.floor = floor;
        this.type = type;
        this.longName = longName;
        this.shortName = shortName;
    }

    public NodeData(String id, int x, int y, String floor,
                    String type, String longName, String shortName) {
        this.id = id;
        this.location = new Coordinate(x,y);
        this.floor = floor;
        this.type = type;
        this.longName = longName;
        this.shortName = shortName;
    }

    public String getId() { return this.id; }

    public void setId(String newId) {
        this.id = newId;
    }

    public int getX() {
        return this.location.getX();
    }

    public int getY() {
        return this.location.getY();
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeData nodeData = (NodeData) o;

        if (!id.equals(nodeData.id)) return false;
        if (!location.equals(nodeData.location)) return false;
        if (!floor.equals(nodeData.floor)) return false;
        return shortName.equals(nodeData.shortName);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + floor.hashCode();
        result = 31 * result + shortName.hashCode();
        return result;
    }
}
