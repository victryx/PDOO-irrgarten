package tests;
import irrgarten.*;

public class TestP1 {
    public static void main(String[] args) {
        /* 1. Clase Weapon*/
        System.out.println("Prueba clase Weapon");
        Weapon weapon = new Weapon(Dice.weaponPower(), Dice.usesLeft());
        weapon.discard();
        System.out.printf("Weapon: %s\n", weapon.toString());
        System.out.printf("weapon.attack(): %f\n", weapon.attack());
        System.out.printf("weapon.discard(): %b\n", weapon.discard());

        /* 2. Clase shield */
        System.out.println("\nPrueba clase shield");
        Shield shield = new Shield(Dice.shieldPower(), Dice.usesLeft());
        weapon.discard();
        System.out.printf("Shield: %s\n", shield.toString());
        System.out.printf("Shield.protect(): %f\n", shield.protect());
        System.out.printf("Shield.discard(): %b\n", shield.discard());
        
        /* 3. Clase GameState */
        System.out.println("\nPrueba clase GameState");
        GameState g = new GameState("Laberinto", "Jugadores", "Mostruos", 0, false, "log");
        System.out.println("(GameState no tiene funcionalidad todavía");
        
        /* Clase Dice */
        /*
            Solo se han incluido los métodos más importantes
        */
        System.out.println("\nDice.randomPos(10) (valores entre 0 y 10)");
        for (int i = 0; i < 100; i++) {
            System.out.printf("ejecución nº %02d:\t %d\n", i, Dice.randomPos(10));
        }
        
        System.out.println("\nDice.whoStarts(3) (valores entre 0 y 2");
        for (int i = 0; i < 100; i++) {
            System.out.printf("ejecución nº %02d:\t %d\n", i, Dice.whoStarts(3));
        }
        
        System.out.println("\nDice.randomIntelligence()");
        for (int i = 0; i < 100; i++) {
            System.out.printf("ejecución nº %02d:\t %f\n", i, Dice.randomIntelligence());
        }
        
        
        System.out.println("\nDice.randomStrength()");
        for (int i = 0; i < 100; i++) {
            System.out.printf("ejecución nº %02d:\t %f\n", i, Dice.randomStrength());
        }
        
        System.out.println("\n¡Dice.resurrectPlayer() (tiene que resucitar aproximádamente el 30% de las ejecuciones");
        int veces_true = 0;
        for (int i = 0; i < 100; i++) {
            boolean resurrects = Dice.resurrectPlayer();
            System.out.printf("ejecución nº %02d:\t %b\n", i, resurrects);
            if (resurrects) veces_true++;
        }
        System.out.printf("Resucita: %d ejecuciones\nNo resucita: %d ejecuciones\n",
                veces_true, 100 - veces_true);
        
        System.out.println("\nDice.discardElement(0) (tiene que devolver siempre true)");
        veces_true = 0;
        for (int i = 0; i < 100; i++) {
            boolean descartado = Dice.discardElement(0);
            System.out.printf("ejecución nº %02d:\t %b\n", i, descartado);
            if (descartado) veces_true++;
        }
        System.out.printf("Se descarta: %d ejecuciones\nNo se descarta: %d ejecuciones\n",
                veces_true, 100 - veces_true);
        
        System.out.println("\nDice.discardElement(5) (tiene que devolver siempre false)");
        veces_true = 0;
        for (int i = 0; i < 100; i++) {
            boolean descartado = Dice.discardElement(5); // Dice.MAX_USES == 5
            System.out.printf("ejecución nº %02d:\t %b\n", i, descartado);
            if (descartado) veces_true++;
        }
        System.out.printf("Se descarta: %d ejecuciones\nNo se descarta: %d ejecuciones\n",
                veces_true, 100 - veces_true);
        
        System.out.println("\nDice.discardElement(3) (Se debería descartar aproximadamente 2 de cada 5 veces (40%)))");
        veces_true = 0;
        for (int i = 0; i < 100; i++) {
            boolean descartado = Dice.discardElement(3); // Dice.MAX_USES == 5
            System.out.printf("ejecución nº %02d:\t %b\n", i, descartado);
            if (descartado) veces_true++;
        }
        System.out.printf("Se descarta: %d ejecuciones\nNo se descarta: %d ejecuciones\n",
                veces_true, 100 - veces_true);
        
    }
}
