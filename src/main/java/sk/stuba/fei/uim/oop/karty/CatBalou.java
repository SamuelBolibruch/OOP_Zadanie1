package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.hrac.Hrac;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.Random;

public class CatBalou extends Karta {
    private static final String CARD_NAME = "CatBalou";
    private final Random random;
    public CatBalou() {
        super(CARD_NAME);
        random = new Random();
    }

    @Override
    public boolean pouziKartu(int aktualnyHrac, int cisloKarty, ArrayList<Hrac> hraci, Balicek balicek) {
        int vyhodKartu;


        if(!asponJedenHracMaKarty(aktualnyHrac, hraci)) {
            System.out.println(BangLite.ANSI_YELLOW + "!!! Kartu nemozes zahrat, nikto nema karty !!!\n" + BangLite.ANSI_RESET);
            return false;
        }
        super.vypis(aktualnyHrac, hraci);
        int indexProtihraca = this.vyberProtihraca(aktualnyHrac, hraci);
        while (!hraci.get(indexProtihraca).maKarty()) {
            System.out.println(BangLite.ANSI_YELLOW + "!!! Hrac nema karty, vyber ineho hraca !!!\n" + BangLite.ANSI_RESET);
            indexProtihraca = this.vyberProtihraca(aktualnyHrac, hraci);
        }

        Hrac protihrac = hraci.get(indexProtihraca);

        if (protihrac.getKartyNaRuke().size() != 0 && protihrac.getVylozeneKarty().size() == 0) {
            vyhodKartu = vyberNahodnuKartu(protihrac.getKartyNaRuke().size());
            System.out.println(BangLite.ANSI_YELLOW + "Hracovi " + protihrac.getMeno() + " bola vyhodena z ruky " + (vyhodKartu+1) + ". karta, s nazvom: '" + protihrac.getKartyNaRuke().get(vyhodKartu).getNazov() + "'\n" + BangLite.ANSI_RESET);
            balicek.pridajKartu(protihrac.getKartyNaRuke().get(vyhodKartu));
            protihrac.getKartyNaRuke().remove(vyhodKartu);
        } else if (protihrac.getKartyNaRuke().size() == 0 && protihrac.getVylozeneKarty().size() != 0) {
            vyhodKartu = vyberNahodnuKartu(protihrac.getVylozeneKarty().size());
            System.out.println(BangLite.ANSI_YELLOW + "Hracovi " + protihrac.getMeno() + " bola vyhodena zo stola " + (vyhodKartu+1) + ". karta, s nazvom: '" + protihrac.getVylozeneKarty().get(vyhodKartu).getNazov() + "'\n" + BangLite.ANSI_RESET);
            balicek.pridajKartu(protihrac.getVylozeneKarty().get(vyhodKartu));
            protihrac.getVylozeneKarty().remove(vyhodKartu);
        } else {
            int vyberOdkialVyhoditKartu = 0;
            while (vyberOdkialVyhoditKartu < 1 || vyberOdkialVyhoditKartu > 2) {
                System.out.println("*** Hrac " + protihrac.getMeno() + " urcuje odkial vyhodit kartu: ***");
                vyberOdkialVyhoditKartu = ZKlavesnice.readInt("Pre kartu z ruky zadaj '1', pre kartu zo stola zadaj '2': ");
                if(vyberOdkialVyhoditKartu < 1 || vyberOdkialVyhoditKartu > 2) {
                    System.out.println("!!! Neplatny typ karty !!!");
                }
            }
            if (vyberOdkialVyhoditKartu == 1) {
                vyhodKartu = vyberNahodnuKartu(protihrac.getKartyNaRuke().size());
                System.out.println(BangLite.ANSI_YELLOW + "Hracovi " + protihrac.getMeno() + " bola vyhodena z ruky " + (vyhodKartu+1) + ". karta, s nazvom: '" + protihrac.getKartyNaRuke().get(vyhodKartu).getNazov() + "'\n" + BangLite.ANSI_RESET);
                balicek.pridajKartu(protihrac.getKartyNaRuke().get(vyhodKartu));
                hraci.get(indexProtihraca).getKartyNaRuke().remove(vyhodKartu);
            } else {
                vyhodKartu = vyberNahodnuKartu(protihrac.getVylozeneKarty().size());
                System.out.println(BangLite.ANSI_YELLOW + "Hracovi " + protihrac.getMeno() + " bola vyhodena zo stola " + (vyhodKartu+1) + ". karta, s nazvom: '" + protihrac.getVylozeneKarty().get(vyhodKartu).getNazov() + "'\n" + BangLite.ANSI_RESET);
                balicek.pridajKartu(protihrac.getVylozeneKarty().get(vyhodKartu));
                protihrac.getVylozeneKarty().remove(vyhodKartu);
            }
        }
        return true;
    }

    public boolean asponJedenHracMaKarty(int aktualnyHrac, ArrayList<Hrac> hraci) {
        for (int i = 0; i < hraci.size(); i++) {
            Hrac hrac = hraci.get(i);
            if (hrac.maKarty() && i != aktualnyHrac) {
                return true;
            }
        }
        return false;
    }

    private int vyberNahodnuKartu(int hornaHranica) {
        return random.nextInt(hornaHranica);
    }
}
