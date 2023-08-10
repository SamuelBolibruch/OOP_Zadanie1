package sk.stuba.fei.uim.oop.balicek;

import sk.stuba.fei.uim.oop.karty.*;
import java.util.ArrayList;
import java.util.Collections;

public class Balicek {
    private final ArrayList<Karta> balicek;

    public Balicek() {
        this.balicek = new ArrayList<Karta>();

        for(int i = 0; i < 2; i++){
            balicek.add(new Barrel());
        }
        balicek.add(new Dynamit());

        for(int i = 0; i < 3; i++){
            balicek.add(new Vazenie());
        }
        for(int i = 0; i < 30; i++){
            balicek.add(new Bang());
        }
        for(int i = 0; i < 15; i++){
            balicek.add(new Vedla());
        }
        for(int i = 0; i < 8; i++){
            balicek.add(new Pivo());
        }
        for(int i = 0; i < 6; i++){
            balicek.add(new CatBalou());
        }
        for(int i = 0; i < 4; i++){
            balicek.add(new Dostavnik());
        }
        for(int i = 0; i < 2; i++){
            balicek.add(new Indiani());
        }

        premiesaj();
    }

    public ArrayList<Karta> getBalicek() {
        return balicek;
    }

    private void premiesaj() {
        Collections.shuffle(this.balicek);
    }

    public void pridajKartu(Karta karta) {
        this.getBalicek().add(karta);
    }
}
