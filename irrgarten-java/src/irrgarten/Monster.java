package irrgarten;

public class Monster {

    private static final int INITIAL_HEALTH = 5;

    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;

    public Monster(String name, float intelligence, float strength) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        health = INITIAL_HEALTH;

        setPos(-1, -1);
    }

    public boolean dead() {
        return health <= 0;
    }

    public float attack() {
        return Dice.intensity(strength);
    }

    public boolean defend(float receivedAttack) {
        if (!dead() && Dice.intensity(intelligence) < receivedAttack) {
            gotWounded();
        }

        return dead();
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "Monster{" + "name=" + name + ", intelligence=" + intelligence
                + ", strength=" + strength + ", health=" + health + ", row="
                + row + ", col=" + col + '}';
    }

    private void gotWounded() {
        health--;
    }

}
