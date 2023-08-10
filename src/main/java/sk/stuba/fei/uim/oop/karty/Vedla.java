package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;

import java.util.ArrayList;

public class Vedla extends Karta {
    private static final String CARD_NAME = "Vedla";
    public Vedla() {
        super(CARD_NAME);
    }

    @Override
    public boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek) {
        System.out.println(BangLite.ANSI_YELLOW + "Tuto kartu mozes zahrat iba ak na teba niekto pouzije kartu Bang!!!\n" + BangLite.ANSI_RESET);
        return false;
    }
}