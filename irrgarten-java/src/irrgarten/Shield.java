package irrgarten;

/**
 * Escudo usado por el jugador durante el combate
 *
 * @author vik
 */
public class Shield {

    private float protection;
    private int uses;

    public Shield(float protection, int uses) {
        this.protection = protection;
        this.uses = uses;
    }

    /**
     * Intensidad de la defensa del jugador
     *
     * @return
     */
    public float protect() {
        float prot = 0;
        if (uses > 0) {
            prot = protection;
            uses--;
        }
        return prot;
    }

    @Override
    public String toString() {
        return String.format("S[%f.2, %d]", protection, uses);
    }
    
    public boolean discard() {
        return Dice.discardElement(uses);
    }
}
