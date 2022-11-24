import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	@Test
	void test() {
		fail("Not yet implemented");
	}

	@Test
	void InitalizedCFourInfo(){
		CFourInfo cfour = new CFourInfo();
	}

	@Test
	void InitalizedCFourInfoisPlayer(){
		CFourInfo cfour = new CFourInfo();
		boolean isPlayer1 = cfour.isPlayer1();
		assertEquals(false, isPlayer1, "Values aren't the same!");
	}

	@Test
	void UpdatedCFourInfoisPlayer(){
		CFourInfo cfour = new CFourInfo();
		cfour.setPlayer1(true);
		boolean isPlayer1 = cfour.isPlayer1();
		assertEquals(true, isPlayer1, "Values aren't the same!");
	}

	@Test
	void InitalizedCFourInfoisPlayer2() {
		CFourInfo cfour = new CFourInfo();
		boolean isPlayer2 = cfour.isPlayer2();
		assertEquals(false, isPlayer2, "Values aren't the same!");
	}

	@Test
	void UpdatedCFourInfoisPlayer2() {
		CFourInfo cfour = new CFourInfo();
		cfour.setPlayer2(true);
		boolean isPlayer2 = cfour.isPlayer2();
		assertEquals(true, isPlayer2, "Values aren't the same!");
	}

	@Test
	void InitalizedCFourInfoColumn() {
		CFourInfo cfour = new CFourInfo();
		int getColumn = cfour.getCol();
		assertEquals(-1, getColumn, "Values aren't the same!");
	}

	@Test
	void UpdatedCFourInfoColumn() {
		CFourInfo cfour = new CFourInfo();
		cfour.setCol(0);
		int getColumn = cfour.getCol();
		assertEquals(0, getColumn, "Values aren't the same!");
	}

	@Test
	void InitalizedCFourInfoMove() {
		CFourInfo cfour = new CFourInfo();
		String move = cfour.getRecentMove();
		assertEquals("", move, "Values aren't the same!");
	}

	@Test
	void UpdatedCFourInfoMove() {
		CFourInfo cfour = new CFourInfo();
		cfour.setRecentMove("Chip placed at column 1");
		String move = cfour.getRecentMove();
		assertEquals("Chip placed at column 1", move, "Values aren't the same!");
	}

	@Test
	void InitalizedCFourInfoStatus() {
		CFourInfo cfour = new CFourInfo();
		String status = cfour.getStatus();
		assertEquals("", status, "Values aren't the same!");
	}

	@Test
	void UpdatedCFourInfoStatus() {
		CFourInfo cfour = new CFourInfo();
		cfour.setRecentMove("Player 1's turn");
		String status = cfour.getStatus();
		assertEquals("Player 1's turn", status, "Values aren't the same!");
	}

	@Test
	void InitalizedCFourInfoPlayerNum() {
		CFourInfo cfour = new CFourInfo();
		int playerNum = cfour.getPlayerNum();
		assertEquals(0, playerNum, "Values aren't the same!");
	}

	@Test
	void UpdatedCFourInfoPlayerNum() {
		CFourInfo cfour = new CFourInfo();
		cfour.setPlayerNum(1);
		int playerNum = cfour.getPlayerNum();
		assertEquals(1, playerNum, "Values aren't the same!");
	}

	@Test
	void InitalizedGameButtonColor() {
		GameButton gameButton = new GameButton(0, 0);
		String color = gameButton.getColor();
		assertEquals("Gray", color, "Values aren't the same!");
	}

	@Test
	void UpdatedGameButtonColor() {
		GameButton gameButton = new GameButton(0, 0);
		gameButton.setColor("Red");
		String color = gameButton.getColor();
		assertEquals("Red", color, "Values aren't the same!");
	}

	@Test
	void InitalizedGameButtonCol() {
		GameButton gameButton = new GameButton(0, 0);
		int column = gameButton.getCol();
		assertEquals(0, column, "Values aren't the same!");
	}

	@Test
	void UpdatedGameButtonCol() {
		GameButton gameButton = new GameButton(0, 0);
		gameButton.setCol(1);
		int column = gameButton.getCol();
		assertEquals(1, column, "Values aren't the same!");
	}

	@Test
	void InitalizedGameButtonRow() {
		GameButton gameButton = new GameButton(0, 0);
		int row = gameButton.getRow();
		assertEquals(0, row, "Values aren't the same!");
	}

	@Test
	void UpdatedGameButtonRow() {
		GameButton gameButton = new GameButton(0, 0);
		gameButton.setRow(1);
		int row = gameButton.getRow();
		assertEquals(1, row, "Values aren't the same!");
	}
}