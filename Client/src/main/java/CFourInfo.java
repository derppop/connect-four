import java.io.Serializable;
import java.util.Objects;

// No player : Gray
// Player 1 : Purple
// Player 2 : Cyan
public class CFourInfo implements Serializable {
    boolean Player1 = false; // tells player if there is a player 1
    boolean Player2 = false; // tells the player if there is a player 2

    boolean turn = false; // tells the player if it's their turn

    int col = -1; // tells the player where other player placed chip in order to modify their own screen

    String recentMove;

    String status;

    int playerNum;

    public boolean isPlayer1() {
        return Player1;
    }

    public void setPlayer1(boolean player1) {
        Player1 = player1;
    }

    public boolean isPlayer2() {
        return Player2;
    }

    public void setPlayer2(boolean player2) {
        Player2 = player2;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public String getRecentMove() {
        return recentMove;
    }

    public void setRecentMove(String recentMove) {
        this.recentMove = recentMove;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
}
