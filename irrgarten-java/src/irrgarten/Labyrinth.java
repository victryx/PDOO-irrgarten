package irrgarten;

import java.util.Arrays;

/**
 *
 * @author vik
 */
public class Labyrinth {

    private static final char BLOCK_CHAR = 'X';
    private static final char EMPTY_CHAR = '-';
    private static final char MONSTER_CHAR = 'M';
    private static final char COMBAT_CHAR = 'C';
    private static final char EXIT_CHAR = 'E';
    private static final int ROW = 0;
    private static final int COL = 1;

    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;

    private Monster[][] monsters;
    private char[][] labyrinth;
    private Player[][] players;

    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol) {
        this.nRows = nRows;
        this.nCols = nCols;
        this.exitRow = exitRow;
        this.exitCol = exitCol;

        monsters = new Monster[nRows][nCols];
        labyrinth = new char[nRows][nCols];
        players = new Player[nRows][nCols];

        for (int i = 0; i < nRows; i++) {
            // Arrays.fill(monsters[i], null); // Esto no hace falta (ya son null)
            Arrays.fill(labyrinth[i], EMPTY_CHAR);
        }

        labyrinth[exitRow][exitCol] = EXIT_CHAR;
    }

    public void spreadPlayers(Player[] players) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean haveAWinner() {
        return players[exitRow][exitCol] != null;
    }

    // TODO: creo que aqui hay que imprimirlo como tabla
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Labyrinth{");
        sb.append("nRows=").append(nRows);
        sb.append(", nCols=").append(nCols);
        sb.append(", exitRow=").append(exitRow);
        sb.append(", exitCol=").append(exitCol);
        sb.append(", monsters=").append(monsters);
        sb.append(", labyrinth=").append(labyrinth);
        sb.append(", players=").append(players);
        sb.append('}');
        return sb.toString();
    }

    public void addMonster(int row, int col, Monster monster) {
        if (posOK(row, col) && emptyPos(row, col)) {
            monsters[row][col] = monster;
            monster.setPos(row, col);
        }
    }

    public Monster putPlayer(Directions direction, Player player) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Directions[] validMoves(int row, int col) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private boolean posOK(int row, int col) {
        return 0 <= row && 0 <= col && nRows > row && nCols > col;
    }

    private boolean emptyPos(int row, int col) {
        return labyrinth[row][col] == EMPTY_CHAR;
    }

    private boolean monsterPos(int row, int col) {
        return labyrinth[row][col] == MONSTER_CHAR;
    }

    private boolean exitPos(int row, int col) {
        return labyrinth[row][col] == EXIT_CHAR;
    }

    private boolean combatPos(int row, int col) {
        return labyrinth[row][col] == COMBAT_CHAR;
    }

    private boolean canStepOn(int row, int col) {
        return posOK(row, col)
                && labyrinth[row][col] != COMBAT_CHAR
                && labyrinth[row][col] != BLOCK_CHAR;
    }

    private void updateOldPos(int row, int col) {
        if (posOK(row, col)) {
            labyrinth[row][col] = combatPos(row, col) ? MONSTER_CHAR : EMPTY_CHAR;
        }
    }

    private int[] dir2Pos(int row, int col, Directions direction) {
        int[] out = {row, col};
        
        switch (direction) {
            case DOWN  -> out[ROW]--;
            case UP    -> out[ROW]++;
            case LEFT  -> out[COL]--;
            case RIGHT -> out[COL]++;
        }
                
        return out;
    }
    
    private int[] randomEmptyPos() {
        int row, col;
        do {
            row = Dice.randomPos(nRows);
            col = Dice.randomPos(nCols);
        } while (!emptyPos(row, col));
        return new int[]{row, col};
    }
    
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
