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
                nodes.get(i).setgValue();
                nodes.get(i).sethValue(goalNode);
                nodes.get(i).setfValue();
            }
        }
    }

    public void findNeighbours(Node current){
        for (int i = 0; i <nodes.size() ; i++) {
            if (nodes.get(i).getBoardValue().equals(".") || nodes.get(i).getBoardValue().equals("B")) {
                if ((Math.abs(nodes.get(i).getyCoord() - current.getyCoord()) <= 1) && (nodes.get(i).getxCoord() == current.getxCoord()) && (!nodes.get(i).equals(current)) && !open.contains(nodes.get(i)) && !closed.contains(nodes.get(i))) {
                    nodes.get(i).setParent(current);
                    open.add(nodes.get(i));
                    nodes.remove(nodes.get(i));
                } else if ((Math.abs(nodes.get(i).getxCoord() - current.getxCoord()) <= 1) && (nodes.get(i).getyCoord() == current.getyCoord()) && (!nodes.get(i).equals(current)) &&  !open.contains(nodes.get(i)) && !closed.contains(nodes.get(i))) {
                    nodes.get(i).setParent(current);
                    open.add(nodes.get(i));
                    nodes.remove(nodes.get(i));
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
        int counter = 0;
        while(solution == false){
            for (int i = 0; i <open.size(); i++) {
                findNeighbours(current);
            }
            open.remove(current);
            closed.add(current);
            for (int i = 0; i < open.size(); i++) {
                  if (open.get(i).parent.gethValue() == 1){
                      System.out.println(open.get(i).getxCoord()+ " " + open.get(i).getyCoord() + "yay");
                      solution = true;
                      boolean c = true;
                      while (c){
                          if (current.hasParent()){
                              board.get(current.getxCoord()).set(current.getyCoord(), "R");
                              current = current.getParent();
                          } else{
                              printBoard();
                              c = false;
                          }
                      }
                      break;
                  }
            }
            current = open.get(0);
            if (open.size()>1){
                for (int i = 0; i < open.size(); i++) {
                    open.get(i).setgValue(open.get(i).getParent().getgValue());
                    if(open.get(i).getfValue() < current.getfValue()){
                        current = open.get(i);
                    }
                }
            }
            if (current.getBoardValue().equals(".")) {
                board.get(current.getxCoord()).set(current.getyCoord(), "O");
            }
            printBoard();
        }
    }

    public static void main(String[] args) {
        AStarAlgorithm asa = new AStarAlgorithm();
        asa.aStar();
    }
}
