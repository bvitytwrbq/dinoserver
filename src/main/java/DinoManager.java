import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DinoManager extends Application {

    public DinoManager() {
    }

    @Override
    public void init() {
        //By default this does nothing, but it
        //can carry out code to set up your app.
        //It runs once before the start method,
        //and after the constructor.
    }

    @Override
    public void start(Stage primaryStage) {
        Button stegoButton = new Button("stego");
        Button brontoButton = new Button("bronto");
        Button increaseBrontoButton = new Button("add 5 brontos");
        Button decreaseBrontoButton = new Button("remove 5 brontos");
        brontoButton.setTranslateY(-101);
        brontoButton.setTranslateX(-101);
        stegoButton.setTranslateY(-101);
        stegoButton.setTranslateX(101);
        increaseBrontoButton.setTranslateX(-105);
        decreaseBrontoButton.setTranslateX(105);
        final Text text = new Text("type: ");
        text.setTranslateY(-73);
        // Setting text to button
        //stegoButton.setText("button");
        // Registering a handler for button
        stegoButton.setOnAction((ActionEvent event) -> {
            // Printing Hello World! to the console
            try {
                text.setText("type: " + String.valueOf(MessageServer.getInfo("stego","quantity")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        brontoButton.setOnAction((ActionEvent event) -> {

            try {
                text.setText("type: " + String.valueOf(MessageServer.getInfo("bronto","quantity")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        increaseBrontoButton.setOnAction((ActionEvent event) -> {

            try {
                MessageServer.increaseQuantity("bronto",5);
                text.setText(String.valueOf(MessageServer.getInfo("bronto","quantity")));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });

        decreaseBrontoButton.setOnAction((ActionEvent event) -> {

            try {
                MessageServer.increaseQuantity("bronto",-5);
                text.setText(String.valueOf(MessageServer.getInfo("bronto","quantity")));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });


        // Initializing the StackPane class
        final StackPane root = new StackPane();
        // Adding all the nodes to the StackPane
        root.getChildren().add(stegoButton);
        root.getChildren().add(brontoButton);
        root.getChildren().add(increaseBrontoButton);
        root.getChildren().add(text);
        root.getChildren().add(decreaseBrontoButton);

        // Creating a scene object
        final Scene scene = new Scene(root, 300, 250);
        // Adding the title to the window (primaryStage)
        primaryStage.setTitle("DinoManager");
        primaryStage.setScene(scene);
        // Show the window(primaryStage)
        primaryStage.show();
    }
    @Override
    public void stop() {
        //By default this does nothing
        //It runs if the user clicks the go-away button
        //closing the window or if Platform.exit() is called.
        //Use Platform.exit() instead of System.exit(0).
        //This is where you should offer to save any unsaved
        //stuff that the user may have generated.
    }

    public static void main(final String[] arguments) {
        launch(arguments);
    }
}
