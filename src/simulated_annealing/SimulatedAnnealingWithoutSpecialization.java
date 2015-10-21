package simulated_annealing;

import java.util.ArrayList;

/**
 * Created by didrikpa on 20.10.15.
 */
/*public class SimulatedAnnealingWithoutSpecialization {
    private void simulatedAnnealing(int m, int n, int k){
        int iterations = 0;
        double optimal = m*k;
        double temp = optimal;
        while(temp>1){
            if (evaluate(board)>=optimal){
                printBoard();
                return board;
            }
            neighbors.clear();
            neighbors = generateNeighbours(board);
            neighborValues.clear();
            double a = 0;
            for (int i = 0; i < neighbors.size(); i++) {
                neighborValues.add(evaluate(neighbors.get(i)));
                if (evaluate(neighbors.get(i))>a){
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
        }

    }

}
*/