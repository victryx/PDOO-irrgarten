package irrgarten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            Arrays.fill(labyrinth[i], EMPTY_CHAR);
        }

        labyrinth[exitRow][exitCol] = EXIT_CHAR;
    }

    public void spreadPlayers(Player[] players) {
        for (Player player : players) {
            int[] pos = randomEmptyPos();
            putPlayer2D(-1, -1, pos[ROW], pos[COL], player);       
        }
    }

    public boolean haveAWinner() {
        return players[exitRow][exitCol] != null;
    }

    // TODO: creo que aqui hay que imprimirlo como tabla
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                sb.append(labyrinth[i][j]);
                sb.append(" ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public void addMonster(int row, int col, Monster monster) {
        if (posOK(row, col) && emptyPos(row, col)) {
            monsters[row][col] = monster;
            monster.setPos(row, col);
        }
    }

    public Monster putPlayer(Directions direction, Player player) {
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        int[] newPos = dir2Pos(oldRow, oldCol, direction);
        return putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        int incRow, incCol;
        if (orientation == Orientation.VERTICAL) {
            incRow = 1;
            incCol = 0;
        } else {
            incRow = 0;
            incCol = 1;
        }
        
        int row = startRow;
        int col = startCol;
        
        while (posOK(row, col) && emptyPos(row, col) && length > 0) {
            labyrinth[row][col] = BLOCK_CHAR;
            length--;
            row += incRow;
            col += incCol;
        }
    }

    public Directions[] validMoves(int row, int col) {
        List<Directions> output = new ArrayList<>();
        if (canStepOn(row+1, col)) {
            output.add(Directions.DOWN);
        }
        if (canStepOn(row-1, col)) {
            output.add(Directions.UP);
        }
        if (canStepOn(row, col+1)) {
            output.add(Directions.RIGHT);
        }
        if (canStepOn(row, col-1)) {
            output.add(Directions.LEFT);
        }
        
        
        return output.toArray(new Directions[output.size()]);
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
                && !combatPos(row, col)
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
            case DOWN  -> out[ROW]++;
            case UP    -> out[ROW]--;
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
        Monster output = null;
        if (canStepOn(row, col)) {
            if (posOK(oldRow, oldCol)) {
                Player p = players[oldRow][oldCol];
                if (p == player) {
                    updateOldPos(oldRow, oldCol);
                    players[oldRow][oldCol] = null;
                }
            }
            
            if (monsterPos(row, col)) {
                labyrinth[row][col] = COMBAT_CHAR;
                output = monsters[row][col];
            } else {
                labyrinth[row][col] = player.getNumber();
            }
            
            players[row][col] = player;
            player.setPos(row, col);
            
        }
        return output;
    }
}
