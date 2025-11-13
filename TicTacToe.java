public class TicTacToe {
    // 盤面：3x3 陣列
    private final char[][] board;
    // 當前玩家：'X' 或 'O'
    private char currentPlayer;
    // 遊戲狀態：RUNNING, X_WINS, O_WINS, DRAW
    private GameStatus status;
    // 已下棋步數
    private int moves;

    /**
     * 遊戲狀態列舉
     */
    public enum GameStatus {
        RUNNING, X_WINS, O_WINS, DRAW
    }

    /**
     * 建構子：初始化盤面和狀態
     */
    public TicTacToe() {
        board = new char[3][3];
        // 初始設定盤面為空
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        currentPlayer = 'X'; // X 先手
        status = GameStatus.RUNNING;
        moves = 0;
    }

    /**
     * 玩家下棋方法 (Set Method)
     * @param row 0-2 之間，表示行
     * @param col 0-2 之間，表示列
     * @return true 如果下棋成功，false 如果位置已被佔用或遊戲已結束
     */
    public boolean set(int row, int col) {
        if (status != GameStatus.RUNNING) {
            return false; // 遊戲已結束
        }
        if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != ' ') {
            return false; // 位置無效或已被佔用
        }

        board[row][col] = currentPlayer;
        moves++;

        // 檢查遊戲狀態並更新
        evaluate();
        
        // 只有在遊戲仍在進行中時，才切換玩家
        if (status == GameStatus.RUNNING) {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        return true;
    }

    /**
     * 判斷遊戲狀態並更新內部狀態 (Evaluate Method)
     */
    public void evaluate() {
        // 檢查行、列、對角線是否有獲勝者
        if (checkWin(currentPlayer)) {
            status = (currentPlayer == 'X') ? GameStatus.X_WINS : GameStatus.O_WINS;
            return;
        }

        // 檢查是否平手 (所有位置都滿了)
        if (moves == 9) {
            status = GameStatus.DRAW;
            return;
        }
        
        // 否則，遊戲仍在進行
        status = GameStatus.RUNNING;
    }

    /**
     * 輔助方法：檢查是否有獲勝三連
     * @param player 當前玩家的棋子 ('X' 或 'O')
     * @return true 如果該玩家獲勝
     */
    private boolean checkWin(char player) {
        // 檢查三行
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
        }
        // 檢查三列
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) return true;
        }
        // 檢查對角線 (左上到右下)
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        // 檢查反對角線 (右上到左下)
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;

        return false;
    }

    // --- Getter 方法，用於測試 ---
    
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }
    
    public char getBoardCell(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
            return board[row][col];
        }
        return ' ';
    }
}