package simulated_annealing;

import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing {
    private int m; // rows
    private int n; // columns
    private int k; // constraint
    private int optimal; // Optimal number of eggs.
    private ArrayList<ArrayList<ArrayList<String>>> neighbors; // A list containing all legal neighbor boards.
    private ArrayList<Double> neighborValues; // A list containing the value of the the neighbor boards
    private double temp; // Temperatur
    private Random random;
    private ArrayList<ArrayList<Integer>> diagonalsCheck; //A list used to check diagonals
    private ArrayList<ArrayList<String>> board; // The main board.
    private ArrayList<ArrayList<String>> pMax; // pMax - the neighbor board with best value.

    //Constructor
    public SimulatedAnnealing(int m, int n, int k){
        this.m = m;
        this.n = n;
        this.k = k;
        this.optimal = k*m; //THe optimal value is k*m
        this.temp = optimal; //Set temp to optimal
        this.random = new Random();
        this.board = new ArrayList<>();
        this.neighbors = new ArrayList<>();
        this.diagonalsCheck = new ArrayList<>();
        this.neighborValues = new ArrayList<>();
    }
    // This function sets a random board for the algorithm to start with.
    private ArrayList<ArrayList<String>> setBoard(ArrayList<ArrayList<String>> board){
        for (int i = 0; i < m; i++) {
            board.add(new ArrayList());
            for (int j = 0; j < n; j++) {
                int rand = random.nextInt((10-1)+1)+1;
                if (rand+j%2==0 || rand+j%3==1 || rand+j%5==2 || rand+j%2==1 || rand+j%3==0 || rand+j == m+m-2 || rand+j%5==0){
                    board.get(i).add("O");
                }
                else {
                    board.get(i).add(" ");

                }
            }
        }
       if (!isLegal(board)){ // Check if board is legal, if not clear board and make a new one.
           board.clear();
           setBoard(board);
       }
       return board;
    }
    //Prints the board.
    private void printBoard(){
        for (int i = 0; i < board.size(); i++) {
            System.out.print(board.get(i) + "\n");
        }
        System.out.println("");
    }
    //Sets up the tiles of which diagonals are to be checked from.
    private ArrayList<ArrayList<Integer>> setUpDiagonals(){
        int counter = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || j == 0 || j == m-1){
                    diagonalsCheck.add(new ArrayList<>());
                    diagonalsCheck.get(counter).add(i);
                    diagonalsCheck.get(counter).add(j);
                    counter+=1;
                }
            }
        }
        return diagonalsCheck;
    }
    //Checks the diagonals
    private boolean checkDiagonals(ArrayList<ArrayList<String>> board){
        for (int i = 0; i < diagonalsCheck.size(); i++) {
            int row = diagonalsCheck.get(i).get(0);
            int col = diagonalsCheck.get(i).get(1);
            int counter = 0;
            while (row<m && col <m){
                if (board.get(row).get(col)== ("O")){
                    counter+=1;
                }
                if (counter>k){
                    return false;
                }
                row+=1;
                col+=1;
            }
            row = diagonalsCheck.get(i).get(0);
            col = diagonalsCheck.get(i).get(1);
            counter = 0;
            while (row<m && col>=0){
                if (board.get(row).get(col).equals("O")){
                    counter+=1;
                }
                if (counter >k){
                    return false;
                }
                row+=1;
                col-=1;
            }

        }
        return true;
    }
    //Checks the rows.
    private boolean checkRows(ArrayList<ArrayList<String>> currentBoard){
        for (int i = 0; i < m; i++) {
            int counter = 0;
            for (int j = 0; j < n; j++) {
                if (currentBoard.get(i).get(j).equals("O")){
                    counter+=1;
                }
            }
            if (counter > k){
                return false;
            }
        }
        return true;
    }
    //Checks the columns
    private boolean checkColumns(ArrayList<ArrayList<String>> currentBoard){
        int a = 1;
        int b = 0;
        ArrayList<Integer> checks = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            checks.add(0);
            int counter = 0;
            for (int j = 0; j < n; j++) {
                if (currentBoard.get(j).get(i).equals("O")){
                    counter+=1;

                }
            }
            if (counter>k){
                return false;
            }
        }
        return true;
    }
    //checks  rows, columns and diagonals and returns whether the board is legal or not.
    private boolean isLegal(ArrayList<ArrayList<String>> board){//Check if the board is a legal board.
        if (checkColumns(board) && checkRows(board) && checkDiagonals(board)){
            return true;
        }
        return false;
    }
    //The objective function. Gives each board a value based on number of eggs on the board. Since only legal boards can be
    // generated, the functions gives an ok indication wether a board is good or not.
    private double evaluate(ArrayList<ArrayList<String>> board){
        int maincounter = 0;
        for (int i = 0; i < board.size(); i++) {
            int counter = 0;
            for (int j = 0; j < board.size(); j++) {
                if (board.get(i).get(j).equals("O")){
                    counter+=1;
                }
            }
            maincounter+=counter;
        }
        return maincounter;
    }
    // counts the egss.
    private int countEggs(ArrayList<ArrayList<String>> board){
        int counter = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board.get(i).get(j).equals("O")){
                    counter+=1;
                }
            }
        }
        return counter;
    }
    //Removes one egg, given the input.
    private ArrayList<ArrayList<String>> removeEgg(ArrayList<ArrayList<String>> board, int row, int col){
        ArrayList<ArrayList<String>> fakeBoard = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            fakeBoard.add(new ArrayList());
            for (int j = 0; j < m; j++) {
                fakeBoard.get(i).add(board.get(i).get(j));
                }
            }
        fakeBoard.get(row).set(col, " ");
        return fakeBoard;
    }
    //adds an egg to the board, but only if the board is still legal after the add.
    private ArrayList<ArrayList<ArrayList<String>>> addRandomEgg(ArrayList<ArrayList<String>>board , int row, int col){
        ArrayList<ArrayList<ArrayList<String>>> neighborswithAdd = new ArrayList<>();
        ArrayList<ArrayList<String>> fakeBoard = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            fakeBoard.add(new ArrayList());
            for (int j = 0; j < board.size(); j++) {
                fakeBoard.get(i).add(board.get(i).get(j));
            }
        }
        fakeBoard.get(row).set(col, "O");
        neighborswithAdd.add(fakeBoard);
        neighborswithAdd.add(addMoreEggs(fakeBoard));

        return neighborswithAdd;
    }

    private ArrayList<ArrayList<String>> addMoreEggs(ArrayList<ArrayList<String>> board){
        ArrayList<ArrayList<String>> fakeBoard = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            fakeBoard.add(new ArrayList<>());
            for (int j = 0; j < board.size(); j++) {
                fakeBoard.get(i).add(board.get(i).get(j));
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (fakeBoard.get(i).get(j).equals(" ")){
                    fakeBoard.get(i).set(j, "O");
                    if (!isLegal(fakeBoard)){
                        fakeBoard.get(i).set(j, " ");
                    }
                }
            }
        }
        return fakeBoard;
    }
    // generates neighbors via adding and removing eggs.
    private ArrayList<ArrayList<ArrayList<String>>> generateNeighbours(ArrayList<ArrayList<String>> board){
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (board.get(i).get(j).equals("O")){
                    neighbors.add(removeEgg(board, i, j));
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (board.get(i).get(j).equals(" ")){
                    for (int l = 0; l < addRandomEgg(board, i, j).size(); l++) {
                        if (isLegal(addRandomEgg(board, i, j).get(l))){
                            if (!neighbors.contains(addRandomEgg(board, i, j).get(l))){
                                neighbors.add(addRandomEgg(board, i, j).get(l));
                            }

                        }
                    }
                }
            }
        }
        return neighbors;
    }
    private ArrayList<ArrayList<String>> simulatedAnnealing(ArrayList<ArrayList<String>> board){
        long startTime = System.currentTimeMillis();
        ArrayList<ArrayList<String>> bestBoard = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            bestBoard.add(new ArrayList<>());
            for (int j = 0; j < m; j++) {
                bestBoard.get(i).add(" ");
            }
        }
        board = setBoard(board);
        setUpDiagonals();
        int iterations = 0;
        while(temp>1){
            if (evaluate(board)>=optimal){
                long endTime   = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println(totalTime);
                System.out.println(iterations);
                printBoard();
                return board;
            }
            neighbors.clear();
            neighbors = generateNeighbours(board);
            neighborValues.clear();
            double a = optimal+1;
            for (int i = 0; i < neighbors.size(); i++) {
                neighborValues.add(evaluate(neighbors.get(i)));
                if (evaluate(neighbors.get(i))<a){
                    a = evaluate(neighbors.get(i));
                }
            }
            pMax = neighbors.get(neighborValues.lastIndexOf(a));
            //neighbors.remove(pMax);
            double q = (evaluate(pMax)-evaluate(board))/evaluate(board);
            double p = Math.min(1,Math.exp(-q/temp));
            double x = random.nextDouble();
            if (x>p){
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < m; j++) {
                        board.get(i).set(j, pMax.get(i).get(j));
                    }
                }
            } else{
                ArrayList<ArrayList<String>> temp = new ArrayList<>();
                int nextNeigh = random.nextInt(neighbors.size());
                for (int i = 0; i < m; i++) {
                    temp.add(new ArrayList<>());
                    for (int j = 0; j < m; j++) {
                        temp.get(i).add(neighbors.get(nextNeigh).get(i).get(j));
                    }
                }
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < m; j++) {
                        board.get(i).set(j, temp.get(i).get(j));
                    }
                }
            }
            temp *=0.999999;
            iterations+=1;
            if (evaluate(board)>evaluate(bestBoard)){
                for (int i = 0; i <m ; i++) {
                    for (int j = 0; j < m; j++) {
                        bestBoard.get(i).set(j, board.get(i).get(j));
                    }
                }
            }
            //System.out.println(iterations);
        }
        return bestBoard;
    }

    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(8,8,1);
        sa.simulatedAnnealing(sa.board);
    }
}
