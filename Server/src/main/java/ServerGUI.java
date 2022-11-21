import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.HashMap;

//CALLBACK.ACCEPT FOR SERVER CLASS PRINTS PARAMETER TO LOG OF SERVER GUI
//TODO: WRITE WINNER CHECK FUNCTION, FINISH IMPLEMENTING CLIENT THREAD CLASS

public class ServerGUI extends Application {
	Server server;
	Stage stage;
	ListView<String> log;
	HashMap<String, Scene> sceneMap;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		sceneMap = new HashMap<String, Scene>();

		stage = primaryStage;
		primaryStage.setTitle("Project 3 Server");
		primaryStage.setScene(startScene());
		primaryStage.show();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { // Closes server when user closes server window
			@Override
			public void handle(WindowEvent t) {
				closeServer();
			}
		});
	}

	public Scene startScene() {
		Text title = new Text("Connect 4 Server");
		title.setStyle("-fx-fill: white");
		title.setFont(Font.font("Arial", 50));

		TextField portField = new TextField();
		portField.setPromptText("Port number");
		portField.setMaxWidth(90);
		portField.setStyle("-fx-background-color: #606060;" +
				"-fx-text-fill: white");

		Button startButton = new Button("Start server");
		startButton.setStyle("-fx-background-color: #606060;" +
				"-fx-text-fill: white");
		startButton.setOnAction(e -> {
			initializeServer(portField.getText());
		});


		VBox root = new VBox(title, portField, startButton);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);

		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				initializeServer(portField.getText());
			}
		});
		scene.setFill(Color.web("0x3C3C3D"));
		return scene;
	}

	public Scene mainScene() {
		Text numOfClients = new Text("Clients connected: " + server.getNumOfClients());
		log = new ListView<String>();
		log.setStyle("-fx-background-color: #3C3C3D");
		VBox root = new VBox(numOfClients, log);
		root.setStyle("-fx-background-color: #3C3C3D");
		Scene scene = new Scene(root, 700, 600);
		return scene;
	}

	public void initializeServer(String port) {
		server = new Server(data -> {
			Platform.runLater(() -> {
				log.getItems().add(data.toString());
			});
		}, Integer.parseInt(port));
		System.out.println("changing scene");
		stage.setScene(mainScene());
	}

	public void closeServer() {
		System.out.println("Closing server!");
		Platform.exit();
		System.exit(0);
	}
}
