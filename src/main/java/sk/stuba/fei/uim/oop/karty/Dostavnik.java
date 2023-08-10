package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;

import java.util.ArrayList;

public class Dostavnik extends Karta {
    private static final String CARD_NAME = "Dostavnik";
    public Dostavnik() {
        super(CARD_NAME);
    }

    @Override
    public boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek) {
        if (balicek.getBalicek().size() < 2) {
            System.out.println(BangLite.ANSI_YELLOW + "!!! Kartu nemozes zahrat, v balicku nieje dostatok kariet !!!" + BangLite.ANSI_RESET);
            return false;
        } else {
            super.vypis(aktualnyHrac, hraci);
            System.out.println(BangLite.ANSI_YELLOW + "Berie si dve karty: " + BangLite.ANSI_RESET);
            hraci.get(aktualnyHrac).zoberKarty(balicek, 2, true);
            return true;
        }
    }
}
