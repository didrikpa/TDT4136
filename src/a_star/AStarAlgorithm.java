package a_star;


import java.io.IOException;
import java.util.ArrayList;

public class AStarAlgorithm {
    public String algorithm;
    public Node startNode; //Startnode
    public Node goalNode; // Goalnode
    public ArrayList<Node> nodes; // A list with al the nodes.
    public ArrayList<Node> open; // A list with all "open" nodes or neighbour nodes.
    public ArrayList<Node> closed; // A list with all the closed nodes.
    public ArrayList<ArrayList<String>> board; // Representation of the board via ArrayLists with Strings
    ReadFile rf = new ReadFile();

    //Constructor
    public AStarAlgorithm() {
        algorithm = "A*"; // Defines which algorithm to use. 'A*' 'bfs' ' dijkstra'
        nodes = new ArrayList<>();
        open = new ArrayList<>();
        closed = new ArrayList<>();
        try {
            board = rf.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // This function makes the start- and goalnodes by reading the board.
    public void setStartAndGoal() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                if (board.get(i).get(j).equals("A")) {
                    startNode = new Node(i, j, null, "A");
                } else if (board.get(i).get(j).equals("B")) {
                    goalNode = new Node(i, j, null, "B");
                }
            }
        }
    }

    // This functions makes the rest of the nodes and appends all the nodes to the nodes-list.
    public void setNodes() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                if (startNode.getxCoord() == i && startNode.getyCoord() == j) {
                    nodes.add(startNode); // Adds the startnode
                } else if (goalNode.getxCoord() == i && goalNode.getyCoord() == j) {
                    nodes.add(goalNode); // Adds the goalnode
                } else {
                    nodes.add(new Node(i, j, null, board.get(i).get(j))); // Makes and adds the rest of the nodes.
                }
            }
        }
        // Sets the g, h and f values for the nodes.
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).equals(startNode)) {
                continue;
            } else {
                nodes.get(i).setgValue(nodes.get(i).getBoardValue());
                nodes.get(i).sethValue(goalNode);
                nodes.get(i).setfValue();
            }
        }
    }

    // This function finds the neighbours of the node we're currently in.
    public void findNeighboursWithWeights(Node current) {
        for (int j = 0; j < 4; j++) { // Can find max four neighbours
            for (int i = 0; i < nodes.size(); i++) { // Check all nodes
                if (("[wmfgrB.]").indexOf(nodes.get(i).getBoardValue()) != -1) { //Check if the node is traverseable
                    // Find the neighbours.
                    if (((Math.abs(nodes.get(i).getyCoord() - current.getyCoord()) <= 1)
                            && (nodes.get(i).getxCoord() == current.getxCoord()))
                            || ((Math.abs(nodes.get(i).getxCoord() - current.getxCoord()) <= 1)
                            && (nodes.get(i).getyCoord() == current.getyCoord()))) {
                        nodes.get(i).setParent(current);
                        nodes.get(i).updategValue(nodes.get(i).getParent());
                        nodes.get(i).setfValue();
                        open.add(nodes.get(i));
                        nodes.remove(nodes.get(i));
                    }
                }
            }
            // Check Closed nodes if its possible to get to that node with a lower value.
            for (int i = 0; i < closed.size(); i++) {
                if (("[wmfgrB.]").indexOf(closed.get(i).getBoardValue()) != -1) {
                    if (((Math.abs(closed.get(i).getyCoord() - current.getyCoord()) <= 1)
                            && (closed.get(i).getxCoord() == current.getxCoord()))
                            || ((Math.abs(closed.get(i).getxCoord() - current.getxCoord()) <= 1)
                            && (closed.get(i).getyCoord() == current.getyCoord()))) {
                        if (closed.get(i).getfValue() > current.getfValue() + closed.get(i).getgValue()) {
                            closed.get(i).setParent(current);
                            closed.get(i).updategValue(closed.get(i).getParent());
                            closed.get(i).setfValue();
                        }
                    }
                }
            }
        }
    }

    // The a* Algorithm
    public void aStar() {
        setStartAndGoal(); // Set the start an goal nodes.
        setNodes(); //Set the other nodes.
        Node current = startNode; // Set current node to startnode
        open.add(current); // Add startnode to open.
        nodes.remove(current); //Remove startnode from nodes.
        Boolean solution = false; //Set solution to false, and search for a solution.
        while (!solution) {
            //Find current nodes neighbours
            findNeighboursWithWeights(current);
            open.remove(current); //Remove current from open
            closed.add(current); // Add current to closed
            //Check if current node is the goalnode
            if (current == goalNode) {
                boolean c = true;
                int counter = 0;
                while (c) {
                    // If current node is the goalnode, set the shortest path from the goalnode to the startnode via nodes parents.
                    if (current.hasParent()) {
                        if (counter > 0) {
                            board.get(current.getxCoord()).set(current.getyCoord(), current.getBoardValue() + "P");
                            current = current.getParent();
                        } else {
                            current = current.getParent();
                        }
                        counter += 1;
                    } else {
                        c = false;
                    }
                }
                break;
            }
            current = open.get(0); // Set current to the first element in open. Equivalent to BFS
            // A*
            if(algorithm.equals("A*")){
                for (int i = 0; i < open.size(); i++) { // Make it a priority queue by iterating through open and check if there is a f-value
                    if (open.get(i).getfValue() < current.getfValue()) { // smaller than the current.
                        current = open.get(i); // if there is a f-value smaller than current, that node is set to current
                    }
                }
                //Dijkstra
            } else if (algorithm.equals("dijkstra")) {
                for (int i = 0; i < open.size(); i++) { // Make it a priority queue by iterating through open and check if there is a g-value
                    if (open.get(i).getgValue() < current.getgValue()) { // smaller than the current.
                        current = open.get(i); // if there is a g-value smaller than current, that node is set to current
                    }
                }
            }

            // Set neighbournodes on the board
            for (int i = 0; i < open.size(); i++) {
                if (!open.get(i).getBoardValue().equals("B")) {
                    board.get(open.get(i).getxCoord()).set(open.get(i).getyCoord(), open.get(i).getBoardValue() + "N");
                }
            }
            // Set "visited" nodes on the board.
            if (("[mwfrg.]").indexOf(current.getBoardValue()) != -1) {
                board.get(current.getxCoord()).set(current.getyCoord(), current.getBoardValue() + "V");
            }
        }
    }

    public static void main(String[] args) {
        AStarAlgorithm asa = new AStarAlgorithm();
        asa.aStar();
    }
}
