import java.io.Serializable;
import java.util.Objects;

// No player : Gray
// Player 1 : Purple
// Player 2 : Cyan
public class CFourInfo implements Serializable {

    private GameButton[][] grid;

    private int player = 1;

    private String recentMove;

    public CFourInfo() {
        grid = new GameButton[6][7];
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                GameButton button = new GameButton("Gray", i, j);
                int finalJ = j;
                button.setOnAction(e -> { // Once button on grid is pressed, this event is fired
                    playChip(finalJ);
                });
                grid[i][j] = button;
            }
        }
    }

    private GameButton findEmptySpot(int col) {
        int row = 0;

        while (row < 6) {
            if (grid[row+1][col].getColor() == "Grey") { // Check if there is a valid slot below
                row++;
            } else {
                return grid[row][col]; // On the valid slot
            }
        }
        // If this point is reached, entire column is full
        return null;
    }

    private void playChip(int col) {
        GameButton button = findEmptySpot(col);
        if (button != null) { // Found a valid spot to place chip
            recentMove = "Player " + player + " placed a chip in column " + col;
            if (player == 1) { // Change color of spot and move on to next player
                button.setColor("Purple");
                player = 2;
            } else {
                button.setColor("Cyan");
                player = 1;
            }
        }
    }

    public GameButton[][] getGrid() {
        return grid;
    }

    public String getRecentMove() {
        return recentMove;
    }

    public int getPlayer() {
        return player;
    }

    public boolean checkIfPlayerWon() {
        GameButton curButton = grid[0][0];
        // fucked

        return false;
    }
}
