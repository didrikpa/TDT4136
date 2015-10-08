package a_star;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {

    //This file reads the board textfiles and return them as nested ArrayLists. No further commenting needed.
    public static ArrayList<ArrayList<String>> read() throws IOException {
        ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
        File file = new File("/home/didrikpa/IdeaProjects/ArtificialIntelligence/src/a_star/boards/board-2-4.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int counter = 0;
        while ((line = br.readLine()) != null) {
            board.add(new ArrayList<String>());
            for (int i = 0; i < line.split("").length; i++) {
                board.get(counter).add(line.split("")[i]);
            }
            counter += 1;

        }
        br.close();
        fr.close();

        return board;
    }
}
