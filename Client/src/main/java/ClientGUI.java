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
import javafx.scene.layout.HBox;
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

	Text playerLabel;

	Text playerStatus;


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
				closeApplication();
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
				if (client.getGameInfo().getPlayerWon() != 0) {
					stage.setScene(gameOverScene());
				}
				if (client.getGameInfo().getCol() != -1) {
					System.out.println("Received col from other client: " + client.getGameInfo().getCol());
					playChip(client.getGameInfo().getCol(), false);
					client.getGameInfo().setCol(-1);
				}
//				System.out.println("Received message on player " + client.getPlayerNum());
				playerLabel.setText("Player " + client.getPlayerNum());
				playerStatus.setText(client.getGameInfo().getStatus());
//				System.out.println("Received status: " + client.getGameInfo().getStatus());

			});
		}, ip, port);

		grid = new GameButton[6][7];
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				GameButton button = new GameButton(i, j);
				button.setStyle("-fx-background-color: #818181;" +
						"-fx-background-radius: 10");
				button.setPrefSize(40, 40);
				int finalI = i;
				int finalJ = j;
				button.setOnAction(e -> { // Once button on grid is pressed, this event is fired
					playChip(finalJ, true);
//					System.out.println("CLicking button " + finalI + " " + finalJ);
				});
				grid[i][j] = button;
			}
		}
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
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				game_grid.add(grid[i][j], j, i);
			}
		}

		playerLabel = new Text();
		playerLabel.setStyle("-fx-fill: white");
		playerLabel.setFont(Font.font("Arial", 35));

		playerStatus = new Text();
		playerStatus.setStyle("-fx-fill: white");
		playerStatus.setFont(Font.font("Arial", 30));

		VBox root = new VBox(playerLabel, game_grid, playerStatus);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);
		return scene;
	}

	public Scene gameOverScene() {
		Text gameOver = new Text("Game Over");
		gameOver.setStyle("-fx-fill: white");
		gameOver.setFont(Font.font("Arial", 50));

		Text winner = new Text();
		if (client.getGameInfo().getPlayerWon() == -1) {
			winner.setText("There was a tie!");
		} else {
			winner.setText("Player " + client.getGameInfo().getPlayerWon() + " wins!");
		}
		winner.setStyle("-fx-fill: white");
		winner.setFont(Font.font("Arial", 35));

		Button quitButton = new Button("Quit");
		quitButton.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");
		quitButton.setOnAction(e -> {
			closeApplication();
		});

		Button playAgainButton = new Button("Play Again");
		playAgainButton.setStyle("-fx-background-color: #606060;" + "-fx-text-fill: white");
		playAgainButton.setOnAction(e -> {
			stage.setScene(gameScene());
		});

		HBox options = new HBox(playAgainButton, quitButton);
		options.setAlignment(Pos.CENTER);
		options.setSpacing(10);
		VBox root = new VBox(gameOver, winner, options);
		root.setAlignment(Pos.CENTER);
		root.setSpacing(20);
		root.setStyle("-fx-background-color: #3C3C3D");

		Scene scene = new Scene(root, 700,600);
		return scene;
	}

	public void closeApplication() {
		System.out.println("Closing client!");
		Platform.exit();
		System.exit(0);
	}

	private void playChip(int col, boolean currentPlayersMove) {
		if (client.getGameInfo().isTurn()) {
			GameButton button = findEmptySpot(col);
			if (button != null) { // Found a valid spot to place chip
//				System.out.println("Playing col " + col);
				client.getGameInfo().setCol(col); // to tell other player what I played
				if (currentPlayersMove) {
					client.getGameInfo().recentMove = "Player " + client.getPlayerNum() + " placed a chip in column " + col;
					if (client.getPlayerNum() == 1) { // Change color of spot and move on to next player
						button.setColor("Purple");
						button.setStyle("-fx-background-color: #673AB7;" +
								"-fx-background-radius: 10");
					} else {
						button.setColor("Cyan");
						button.setStyle("-fx-background-color: #00908F;" +
								"-fx-background-radius: 10");
					}
					if (checkForWin()) {
						client.getGameInfo().setRecentMove("Player " + client.getPlayerNum() + " won!");
						client.getGameInfo().setPlayerWon(client.getPlayerNum());
					}

					if (checkForTie()) {
						System.out.println("There was a tie!");
						client.getGameInfo().setRecentMove("There was a tie!"); // might need to do this on server in order to preserve last move that was played because this is overriding the last move
						client.getGameInfo().setPlayerWon(-1);
					}
					client.send(client.getGameInfo());
				} else {
					if (client.getPlayerNum() == 1) { // Change color of spot and move on to next player
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
			} else {
				// Invalid play
			}
		} else {
			// not this players turn
			System.out.println("Not your turn, cannot play");
		}
	}

	private boolean checkForTie() {
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				GameButton button = grid[i][j];
				if (button.getColor() == "Gray") {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkForWin() {
		String colorTarget;
		if (client.getPlayerNum() == 1) {
			colorTarget = "Purple";
		} else {
			colorTarget = "Cyan";
		}

		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 7; j++){
				GameButton button = grid[i][j];
				if (button.getColor() == colorTarget) {
					if (checkVertical(colorTarget, i, j)) {
						System.out.println("Vertical win!");
						return true;
					}

					if (checkHorizontal(colorTarget, i, j)) {
						System.out.println("Horizontal win!");
						return true;
					}

					if (checkLeftDiagonal(colorTarget, i, j)) {
						System.out.println("Left diagonal win!");
						return true;
					}
					if (checkRightDiagonal(colorTarget, i, j)) {
						System.out.println("Right diagonal win!");
						return true;
					}
 				}
			}
		}
		return false;
	}

	private boolean checkVertical(String colorTarget, int row, int col) {
		int count = 0;
		GameButton button;
		while (row < 6) { // so we dont fall out of bounds
			button = grid[row][col];
			if (button.getColor() == colorTarget) {
				count++;
			} else {
				return false;
			}
			row++;
		}
		return count >= 4;
	}

	private boolean checkHorizontal(String colorTarget, int row, int col) {
		int count = 0;
		GameButton button;
		while (col < 7) { // so we dont fall out of bounds
			button = grid[row][col];
			if (button.getColor() == colorTarget) {
				count++;
			} else {
				return false;
			}
			col++;
		}
		return count >= 4;
	}

	private boolean checkLeftDiagonal(String colorTarget, int row, int col) {
		int count = 0;
		GameButton button;
		while (col >= 0 && col < 7 && row < 6) { // so we dont fall out of bounds
			button = grid[row][col];
			if (button.getColor() == colorTarget) {
				count++;
			} else {
				return false;
			}
			row++;
			col--;
		}
		return count >= 4;
	}

	private boolean checkRightDiagonal(String colorTarget, int row, int col) {
		int count = 0;
		GameButton button;
		while (col >= 0 && col < 7 && row < 6) { // so we dont fall out of bounds
			button = grid[row][col];
			if (button.getColor() == colorTarget) {
				count++;
			} else {
				return false;
			}
			row++;
			col++;
		}
		return count >= 4;
	}

	private GameButton findEmptySpot(int col) {
		System.out.println("Looking for spot in col " + col);
		int row = 0;

		while (row < 6) {
			GameButton button = grid[row][col];

			if (button.getColor() != "Gray") {
				return null;
			}
			if (row == 5 && button.getColor() == "Gray") {
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
