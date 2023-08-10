package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;

import java.util.ArrayList;

public class Bang extends Karta {
    private static final String CARD_NAME = "Bang";
    public Bang() {
        super(CARD_NAME);
    }

    @Override
    public boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek) {
        super.vypis(aktualnyHrac, hraci);
        int indexProtihraca = this.vyberProtihraca(aktualnyHrac, hraci);
        Hrac protihrac = hraci.get(indexProtihraca);

        boolean uholSa = false;
        for (Karta karta : protihrac.getVylozeneKarty()) {
            if (karta instanceof Barrel) {
                uholSa = ((Barrel) karta).vykonajEfekt();
                break;
            }
        }

        if (!uholSa) {
            Karta vedla = new Vedla();

            if (protihrac.maKartu(vedla, protihrac.getKartyNaRuke()) != -1) {
                System.out.println(BangLite.ANSI_YELLOW + protihrac.getMeno() + " ma kartu 'Vedla' ktoru zahral. \n" + BangLite.ANSI_RESET);
                balicek.getBalicek().add(protihrac.getKartyNaRuke().get(protihrac.maKartu(vedla, protihrac.getKartyNaRuke())));
                protihrac.getKartyNaRuke().remove(protihrac.maKartu(vedla, protihrac.getKartyNaRuke()));
            } else {
                protihrac.uberZivot(1);
                System.out.println(BangLite.ANSI_YELLOW + protihrac.getMeno() + " prisiel o 1 zivot.\n" + BangLite.ANSI_RESET);
            }
        }
        return true;
    }

}

