package tests;

import irrgarten.Game;
import irrgarten.ui.TextUI;
import irrgarten.controller.Controller;


public class TestP3 {
    public static void main(String[] args) {
        Game g = new Game(1);
        TextUI i = new TextUI();
        Controller c = new Controller(g, i);
        c.play();
    }
}
