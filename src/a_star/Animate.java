package a_star;/**
 * Created by didrikpa on 02.10.15.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Animate extends Application {
    FXMLLoader fxmlLoader;
    Parent root;
    public Stage ps;
    GridPane gp;



    @Override
    public void start(Stage primaryStage) throws IOException{
        gp = new GridPane();
        gp.addColumn(0);
        gp.setGridLinesVisible(false);
        fxmlLoader = new FXMLLoader();
        root = (Parent) fxmlLoader.load(this.getClass().getResource("/a_star/board.fxml"));
        ps = primaryStage;
        primaryStage.setScene(new Scene(gp));
        primaryStage.setTitle("A*");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
