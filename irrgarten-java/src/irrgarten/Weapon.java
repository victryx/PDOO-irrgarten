package irrgarten;

/**
 * Arma utilizada por el jugador durante el combate
 * @author vik
 */
public class Weapon {
    
    private float power;
    private int uses;

    public Weapon(float power, int uses) {
        this.power = power;
        this.uses = uses;
    }
    
    /**
     * Intensidad del ataque del jugador
     * @return 0 si uses == 0, power si uses > 0
     */
    public float attack() {
        float pow = 0;
        if (uses > 0) {
            pow = power;
            uses--;
        }
        return pow;
    }
    
    public boolean discard() {
        return Dice.discardElement(uses);
    }
    
    @Override
    public String toString() {
        return String.format("W[%f.2, %d]",power, uses);
    }
}