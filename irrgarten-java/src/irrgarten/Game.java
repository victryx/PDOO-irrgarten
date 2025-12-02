package irrgarten;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;

    private Player currentPlayer;
    private List<Player> players;
    private Labyrinth labyrinth;
    private List<Monster> monsters;

    public Game(int nPlayers) {
        // 0 < nPlayers < 10
        monsters = new ArrayList();
        players = new ArrayList(nPlayers);
        for (int i = 0; i <= nPlayers; i++) {
            players.add(new Player((char) (i + '0'), Dice.randomIntelligence(), Dice.randomStrength()));
        }
        currentPlayerIndex = Dice.whoStarts(nPlayers);
        currentPlayer = players.get(currentPlayerIndex);
        log = "";

        labyrinth = new Labyrinth(10, 10, 1, 1);
        configureLabyrinth();
        labyrinth.spreadPlayers(players.toArray(new Player[players.size()]));
    }

    public boolean finished() {
        return labyrinth.haveAWinner();
    }

    public boolean nextStep(Directions preferredDirection) {
        if (!currentPlayer.dead()) {
            Directions direction = actualDirection(preferredDirection);
            if (direction != preferredDirection) {
                logPlayerNoOrders();
            }
            Monster monster = labyrinth.putPlayer(direction, currentPlayer);
            
            if (monster == null) {
                logNoMonster();
            } else {
                GameCharacter winner = combat(monster);
                manageReward(winner);
            }
        
        } else {
            manageResurrection();
        }
        
        boolean endGame = finished();
        if (!endGame) {
            nextPlayer();
        }
        return endGame;
    }
    
    public GameState getGameState() {
        return new GameState(
                labyrinth.toString(),
                players.toString(),
                monsters.toString(),
                currentPlayerIndex,
                finished(),
                log
        );
    }

    private void configureLabyrinth() {
        // TODO: esto se puede hacer con posiciones aleatorias
        Monster m1 = new Monster("1", Dice.randomIntelligence(), Dice.randomStrength());
        monsters.add(m1);
        labyrinth.addMonster(1, 0, m1);
        m1 = new Monster("1", Dice.randomIntelligence(), Dice.randomStrength());
        monsters.add(m1);
        labyrinth.addMonster(2, 1, m1);
        
        labyrinth.addBlock(Orientation.HORIZONTAL, 0, 0, 10);
    }

    private void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }
    
    private Directions actualDirection(Directions preferredDirection) {
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        Directions[] validMoves = labyrinth.validMoves(currentRow, currentCol);
        return currentPlayer.move(preferredDirection, validMoves);
    }

    private GameCharacter combat(Monster monster) {
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        
        float playerAttack = currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        while (!lose && rounds < MAX_ROUNDS) {
            winner = GameCharacter.MONSTER;
            rounds++;
            float monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);
            
            if (!lose) {
                playerAttack = currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
            }
        }
        
        logRounds(rounds, MAX_ROUNDS);

        return winner;
    }
    
    private void manageReward(GameCharacter winner) {
        if (winner == GameCharacter.PLAYER) {
            currentPlayer.receiveReward();
            logPlayerWon();
        } else {
            logMonsterWon();
        }        
    }
    
    private void manageResurrection() {
        if (Dice.resurrectPlayer()) {
            currentPlayer.resurrect();
            logResurrected();
        } else {
            logPlayerSkipTurn();
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    private void logPlayerWon() {
        log += "Player " + currentPlayer.getNumber() + " wins\n";
    }

    private void logMonsterWon() {
        log += "Monster wins the combat\n";
    }

    private void logResurrected() {
        log += "Player " + currentPlayer.getNumber() + " resurrects\n";
    }

    private void logPlayerSkipTurn() {
        log += "Player " + currentPlayer.getNumber() + " skips their turn\n";
    }

    private void logPlayerNoOrders() {
        log += "Player " + currentPlayer.getNumber() + " doesn't follow human player instructions\n";
    }

    private void logNoMonster() {
        log += "Player " + currentPlayer.getNumber() + " moves to an empty cell or is unable to move\n";
    }

    private void logRounds(int rounds, int max) {
        log += rounds + "/" + max + " combat rounds played\n";
    }

}
