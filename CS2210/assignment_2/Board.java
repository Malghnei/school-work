public class Board implements BoardADT {
    // Instance variables
    private char[][] theBoard;
    private int board_size;
    private int empty_position;
    private int max_levels;

    /**
     * Constructor for Board class
     * @param board_size
     * @param empty_position
     * @param max_levels
     */
    public Board(int board_size, int empty_position, int max_levels) {
        this.board_size = board_size;
        this.empty_position = empty_position;
        this.max_levels = max_levels;

        theBoard = new char[board_size][board_size];

        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                theBoard[i][j] = 'E';
            }
        }
    }

    /**
     * Creates and returns a new HashDictionary with a prime number size .
     * Size 9973, less than 10000, is chosen to reduce collisions in the hash table.
     */
    public HashDictionary makeDictionary() {
        return new HashDictionary(9973);
    }

    /**
     * Checks if the current board layout has been seen before in the provided dictionary.
     * If found, returns the associated score; otherwise, returns -1.
     * @param dict The HashDictionary to check for repeated layouts.
     */
    public int repeatedLayout(HashDictionary dict) {
        String layoutString = this.boardToString();
        return dict.get(layoutString);
    }

    /**
     * Stores the current board layout and its associated score in the provided dictionary.
     * If the layout already exists, it will not be added again.
     * @param dict The HashDictionary to store the layout and score.
     * @param score The score associated with the current board layout (0, 1, 2, or 3).
     * @throws DictionaryException if the layout already exists in the dictionary.
     */
    public void storeLayout(HashDictionary dict, int score) {
        String layoutString = this.boardToString();
        Layout layout = new Layout(layoutString, score);
        try {
            dict.put(layout);
        } catch (DictionaryException e) {
            // Handle exception if needed
        }
    }

    /**
     * Saves a tile on the board at the specified position with the given symbol.
     * @param i Row index
     * @param j Column index
     * @param symbol 'X' for human, 'O' for computer, 'E' for empty
     */
    public void saveTile(int i, int j, char symbol) {
        theBoard[i][j] = symbol;
    }

    /**
     * Checks if the position at (row, column) is empty.
     * @param row Row index
     * @param column Column index
     * @return true if the position is empty ('E'), false otherwise
     */
    public boolean positionIsEmpty(int row, int column) {
        return theBoard[row][column] == 'E';
    }

    /**
     * Checks if the tile at (row, column) is occupied by the computer ('O').
     * @param row Row index
     * @param column Column index
     * @return true if the tile is occupied by the computer, false otherwise
     */
    public boolean isComputerTile(int row, int column) {
        return theBoard[row][column] == 'O';
    }

    /**
     * Checks if the tile at (row, column) is occupied by the human ('X').
     * @param row Row index
     * @param column Column index
     * @return true if the tile is occupied by the human, false otherwise
     */
    public boolean isHumanTile(int row, int column) {
        return theBoard[row][column] == 'X';
    }

    /**
     * Checks if the given player has won the game.
     * @param symbol 'X' for human, 'O' for computer
     * @return true if the player has won, false otherwise
     */
    public boolean winner(char symbol) {
        // Check rows and columns
        for (int i = 0; i < board_size; i++) {
            boolean rowWin = true;
            boolean colWin = true;
            for (int j = 0; j < board_size; j++) {
                if (theBoard[i][j] != symbol) {
                    rowWin = false;
                }
                if (theBoard[j][i] != symbol) {
                    colWin = false;
                }
            }
            if (rowWin || colWin) {
                return true;
            }
        }

        // Check diagonals
        boolean diag1Win = true;
        boolean diag2Win = true;
        for (int i = 0; i < board_size; i++) {
            if (theBoard[i][i] != symbol) {
                diag1Win = false;
            }
            if (theBoard[i][board_size - 1 - i] != symbol) {        
                diag2Win = false;
            }
        }

        return diag1Win || diag2Win;
    }

    /**
     * Checks if the game is a draw.
     * A draw is defined as no player has won and the number of empty positions
     * is less than minEmpty.
     * @param player 'X' for human, 'O' for computer
     * @param minEmpty Minimum number of empty positions to avoid a draw
     * @return true if the game is a draw, false otherwise
     */
    public boolean isDraw(char player, int empty_position) {
        char opponent = (player == 'X') ? 'O' : 'X';

        if (winner(player) || winner(opponent)) {
            return false; // A player has won
        }

        int emptyCount = 0;
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (theBoard[i][j] == 'E') {
                    emptyCount++;
                }
            }
        }

        return emptyCount < empty_position;
    }

    /**
     * Evaluates the current board state for the given player.
     * Returns:
     * 3 if the player has won,
     * 0 if the opponent has won,
     * 1 if the game is a draw,
     * 2 if the game is not finished.
     * @param player 'X' for human, 'O' for computer
     * @param minEmpty Minimum number of empty positions to avoid a draw
     * @return The evaluation score (0, 1, 2, or 3
     */
    public int evaluate(char player, int minEmpty) {
        char opponent = (player == 'X') ? 'O' : 'X';

        if (winner(player)) {
            return 3; // Win
        } else if (winner(opponent)) {
            return 0; // Loss
        } else if (isDraw(player, minEmpty)) {
            return 1; // Draw
        } else {
            return 2; // Game not finished
        }
    }
    
    /**
     * Helper method that converts the current board layout to a string representation.
     * @return The string representation of the board layout
     */
    private String boardToString() {
        String boardString = "";
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                boardString += theBoard[i][j];
            }
        }
        return boardString;
    }
}
