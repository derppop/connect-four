import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientGUI extends Application {
	Client client;

	Stage stage;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stage = primaryStage;
		primaryStage.setTitle("Project 3 Client");
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
		portField.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");

		TextField ipField = new TextField();
		ipField.setPromptText("IP Address");
		ipField.setMaxWidth(90);
		ipField.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");

		Button startButton = new Button("Play");
		startButton.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");
		startButton.setOnAction(e -> {
			initializeGame(ipField.getText(), Integer.parseInt(portField.getText()));
		});

		VBox root = new VBox(title, portField, ipField, startButton);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);

		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				initializeGame(ipField.getText(), Integer.parseInt(portField.getText()));
			}
		});
		scene.setFill(Color.web("0x3C3C3D"));
		return scene;
	}
	private void initializeGame(String ip, int port) {
		client = new Client(data -> {
			Platform.runLater(() -> {
				// IMPLEMENT CLIENT CALLBACK.ACCEPT
			});
		}, ip, port);
		System.out.println("Changing scene");
		client.start();
		stage.setScene(gameScene());
	}

	public Scene gameScene() {
		GameButton[][] grid = client.getGameState().getGrid();

		GridPane game_grid = new GridPane();
		game_grid.setMinSize(350, 300);
		game_grid.setPadding(new Insets(10, 10, 10, 10));
		game_grid.setVgap(5);
		game_grid.setHgap(5);
		game_grid.setAlignment(Pos.CENTER);
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				game_grid.setRowIndex(grid[i][j], i);
				game_grid.setColumnIndex(grid[i][j], j);
			}
		}

		Text player_state = new Text("");
		player_state.setStyle("-fx-fill: white");
		player_state.setFont(Font.font("Arial", 30));
		player_state.setText("Player " + client.getGameState().getPlayer() + "'s turn!");

		VBox root = new VBox(game_grid);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);
		return scene;
	}

	public Scene gameOverScene() {
		Text game_over = new Text("Game Over");
		game_over.setStyle("-fx-fill: white");
		game_over.setFont(Font.font("Arial", 50));
		game_over.setText("Player " + client.getGameState().getPlayer() + "Wins!");

		Text which_player_won = new Text("");
		which_player_won.setStyle("-fx-fill: white");
		which_player_won.setFont(Font.font("Arial", 50));
		which_player_won.setText("text here..."); // didn't finish

		Button playAgainButton = new Button("Play Again");
		playAgainButton.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");
		playAgainButton.setOnAction(e -> {
			stage.setScene(gameScene());
		});

		Button quitButton = new Button("Quit");
		quitButton.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");
		quitButton.setOnAction(e -> {
			Platform.exit();
		});

		VBox root = new VBox(game_over, which_player_won, playAgainButton, quitButton);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);
		return scene;
	}
}
