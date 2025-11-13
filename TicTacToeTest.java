import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTest {

    private TicTacToe game;

    // 在每個測試方法執行前都會執行此方法，確保測試是獨立的
    @BeforeEach
    void setUp() {
        game = new TicTacToe();
    }

    @Test
    void testInitialState() {
        // 檢查初始狀態：盤面清空，X 先手，狀態為 RUNNING
        assertEquals(TicTacToe.GameStatus.RUNNING, game.getStatus());
        assertEquals('X', game.getCurrentPlayer());
        assertEquals(' ', game.getBoardCell(1, 1));
    }

    @Test
    void testSetValidMoveAndPlayerSwitch() {
        // X 下棋成功
        assertTrue(game.set(0, 0));
        assertEquals('X', game.getBoardCell(0, 0));
        // 玩家切換到 O
        assertEquals('O', game.getCurrentPlayer());

        // O 下棋成功
        assertTrue(game.set(1, 1));
        assertEquals('O', game.getBoardCell(1, 1));
        // 玩家切換回 X
        assertEquals('X', game.getCurrentPlayer());
    }

    @Test
    void testSetInvalidMove() {
        // X 下棋成功
        game.set(0, 0); 
        // 嘗試在同一位置再次下棋，應該失敗
        assertFalse(game.set(0, 0));
        // 嘗試在超出範圍的位置下棋，應該失敗 (假設 set 內部會處理)
        assertFalse(game.set(3, 3)); 
        // 玩家不應該改變 (仍為 O)
        assertEquals('O', game.getCurrentPlayer());
    }
    
    @Test
    void testXWinsByRow() {
        // X 下第一行
        game.set(0, 0); // X
        game.set(1, 0); // O
        game.set(0, 1); // X
        game.set(1, 1); // O
        game.set(0, 2); // X -> X wins
        
        // 檢查狀態
        assertEquals(TicTacToe.GameStatus.X_WINS, game.getStatus());
        // 檢查遊戲結束後無法繼續下棋
        assertFalse(game.set(2, 2));
    }

    @Test
    void testOWinsByColumn() {
        // 讓 O 贏第二列
        game.set(0, 0); // X
        game.set(0, 1); // O
        game.set(1, 0); // X
        game.set(1, 1); // O
        game.set(2, 2); // X
        game.set(2, 1); // O -> O wins
        
        // 檢查狀態
        assertEquals(TicTacToe.GameStatus.O_WINS, game.getStatus());
    }

    @Test
    void testDrawGame() {
        // 進行一場平局 (九步下滿)
        game.set(0, 0); // X
        game.set(1, 1); // O
        game.set(0, 1); // X
        game.set(0, 2); // O
        game.set(2, 0); // X
        game.set(1, 0); // O
        game.set(1, 2); // X
        game.set(2, 2); // O
        game.set(2, 1); // X -> DRAW
        
        // 檢查狀態
        assertEquals(TicTacToe.GameStatus.DRAW, game.getStatus());
    }
}