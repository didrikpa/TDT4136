package a_star;


import java.io.IOException;
import java.util.ArrayList;

public class AStarAlgorithm {
    public Node startNode;
    public Node goalNode;
    public ArrayList<Node> nodes;
    public ArrayList<Node> open;
    public ArrayList<Node> closed;
    public ArrayList<ArrayList<String>> board;
    ReadFile rf = new ReadFile();

    public AStarAlgorithm(){
        nodes = new ArrayList<>();
        open = new ArrayList<>();
        closed = new ArrayList<>();
        try {
            board = rf.read();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void printBoard(){
        for (int i = 0; i < board.size(); i++) {
            System.out.println(board.get(i) + "\n");
        }
        System.out.println("");

    }

    public void setStartAndGoal(){
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                if(board.get(i).get(j).equals("A")) {
                    startNode = new Node(i, j, null, "A");
                }
                else if(board.get(i).get(j).equals("B")){
                    goalNode = new Node(i, j, null, "B");
                }
            }
        }
    }

    public void setNodes(){
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                if (startNode.getxCoord() == i && startNode.getyCoord() == j){
                    nodes.add(startNode);
                }
                else if (goalNode.getxCoord() == i && goalNode.getyCoord() == j){
                    nodes.add(goalNode);
                }
                else {
                    nodes.add(new Node(i, j, null, board.get(i).get(j)));
                }
            }
        }
        for (int i = 0; i <nodes.size() ; i++) {
            if (nodes.get(i).equals(startNode)){
                continue;
            }
            else {
                nodes.get(i).setgValue(nodes.get(i).getBoardValue());
                nodes.get(i).sethValue(goalNode);
                nodes.get(i).setfValue();
            }
        }
    }

    public void findNeighboursWithWeights(Node current){
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i <nodes.size() ; i++) {
                if (("[wmfgrB.]").indexOf(nodes.get(i).getBoardValue()) != -1){
                    if ((Math.abs(nodes.get(i).getyCoord() - current.getyCoord()) <= 1) && (nodes.get(i).getxCoord() == current.getxCoord()) && (!nodes.get(i).equals(current)) && !open.contains(nodes.get(i))) {
                        nodes.get(i).setParent(current);
                        nodes.get(i).updategValue(nodes.get(i).getParent());
                        nodes.get(i).setfValue();
                        open.add(nodes.get(i));
                        nodes.remove(nodes.get(i));
                    } else if ((Math.abs(nodes.get(i).getxCoord() - current.getxCoord()) <= 1) && (nodes.get(i).getyCoord() == current.getyCoord()) && (!nodes.get(i).equals(current)) &&  !open.contains(nodes.get(i))) {
                        nodes.get(i).setParent(current);
                        nodes.get(i).updategValue(nodes.get(i).getParent());
                        nodes.get(i).setfValue();
                        open.add(nodes.get(i));
                        nodes.remove(nodes.get(i));
                    }
                }
            }
        }

    }

    public void aStar(){
        setStartAndGoal();
        setNodes();
        Node current = startNode;
        open.add(current);
        nodes.remove(current);
        Boolean solution = false;
        while(!solution){
            for (int i = 0; i <open.size(); i++) {
                findNeighboursWithWeights(current);
            }
            open.remove(current);
            closed.add(current);
            if (current == goalNode){
                boolean c = true;
                while (c){
                    if (current.hasParent()){
                        board.get(current.getxCoord()).set(current.getyCoord(), "P");
                        current = current.getParent();
                    } else{
                        printBoard();
                        c = false;
                    }
                }
                break;
            }
            current = open.get(0);
            for (int i = 0; i < open.size(); i++) {
                if(open.get(i).getfValue() < current.getfValue()){
                    current = open.get(i);
                }
            }
            for (int i = 0; i < open.size(); i++) {
                if (!current.getBoardValue().equals("B")) {
                    board.get(open.get(i).getxCoord()).set(open.get(i).getyCoord(), "N");
                }
            }
            if (("[mwfrg.]").indexOf(current.getBoardValue()) != -1) {
                board.get(current.getxCoord()).set(current.getyCoord(), "V");
            }
            printBoard();
        }
    }

    public static void main(String[] args) {
        AStarAlgorithm asa = new AStarAlgorithm();
        asa.aStar();
    }
}
