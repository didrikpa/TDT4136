package simulated_annealing;

import java.util.ArrayList;

/**
 * Created by didrikpa on 09.10.15.
 */
public class SimulatedAnnealing {
    private int m; // rows
    private int n; // columns
    private int k; // constraint
    private ArrayList<ArrayList<String>> board;

    public SimulatedAnnealing(int m, int n, int k){
        this.m = m;
        this.n = n;
        this.k = k;
        this.board = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            board.add(new ArrayList<String>());
            for (int j = 0; j < n; j++) {
                board.get(i).add(" ");
            }
        }

    }
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
    private boolean checkColumns(ArrayList<ArrayList<String>> currentBoard){
        int a = 1;
        int b = 0;
        ArrayList<Integer> checks = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            checks.add(0);
            for (int j = 0; j < n; j++) {
                if (currentBoard.get(i).get(j).equals("O")){
                    b = checks.get(i)+a;
                    checks.set(i, b);
                }
            }
        }
        for (int i = 0; i < checks.size(); i++) {
            if (checks.get(i) > k){
                return false;
            }
        }
        return true;
    }
    public boolean checkDiagonalsInSquares(){
        // 2 = 2
        // 3 = 6
        // 4 = 10
        // 5 = 14
        // 6 = 18
        // 7 = 22
        // 8 = 26
        ArrayList<Integer> checks = new ArrayList<>();
        for (int i = 1; i < m*2; i++) {
            checks.add(i-1, 0);
        }
        System.out.println(checks);
        return false;
    }

    public boolean checkDiagonalsInRectangles(){

    }

    public static void main(String[] args) {
        SimulatedAnnealing sa = new SimulatedAnnealing(5,5,2);
        System.out.println(sa.checkRows(sa.board));
        System.out.println(sa.checkColumns(sa.board));
        sa.checkDiagonalsInSquares();
    }


}
