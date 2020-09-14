import javafx.application.Application;
import javafx.event.ActionEvent;
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
        Button decreaseQuantityButton = new Button("-5");
        Button increaseQuantityButton = new Button("+5");

        brontoButton.setTranslateY(-101);
        brontoButton.setTranslateX(-101);
        stegoButton.setTranslateY(-101);
        stegoButton.setTranslateX(101);
        decreaseQuantityButton.setTranslateX(-105);
        increaseQuantityButton.setTranslateX(105);

        final Text typeText = new Text();
        final Text infoText = new Text();
        typeText.setTranslateY(-73);
        typeText.setTranslateX(-23);
        infoText.setTranslateY(-73);
        infoText.setTranslateX(23);
        stegoButton.setOnAction((ActionEvent event) -> {
            try {
                typeText.setText("stego");
                infoText.setText(": " + (DinoServer.getInfo("stego","quantity")).get(1));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        brontoButton.setOnAction((ActionEvent event) -> {

            try {
                typeText.setText("bronto");
                infoText.setText(": " + (DinoServer.getInfo("bronto","quantity")).get(1));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        increaseQuantityButton.setOnAction((ActionEvent event) -> {

            try {
                DinoServer.increaseQuantity(typeText.getText(),5);
                infoText.setText(": " + (DinoServer.getInfo(typeText.getText(),"quantity").get(1)));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });

        decreaseQuantityButton.setOnAction((ActionEvent event) -> {

            try {
                DinoServer.increaseQuantity(typeText.getText(),-5);
                infoText.setText(": " + (DinoServer.getInfo(typeText.getText(),"quantity").get(1)));
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        });


        // Initializing the StackPane class
        final StackPane root = new StackPane();
        // Adding all the nodes to the StackPane
        root.getChildren().add(stegoButton);
        root.getChildren().add(brontoButton);
        root.getChildren().add(increaseQuantityButton);
        root.getChildren().add(typeText);
        root.getChildren().add(decreaseQuantityButton);
        root.getChildren().add(infoText);

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
