package Model;

/**
 *  Main class
 *
 * RUNTIME ERROR
 * While writing this project I experienced the OutofBounds error "java.lang.reflect.InvocationTargetException OutofBounds". I corrected this error by modifying my for loop logic to only iterate through the array within bounds.
 *
 * FUTURE ENHANCEMENT
 * A future enhancement I would love to add to this product would be a proper database to save and retrieve parts and products from.
 *
 *
 * @author Nathaniel Unruh
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Stage primaryStage;

    /**
     * Starts the first form
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../ViewControllers/MainForm.fxml"));
        primaryStage.setTitle("WGU Software 1");
        primaryStage.setScene(new Scene(root, 1280, 600));
        primaryStage.show();
    }


    /**
     *  RUNTIME ERROR
     *  While writing this project I experienced the OutofBounds error "java.lang.reflect.InvocationTargetException OutofBounds". I corrected this error by modifying my for loop logic to only iterate through the array within bounds.
     *
     *  FUTURE ENHANCEMENT
     *  A future enhancement I would love to add to this product would be a proper database to save and retrieve parts and products from.
     *
     * @param args
     */

    public static void main(String[] args) {
        launch(args);
    }
}
