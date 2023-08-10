package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;

import java.util.ArrayList;

public class Pivo extends Karta {
    private static final String CARD_NAME = "Pivo";
    public Pivo() {
        super(CARD_NAME);
    }

    @Override
    public boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek) {
        super.vypis(aktualnyHrac, hraci);
        hraci.get(aktualnyHrac).pridajZivot(1);
        System.out.println(BangLite.ANSI_YELLOW + hraci.get(aktualnyHrac).getMeno() + " si pridal jeden zivot, novy pocet zivotov: " + hraci.get(aktualnyHrac).getZivoty() + "\n" + BangLite.ANSI_RESET);
        return true;
    }
}
