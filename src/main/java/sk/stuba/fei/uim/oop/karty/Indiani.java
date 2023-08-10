package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;

import java.util.ArrayList;

public class Indiani extends Karta {
    private static final String CARD_NAME = "Indiani";
    public Indiani() {
        super(CARD_NAME);
    }

    @Override
    public boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek) {
        super.vypis(aktualnyHrac, hraci);
        Karta bang = new Bang();

        for (Hrac hrac : hraci) {
            if(hraci.indexOf(hrac) != aktualnyHrac) {
                if (hrac.maKartu(bang, hrac.getKartyNaRuke()) != -1) {
                    System.out.println(BangLite.ANSI_YELLOW + hrac.getMeno() + " ma kartu 'Bang' ktoru zahral. " + BangLite.ANSI_RESET);
                    int indexKarty = hrac.maKartu(bang, hrac.getKartyNaRuke());
                    Karta karta = hrac.getKartyNaRuke().get(indexKarty);
                    balicek.getBalicek().add(karta);
                    hrac.getKartyNaRuke().remove(indexKarty);
                } else {
                    hrac.uberZivot(1);
                    System.out.println(BangLite.ANSI_YELLOW + hrac.getMeno() + " prisiel o 1 zivot." + BangLite.ANSI_RESET);
                }
            }
        }
        System.out.println("\n");
        return true;
    }
}

