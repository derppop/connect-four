import javafx.application.Application;

import javafx.application.Platform;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;

import java.awt.*;

public class ClientGUI extends Application {
	Client client;

	Stage stage;

	GameButton[][] grid;

	int playerNum;

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

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { // Closes server when user closes server window
			@Override
			public void handle(WindowEvent t) {
				closeServer();
			}
		});
	}

	public Scene startScene() {
		Text title = new Text("Connect 4");
		title.setStyle("-fx-fill: white");
		title.setFont(Font.font("Arial", 50));

		TextField ipField = new TextField();
		ipField.setPromptText("IP Address");
		ipField.setMaxWidth(90);
		ipField.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");

		TextField portField = new TextField();
		portField.setPromptText("Port number");
		portField.setMaxWidth(90);
		portField.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");

		Button startButton = new Button("Play");
		startButton.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");
		startButton.setOnAction(e -> {
			initializeGame(ipField.getText(), Integer.parseInt(portField.getText()));
			startButton.setDisable(true);
		});

		VBox root = new VBox(title, ipField, portField, startButton);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);

		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				initializeGame(ipField.getText(), Integer.parseInt(portField.getText()));
				startButton.setDisable(true);
			}
		});
		scene.setFill(Color.web("0x3C3C3D"));
		return scene;
	}

	private void initializeGame(String ip, int port) {
		client = new Client(data -> {
			Platform.runLater(() -> {
				if (client.getGameInfo().getCol() != -1) {
					System.out.println(client.getGameInfo().getCol());
					playChip(client.getGameInfo().getCol(), false);
				}
			});
		}, ip, port);

		grid = new GameButton[7][6];
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 6; j++) {
				GameButton button = new GameButton(i, j);
				button.setStyle("-fx-background-color: #818181;" +
						"-fx-background-radius: 10");
				button.setPrefSize(40, 40);
				int finalI = i;
				int finalJ = j;
				button.setOnAction(e -> { // Once button on grid is pressed, this event is fired
					playChip(finalJ, true);
					System.out.println("CLicking button " + finalI + " " + finalJ);
				});
				grid[i][j] = button;

			}
		}
		playerNum = client.getPlayerNum();
		System.out.println("Changing scene");
		client.start();
		stage.setScene(gameScene());
	}

	public Scene gameScene() {
		GridPane game_grid = new GridPane();
		game_grid.setPadding(new Insets(10, 10, 10, 10));
		game_grid.setVgap(20);
		game_grid.setHgap(20);
		game_grid.setAlignment(Pos.CENTER);
		game_grid.setStyle("-fx-background-color: #606060;" +
				"-fx-background-radius: 15");
		game_grid.setMaxWidth(400);
		game_grid.setMaxHeight(400);
		for (int i = 0; i < 7; i++){
			for (int j = 0; j < 6; j++){
				game_grid.add(grid[i][j], j, i);
			}
		}

//		Text player_state = new Text(client.getGameInfo().getStatus());
//		player_state.setStyle("-fx-fill: white");
//		player_state.setFont(Font.font("Arial", 30));

		VBox root = new VBox(game_grid);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);
		return scene;
	}

//	public Scene gameOverScene() {
//		Text game_over = new Text("Game Over");
//		game_over.setStyle("-fx-fill: white");
//		game_over.setFont(Font.font("Arial", 50));
//		game_over.setText("Player " + client.getGameState().getPlayer() + "Wins!");
//
//		Text which_player_won = new Text("");
//		which_player_won.setStyle("-fx-fill: white");
//		which_player_won.setFont(Font.font("Arial", 50));
//		which_player_won.setText("text here..."); // didn't finish
//
//		Button playAgainButton = new Button("Play Again");
//		playAgainButton.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");
//		playAgainButton.setOnAction(e -> {
//			stage.setScene(gameScene());
//		});
//
//		Button quitButton = new Button("Quit");
//		quitButton.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");
//		quitButton.setOnAction(e -> {
//			Platform.exit();
//		});
//
//		VBox root = new VBox(game_over, which_player_won, playAgainButton, quitButton);
//		root.setAlignment(Pos.CENTER);
//		root.setSpacing(20);
//		root.setStyle("-fx-background-color: #3C3C3D");
//
//		Scene scene = new Scene(root, 700,600);
//		return scene;
//	}

	public void closeServer() {
		System.out.println("Closing client!");
		Platform.exit();
		System.exit(0);
	}

	private void playChip(int col, boolean currentPlayersMove) {

		GameButton button = findEmptySpot(col);
		if (button != null) { // Found a valid spot to place chip
			System.out.println("Playing col " + col);
			if (currentPlayersMove) {
				client.getGameInfo().recentMove = "Player " + playerNum + " placed a chip in column " + col;
				if (playerNum == 1) { // Change color of spot and move on to next player
					button.setColor("Purple");
					button.setStyle("-fx-background-color: #673AB7;" +
							"-fx-background-radius: 10");
				} else {
					button.setColor("Cyan");
					button.setStyle("-fx-background-color: #00908F;" +
							"-fx-background-radius: 10");
				}
			} else {
				if (playerNum == 1) { // Change color of spot and move on to next player
					button.setColor("Cyan");
					button.setStyle("-fx-background-color: #00908F;" +
							"-fx-background-radius: 10");
				} else {
					button.setColor("Purple");
					button.setStyle("-fx-background-color: #673AB7;" +
							"-fx-background-radius: 10");
				}
			}
			button.setDisable(true);
		}
	}

	private GameButton findEmptySpot(int col) {
		System.out.println("Looking for spot in col " + col);
		int row = 0;

		while (row < 7) {
			GameButton button = grid[row][col];

			if (button.getColor() != "Gray") {
				return null;
			}
			if (row == 6 && button.getColor() == "Gray") {
				return button;
			}
			if (grid[row+1][col].getColor() != "Gray") {
				return button;
			}
			row++;
		}
		// If this point is reached, entire column is full
		return null;
	}
}
