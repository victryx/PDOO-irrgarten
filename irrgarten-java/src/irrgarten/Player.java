package irrgarten;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vik
 */
public class Player {

    private static int MAX_WEAPONS = 2;
    private static int MAX_SHIELDS = 3;
    private static int INITIAL_HEALTH = 10;
    private static int HITS2LOSE = 3;

    private String name;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits;

    private List<Weapon> weapons;
    private List<Shield> shields;

    public Player(char number, float intelligence, float strength) {
        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
        
        consecutiveHits = 0;
        health = INITIAL_HEALTH;
        name = "Player #" + number;
        setPos(-1,-1);
        
        weapons = new ArrayList();
        shields = new ArrayList();
    }

    public void resurrect() {
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        consecutiveHits = 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getNumber() {
        return number;
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean dead() {
        return health <= 0;
    }

    public Directions move(Directions direction, Directions[] validMoves) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public float attack() {
        return strength + sumWeapons();
    }

    public boolean defend(float receivedAttack) {
        return manageHit(receivedAttack);
    }

    public void receiveReward() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", number=" + number + ", intelligence=" + intelligence + ", strength=" + strength + ", health=" + health + ", row=" + row + ", col=" + col + ", consecutiveHits=" + consecutiveHits + ", weapons=" + weapons + ", shields=" + shields + '}';
    }

    private void receiveWeapon(Weapon w) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void receiveShield(Shield s) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    private Weapon newWeapon() {
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }
    
    private Shield newShield() {
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }
    
    private float sumWeapons() {
        float sum = 0;
        for (Weapon w : weapons) {
            sum += w.attack();
        }
        return sum;
    }
    
    private float sumShields() {
        float sum = 0;
        for (Shield s : shields) {
            sum += s.protect();
        }
        return sum;
    }
    
    private float defensiveEnergy() {
        return intelligence + sumShields();
    }

    private boolean manageHit(float receivedAttack) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    private void resetHits() {
        consecutiveHits = 0;
    }
    
    private void gotWounded() {
        health--;
    }
    
    private void incConsecutiveHits() {
        consecutiveHits++;
    }
}
