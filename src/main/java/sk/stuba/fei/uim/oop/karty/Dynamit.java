package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;

import java.util.ArrayList;
import java.util.Random;

public class Dynamit extends Karta {
    private static final String CARD_NAME = "Dynamit";
    private final Random random;

    public Dynamit() {
        super(CARD_NAME);
        random = new Random();
    }

    @Override
    public boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek) {
        Karta dynamit = new Dynamit();
        if(hraci.get(aktualnyHrac).maKartu(dynamit, hraci.get(aktualnyHrac).getVylozeneKarty()) != -1){
            System.out.println(BangLite.ANSI_YELLOW + "!!! Uz mas kartu " + dynamit.getNazov() + " vylozenu, nemozes ju mat vylozenu 2x. !!!" + BangLite.ANSI_RESET);
        } else {
            super.vypis(aktualnyHrac, hraci);
            Karta karta = hraci.get(aktualnyHrac).getKartyNaRuke().get(cisloKarty);
            hraci.get(aktualnyHrac).getVylozeneKarty().add(karta);
            hraci.get(aktualnyHrac).getKartyNaRuke().remove(cisloKarty);
            System.out.println(BangLite.ANSI_YELLOW + "Odteraz ma kartu vylozenu na stole\n" + BangLite.ANSI_RESET);
        }
        return false;
    }
    public boolean vykonajEfekt(int aktualnyHrac, ArrayList<Hrac> hraci) {
        boolean vybuchol = random.nextInt(8) == 0;
        if (vybuchol) {
            System.out.println(BangLite.ANSI_PURPLE + "Dynamit vybuchol, hracovi " + hraci.get(aktualnyHrac).getMeno() + " sa uberaju 3 zivoty." + BangLite.ANSI_RESET);
            hraci.get(aktualnyHrac).uberZivot(3);
            if (!hraci.get(aktualnyHrac).jeAktivny()) {
                System.out.println(BangLite.ANSI_PURPLE + "!!! " + hraci.get(aktualnyHrac).getMeno() + " vypadava z hry !!!\n" + BangLite.ANSI_RESET);
            }
            return true;
        } else {
            int predchadzajuciHrac = aktualnyHrac == 0 ? hraci.size() - 1 : aktualnyHrac - 1;
            System.out.println(BangLite.ANSI_PURPLE + "Dynamit nevybuchol, dynamit sa presuva hracovi " + hraci.get(predchadzajuciHrac).getMeno() + BangLite.ANSI_RESET);
            hraci.get(predchadzajuciHrac).getVylozeneKarty().add(this);
            return false;
        }
    }
}
