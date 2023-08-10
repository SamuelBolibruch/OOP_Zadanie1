package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;

public abstract class Karta {
    private final String nazov;

    public Karta(String nazov) {
        this.nazov = nazov;
    }

    public String getNazov() {
        return nazov;
    }

    protected void vypis(int aktualnyHrac, ArrayList<Hrac> hraci) {
        System.out.println(BangLite.ANSI_GREEN + "--------------------------------------------------------------");
        System.out.println("*** HRAC: " + hraci.get(aktualnyHrac).getMeno() + " ZAHRAL KARTU " + this.nazov + " ***");
        System.out.println("--------------------------------------------------------------" + BangLite.ANSI_RESET);
    }

    protected int vyberProtihraca(int aktualnyHrac,ArrayList<Hrac> protihraci) {
        int cisloProtihraca = 0;
        while (cisloProtihraca < 1 || cisloProtihraca > protihraci.size() || aktualnyHrac == cisloProtihraca-1 ) {
            cisloProtihraca = ZKlavesnice.readInt("-Zadaj cislo protihraca, na  ktoreho chces pouzit kartu: ");
            if(cisloProtihraca < 1 || cisloProtihraca > protihraci.size()) {
                System.out.println("!!! Protihrac so zadanim cislom neexistuje, SKUS TO ZNOVA !!!");
            }
            if(aktualnyHrac == cisloProtihraca-1){
                System.out.println("!!! Nemozes pouzit kartu sam na seba !!!");
            }
        }
        return cisloProtihraca - 1;
    }

    public abstract boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek);
}
