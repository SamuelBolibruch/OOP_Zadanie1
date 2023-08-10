package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;

import java.util.ArrayList;
import java.util.Random;

public class Vazenie extends Karta {
    private static final String CARD_NAME = "Vazenie";
    private final Random random;
    public Vazenie() {
        super(CARD_NAME);
        random = new Random();
    }


    @Override
    public boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek) {
        boolean vsetciMajuVazenie = kontrolaVazenia(aktualnyHrac, hraci);
        if (vsetciMajuVazenie) {
            System.out.println("!!! Vsetci hraci maju vylozenu kartu vazenie, kartu nemozes zahrat !!!");
        } else {
            super.vypis(aktualnyHrac, hraci);
            int indexProtihraca = vyberProtihraca(aktualnyHrac, hraci);
            Karta vazenie = new Vazenie();
            while (hraci.get(indexProtihraca).maKartu(vazenie, hraci.get(indexProtihraca).getVylozeneKarty()) != -1) {
                System.out.println(BangLite.ANSI_YELLOW + "!!! Hrac uz  ma kartu " + vazenie.getNazov() + " vylozenu, nemoze ju mat vylozenu 2x. !!!" + BangLite.ANSI_RESET);
                indexProtihraca = vyberProtihraca(aktualnyHrac, hraci);
            }
            Karta karta = hraci.get(aktualnyHrac).getKartyNaRuke().get(cisloKarty);
            hraci.get(indexProtihraca).getVylozeneKarty().add(karta);
            hraci.get(aktualnyHrac).getKartyNaRuke().remove(cisloKarty);
            System.out.println(BangLite.ANSI_YELLOW + "Hracovi " + hraci.get(indexProtihraca).getMeno() + " bola na stol vylozena karta " + this.getNazov() + "\n" + BangLite.ANSI_RESET);
        }

        return false;
    }

    private boolean kontrolaVazenia(int aktualnyHrac, ArrayList<Hrac> hraci) {
        boolean vsetciMajuVazenie = true;

        for (int i = 0; i < hraci.size(); i++) {
            if (i != aktualnyHrac) {
                Karta vazenie = new Vazenie();
                if (hraci.get(i).maKartu(vazenie, hraci.get(i).getVylozeneKarty()) == -1) {
                    vsetciMajuVazenie = false;
                    break;
                }
            }
        }
        return vsetciMajuVazenie;
    }

    public boolean vykonajEfekt() {
        boolean usielZVazenia = random.nextInt(4) == 0;

        if (usielZVazenia) {
            System.out.println(BangLite.ANSI_PURPLE + "*** Hracovi sa podarilo ujst z vazenia ***\n" + BangLite.ANSI_RESET);
        } else {
            System.out.println(BangLite.ANSI_PURPLE + "*** Hrac zostava toto kolo vo vazeni, pokracuje dalsi hrac v poradi. ***\n" + BangLite.ANSI_RESET);
        }
        return usielZVazenia;
    }
}
