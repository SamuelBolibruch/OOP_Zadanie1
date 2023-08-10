package sk.stuba.fei.uim.oop.hrac;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.bang.BangLite;
import sk.stuba.fei.uim.oop.karty.Karta;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;
import java.util.ArrayList;


public class Hrac {
    private final String meno;
    private int zivoty;
    private final ArrayList<Karta> kartyNaRuke;
    private final ArrayList<Karta> vylozeneKarty;


    public Hrac(String meno) {
        this.meno = meno;
        this.zivoty = 4;
        this.kartyNaRuke = new ArrayList<>();
        this.vylozeneKarty = new ArrayList<>();
    }

    public String getMeno() {
        return meno;
    }

    public int getZivoty() {
        return zivoty;
    }

    public ArrayList<Karta> getKartyNaRuke() {
        return kartyNaRuke;
    }

    public ArrayList<Karta> getVylozeneKarty() {
        return vylozeneKarty;
    }

    public boolean jeAktivny() {
        return this.zivoty>0;
    }

    public void vypisOHracovi() {
        System.out.println(BangLite.ANSI_RED + "--------------------------------------------------------------");
        System.out.println("***** NA RADE JE HRAC: " + this.getMeno() + " *****");
        System.out.println("POCET JEHO ZIVOTOV JE: " + this.zivoty);
        System.out.println("Hrac ma: KARTY NA RUKE x " + this.kartyNaRuke.size() + "\n         VYLOZENE KARTY x " + this.vylozeneKarty.size());
        System.out.println("--------------------------------------------------------------");
        this.vypisKartyNaRuke();
        System.out.println("--------------------------------------------------------------");
        this.vypisKartyNaStole();
        System.out.println("--------------------------------------------------------------" + BangLite.ANSI_RESET);
    }

    public void zoberKarty(Balicek balicek, int pocetKariet, boolean vypis){
        if (balicek.getBalicek().size() < pocetKariet) {
            System.out.println("!!! V balicku nieje dostatok kariet!!!");
        } else {
            for (int i = 0; i < pocetKariet; i++) {
                Karta karta = balicek.getBalicek().remove(0);
                this.kartyNaRuke.add(karta);
                if (vypis) {
                    System.out.println("Zobral si kartu " + karta.getNazov());
                }
            }
            if (vypis) {
                System.out.println();
            }
        }
    }

    private void vypisKartyNaRuke() {
        System.out.println("KARTY NA RUKE: ");
        if (this.getKartyNaRuke().size() == 0) {
            System.out.println("Ziadne karty!");
        } else {
            for (int i = 0; i < this.getKartyNaRuke().size(); i++) {
                System.out.println((i + 1) + ". " + this.kartyNaRuke.get(i).getNazov());
            }
        }
    }

    public void vypisKartyNaStole() {
        System.out.println("KARTY NA STOLE: ");
        if (this.getVylozeneKarty().size() == 0) {
            System.out.println("Ziadne karty!");
        } else {
            for (int i = 0; i < this.getVylozeneKarty().size(); i++) {
                System.out.println((i + 1) + ". " + this.vylozeneKarty.get(i).getNazov());
            }
        }
    }

    private int vypisKariatAVyberKartyZRuky (String sloveso, int od) {
        vypisKartyNaRuke();
        int cisloKarty = -1;

        while (cisloKarty < od || cisloKarty > this.kartyNaRuke.size()) {
            cisloKarty = ZKlavesnice.readInt("\n-Zadaj cislo karty, ktoru chces " + sloveso +": ");
            if(cisloKarty < od || cisloKarty > this.kartyNaRuke.size()) {
                System.out.println("!!! Zle cislo karty, SKUS TO ZNOVA !!!");
            }
        }
        return cisloKarty-1;
    }

    public int zahrajKartu(int aktualnyHrac, ArrayList<Hrac> hraci, Balicek balicek){
        int cisloKarty = vypisKariatAVyberKartyZRuky("zahrat, pre ukoncenie fazy zahravania kariet zadaj '0': ", 0);
        boolean vyhadzuj;
        if (cisloKarty != -1) {
            vyhadzuj = kartyNaRuke.get(cisloKarty).pouziKartu(aktualnyHrac, cisloKarty, hraci, balicek);

            if (vyhadzuj) {
                Karta karta = this.kartyNaRuke.remove(cisloKarty);
                balicek.pridajKartu(karta);
            }
        }
        return cisloKarty;
    }

    public void vyhodKarty (Balicek balicek) {
        System.out.println("--------------------------------------------------------------");
        System.out.println("\n*** FAZA VYHADZOVANIA KARIET ***");
        if (this.kartyNaRuke.size() <= zivoty) {
            System.out.println("Nieje potrebne vyhadzovanie kariet.");
        }
        while(this.kartyNaRuke.size() > zivoty) {
            Karta karta = this.kartyNaRuke.remove(vypisKariatAVyberKartyZRuky("vyhodit", 1));
            balicek.pridajKartu(karta);
        }
        vypisKartyNaRuke();
    }

    public void pridajZivot(int kolkoKrat) {
        this.zivoty += kolkoKrat;
    }

    public void uberZivot(int kolkoKrat) {
        this.zivoty -= kolkoKrat;
    }

    public boolean maKarty() {
        return !kartyNaRuke.isEmpty() || !vylozeneKarty.isEmpty();
    }

    public int maKartu(Karta nazovKarty, ArrayList<Karta> karty) {
        for (int i = 0; i < karty.size(); i++) {
            Karta karta = karty.get(i);
            if (karta.getClass().isInstance(nazovKarty)) {
                return i;
            }
        }
        return -1;
    }
}
