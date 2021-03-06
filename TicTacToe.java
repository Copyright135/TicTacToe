package tictactoe;

import java.util.Arrays;

public class TicTacToe {

    private boolean gameOver;
    private boolean xTurn;
    private int emptySpaces;
    private char[][] gameboard;
    private int inARowToWin;
    private String result;

    public TicTacToe() {
    }

    public void createInitializedBoard(char[] input) {
        gameboard = new char[3][3];
        this.inARowToWin = 3;
        emptySpaces = 0;
        for (int i = 0; i < input.length; i++) {
            char ch = input[i];
            if (ch == '_' || ch == ' ') {
                emptySpaces++;
            }
            gameboard[i / 3][i % 3] = ch;
        }
        xTurn = (gameboard.length * gameboard.length - emptySpaces) % 2 == 0 ? true : false;

        boolean winner = checkAllWinConditions();
        gameOver = winner;
        if (!winner && emptySpaces == 0) {
            gameOver = true;
            result = "Draw";
        }
    }

    /*
     * Create the board matrix
     */
    public void createBoard() {
        createBoard(3);
    }

    /*
     * Create a connect three board with a custom size and three connected to win
     */
    public void createBoard(int customSize) {
        createBoard(customSize, 3);
    }

    /*
     * Create a custom size board with a custom defined amount to connect to win
     */
    public void createBoard(int customSize, int inARowToWin) {
        this.gameOver = false;
        this.xTurn = true;

        if (customSize > 0 && inARowToWin > 0) {
            this.inARowToWin = inARowToWin;

            gameboard = new char[customSize][customSize];
            emptySpaces = customSize * customSize;
            for (char[] chars : gameboard) {
                Arrays.fill(chars, '_');
            }

            printGameboard();
        } else {
            createBoard(3, 3);
        }
    }

    /*
     * Handles the code while the game is in progress
     */
    public boolean move(int inputX, int inputY) {
        if (!gameOver) {
            int coordX;
            int coordY;

            /* Verify that the input is within the game's boundaries */
            if (inputX > 0 && inputX <= gameboard.length && inputY > 0 && inputY <= gameboard.length) {

                coordX = inputX - 1;
                coordY = gameboard.length - inputY;

                /* Commit change to board if spot is empty */
                if (gameboard[coordY][coordX] == '_' || gameboard[coordY][coordX] == ' ') {
                    gameboard[coordY][coordX] = xTurn ? 'X' : 'O';

                    emptySpaces--;

                    /* Check for a win based on the previously played spot */
                    if (isWinCondition(coordY, coordX)) {
                        result = xTurn ? "X wins" : "O wins";
                        gameOver = true;
                    } else if (emptySpaces == 0) {
                        result = "Draw";
                        gameOver = true;
                    }

                    xTurn = !xTurn;

                    return true;

                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            } else {
                System.out.println(String.format("Coordinates should be from 1 to %s!", gameboard.length));
            }
        }
        return false;
    }

    private boolean checkAllWinConditions() {
        boolean winCondition = false;

        for (int i = 0; i < gameboard.length; i++) {
            winCondition = winCondition || isWinCondition(i, i);
            if (winCondition) {
                result = gameboard[i][i] == 'X' ? "X wins" : "O wins";
                break;
            }
        }
        return winCondition;
    }

    private boolean isWinCondition(int y, int x) {
        boolean winCondition;

        winCondition = checkRow(y, x) || checkCol(y, x);

        if (gameboard[y][x] != '_' && (inARowToWin < gameboard.length || y - x == 0 || gameboard.length - 1 - y - x == 0)) {
            winCondition = checkDiagonal(y, x) || winCondition;
        }

        return winCondition;
    }

    /*
     * This method checks the played horizontal row for a win condition
     */
    private boolean checkRow(int y, int x) {

        int count = 0;

        if (gameboard[y][x] != '_') {
            for (int i = 0; i < gameboard.length; i++) {
                if (x - i >= 0 && gameboard[y][x] == gameboard[y][x - i]) {
                    count++;
                } else {
                    break;
                }
            }

            for (int i = 0; i < gameboard.length; i++) {
                if (x + i < gameboard.length && gameboard[y][x] == gameboard[y][x + i]) {
                    count++;
                } else {
                    break;
                }
            }
        }

        return count > inARowToWin;
    }


    /*
     * This method checks the played vertical column for a win condition
     */
    private boolean checkCol(int y, int x) {
        int count = 0;

        if (gameboard[y][x] != '_') {
            for (int i = 0; i < gameboard.length; i++) {
                if (y - i >= 0 && gameboard[y][x] == gameboard[y - i][x]) {
                    count++;
                } else {
                    break;
                }
            }

            for (int i = 0; i < gameboard.length; i++) {
                if (y + i < gameboard.length && gameboard[y][x] == gameboard[y + i][x]) {
                    count++;
                } else {
                    break;
                }
            }
        }

        return count > inARowToWin;
    }

    /*
     * Checks diagonal for a win condition
     */
    private boolean checkDiagonal(int y, int x) {
        boolean desWin = false;
        boolean ascWin = false;
        int count;

        if (inARowToWin < gameboard.length || y - x == 0) {
            count = 1;

            for (int i = 1; i < gameboard.length; i++) {
                if (y - i >= 0 && x - i >= 0 && gameboard[y][x] == gameboard[y - i][x - i]) {
                    count++;
                } else {
                    break;
                }
            }

            for (int i = 1; i < gameboard.length; i++) {
                if (y + i < gameboard.length && x + i < gameboard.length
                        && gameboard[y][x] == gameboard[y + i][x + i]) {
                    count++;
                } else {
                    break;
                }
            }
            desWin = count >= inARowToWin;
        }

        /* check bottom left to top right diagonal win */
        if (inARowToWin < gameboard.length || gameboard.length - 1 - y - x == 0) {
            count = 1;

            for (int i = 1; i < gameboard.length; i++) {
                if (y + i < gameboard.length && x - i >= 0 && gameboard[y][x] == gameboard[y + i][x - i]) {
                    count++;
                } else {
                    break;
                }
            }

            for (int i = 1; i < gameboard.length; i++) {
                if (y - i >= 0 && x + i < gameboard.length && gameboard[y][x] == gameboard[y - i][x + i]) {
                    count++;
                } else {
                    break;
                }
            }

            ascWin = count >= inARowToWin;
        }

        return desWin || ascWin;
    }


    /*
     * Prints game board
     */
    public void printGameboard() {
        /* Determines the size of the header and footer dynamically with board size */
        StringBuilder header = new StringBuilder("---");
        header.append("--".repeat(gameboard.length));

        System.out.println(header);

        for (char[] chars : gameboard) { // print out the gameboard
            System.out.print("| ");

            for (char ch : chars) {
                System.out.print(ch + " ");
            }
            System.out.println("|");
        }

        System.out.println(header);
    }

    public char[][] getGameboard() {
        return gameboard;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }

    public boolean isXTurn() { return xTurn; }

    public String getResult() {
        return result;
    }
}
