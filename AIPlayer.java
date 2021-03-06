package tictactoe;

import java.util.ArrayList;
import java.util.Random;

public abstract class AIPlayer {

    protected String difficulty;
    protected char identifier;

    public AIPlayer(char identifier, String difficulty) {
        this.identifier = identifier;
        this.difficulty = difficulty;
    }

    public abstract int getTurn(char[][] gameboard);

    protected int getRandomMove(char[][] gameboard) {
        ArrayList<Integer> open = getOpenSpaces(gameboard);

        Random random = new Random();
        return open.get(random.nextInt(open.size()));
    }

    protected ArrayList<Integer> getOpenSpaces(char[][] gameboard) {
        ArrayList<Integer> open = new ArrayList<>();
        for (int i = 0; i < gameboard.length; i++) {
            for (int j = 0; j < gameboard.length; j++) {
                if (gameboard[i][j] == '_' || gameboard[i][j] == ' ') {
                    open.add(translateToPos(gameboard.length, i, j));
                }
            }
        }
        return open;
    }

    public int translateToPos(int boardLength, int row, int col) {
        return (boardLength - (row + 1)) * boardLength + (col + 1);
    }

    public String getDifficulty() {
        return difficulty;
    }
}
