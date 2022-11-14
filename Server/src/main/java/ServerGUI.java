import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ServerGUI extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Project 3 Server");


		Text title = new Text("Connect 4 Server");
		title.setStyle("-fx-fill: white");
		title.setFont(Font.font("Arial", 50));

		TextField portField = new TextField();
		portField.setPromptText("Port number");
		portField.setMaxWidth(90);
		portField.setStyle("-fx-background-color: #606060");

		Button startButton = new Button("Start server");
		startButton.setStyle("-fx-background-color: #606060;" +
				"-fx-text-fill: white");

		VBox root = new VBox(title, portField, startButton);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);
		scene.setFill(Color.web("0x3C3C3D"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
