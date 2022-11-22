import javafx.scene.control.Button;

public class GameButton extends Button {
    private String color = "Gray";
    int col;
    int row;

    GameButton(int row, int col) {
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
