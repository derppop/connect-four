import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientGUI extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		primaryStage.setScene(startScene());
		primaryStage.show();
	}

	public Scene startScene() {
		Text title = new Text("Connect 4");
		title.setStyle("-fx-fill: white");
		title.setFont(Font.font("Arial", 50));

		TextField portField = new TextField();
		portField.setPromptText("Port number");
		portField.setMaxWidth(90);
		portField.setStyle("-fx-background-color: #606060;" +
				"-fx-text-fill: white");

		TextField ipField = new TextField();
		ipField.setPromptText("IP Address");
		ipField.setMaxWidth(90);
		ipField.setStyle("-fx-background-color: #606060;" +
				"-fx-text-fill: white");

		Button startButton = new Button("Play");
		startButton.setStyle("-fx-background-color: #606060;" +
				"-fx-text-fill: white");
		startButton.setOnAction(e -> {
		});

		VBox root = new VBox(title, portField, ipField, startButton);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);

		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
			}
		});
		scene.setFill(Color.web("0x3C3C3D"));
		return scene;
	}

}