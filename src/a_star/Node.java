package a_star;


public class Node {
    //This is the Node-class. Each node represents one tile on the board.
    public String boardValue; // Is the tile open, closed, a water tile, a mountain tile, etc?
    public int hValue; //Heuristic value
    public int gValue; //Value between from one tile to another
    public int fValue; // The sum of hValue and gValue
    public int xCoord; // X Coordination on the board.
    public int yCoord; // y Coordination on the board.
    Node parent; // Connection to the parent node.


    public Node(int xCoord, int yCoord, Node parent, String boardValue){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.parent = parent;
        this.boardValue = boardValue;
    }
    // Different setters and getters for the values.
    public void sethValue(Node goal){
        this.hValue = (Math.abs(xCoord-goal.getxCoord())+(Math.abs(yCoord-goal.getyCoord())));
    }
    public void setgValue(int gValue){
        this.gValue += gValue;
    }
    public void setgValue(){
        this.gValue = 1;
    }
    public void setfValue(){
        this.fValue = gValue+hValue;
    }
    public void setxCoord(int xCoord){
        this.xCoord = xCoord;
    }
    public void setyCoord(int yCoord){
        this.yCoord = yCoord;
    }
    public void setParent(Node parent){
        this.parent = parent;
    }
    public void setBoardValue(String boardValue){
        this.boardValue = boardValue;
    }
    public int gethValue(){
        return hValue;
    }
    public int getgValue(){
        return gValue;
    }
    public int getfValue(){
        return fValue;
    }
    public int getxCoord(){
        return xCoord;
    }
    public int getyCoord(){
        return yCoord;
    }
    public Node getParent(){
        return parent;
    }
    public String getBoardValue(){
        return boardValue;
    }
    // To string-method
    public String toString(){
        return "(" + xCoord + ", " + yCoord + ")";
    }
    // Check if the node has a parent
    public boolean hasParent(){
        if (this.getParent() == (null)){
            return false;
        } else{
            return true;
        }

    }

}
