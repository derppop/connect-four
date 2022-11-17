import javafx.scene.control.Button;

public class GameButton extends Button {
    private String color;
    int col;
    int row;

    GameButton(String color, int row, int col) {
        this.color = color;
        this.col = col;
        this.row = row;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
