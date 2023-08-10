package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;

import java.util.ArrayList;
import java.util.Random;

public class Barrel extends Karta {
    private static final String CARD_NAME = "Barrel";
    private final Random random;
    public Barrel() {
        super(CARD_NAME);
        random = new Random();
    }

    @Override
    public boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek) {
        Karta barrel = new Barrel();
        if (hraci.get(aktualnyHrac).maKartu(barrel, hraci.get(aktualnyHrac).getVylozeneKarty()) != -1) {
            System.out.println(BangLite.ANSI_YELLOW + "!!! Uz mas kartu " + barrel.getNazov() + " vylozenu, nemozes ju mat vylozenu 2x. !!!\n" + BangLite.ANSI_RESET);
        } else {
            super.vypis(aktualnyHrac, hraci);
            Karta karta = hraci.get(aktualnyHrac).getKartyNaRuke().remove(cisloKarty);
            hraci.get(aktualnyHrac).getVylozeneKarty().add(karta);
            System.out.println(BangLite.ANSI_YELLOW + "Odteraz ma kartu vylozenu na stole\n" + BangLite.ANSI_RESET);
        }
        return false;
    }

    public boolean vykonajEfekt (){
        boolean skrylSa = random.nextInt(4) == 0;
        if (skrylSa) {
            System.out.println(BangLite.ANSI_YELLOW + "Vybranemu protihracovi sa podarilo skryt. \n" + BangLite.ANSI_RESET);
            return true;
        } else {
            System.out.println(BangLite.ANSI_YELLOW + "Protihracovi sa nepodarilo skryt. \n" + BangLite.ANSI_RESET);
            return false;
        }
    }
}

