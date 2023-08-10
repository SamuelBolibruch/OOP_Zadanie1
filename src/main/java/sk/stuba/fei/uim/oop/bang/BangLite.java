package sk.stuba.fei.uim.oop.bang;

import sk.stuba.fei.uim.oop.balicek.Balicek;
import sk.stuba.fei.uim.oop.hrac.Hrac;
import sk.stuba.fei.uim.oop.karty.Dynamit;
import sk.stuba.fei.uim.oop.karty.Karta;
import sk.stuba.fei.uim.oop.karty.Vazenie;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;

public class BangLite {
    private final ArrayList<Hrac> vyhodenyHraci;
    private final ArrayList<Hrac> hraci;
    private int aktualnyHrac;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";

    public BangLite() {
        setAktualnyHrac(0);
        System.out.println("*____ VITAJ V HRE BANG LITE ____*");
        int pocetHracov = 0;
        while (pocetHracov < 2 || pocetHracov > 4) {
            pocetHracov = ZKlavesnice.readInt(" Zadaj pocet hracov (2-4): ");
            if(pocetHracov < 2 || pocetHracov > 4) {
                System.out.println("!!! Zly pocet hracov. SKUS TO ZNOVA !!!");
            }
        }
        this.vyhodenyHraci = new ArrayList<Hrac>();

        this.hraci = new ArrayList<Hrac>();

        for (int i = 0; i < pocetHracov; i++) {
            String meno = ZKlavesnice.readString("Zadaj meno " + (i + 1 ) + ". hraca: ");
            while (jeMenoVZozname(meno)) {
                System.out.println("Hrac s menom " + meno + " uz existuje, zvolte ine meno.");
                meno = ZKlavesnice.readString("Zadaj meno " + (i + 1 ) + ". hraca: ");
            }
            this.hraci.add(new Hrac(meno));
        }

        this.play();
    }

    public ArrayList<Hrac> getHraci() {
        return hraci;
    }

    public int getAktualnyHrac() {
        return aktualnyHrac;
    }

    public void setAktualnyHrac(int aktualnyHrac) {
        this.aktualnyHrac = aktualnyHrac;
    }

    private void play() {

        System.out.println(ANSI_RED + "_________________________\n*** HRA PRAVE ZACALA ***" + ANSI_RESET);
        Balicek balicek = new Balicek();

        for(Hrac hrac : this.hraci) {
            hrac.zoberKarty(balicek, 4, false);
        }

        while (true) {
            Hrac aktivnyHrac = this.hraci.get(this.aktualnyHrac);
            aktivnyHrac.vypisOHracovi();

            boolean dalsiHracNaRade;
            dalsiHracNaRade = this.skontrolujDynamit(balicek);
            if (dalsiHracNaRade){
                if (hraci.size() == 1){
                    break;
                }
                continue;
            }

            dalsiHracNaRade = this.skontrolujVazenie(balicek);
            if (dalsiHracNaRade){
                continue;
            }
            vypisZivotovOponentov();
            System.out.println("*** Hrac " + aktivnyHrac.getMeno() + " si zobral 2 karty. ***" );
            aktivnyHrac.zoberKarty(balicek, 2, true);

            int cisloZahranejKarty = -10;
            while (aktivnyHrac.getKartyNaRuke().size() > 0) {
                if (cisloZahranejKarty == -1) {
                    break;
                }
                cisloZahranejKarty = aktivnyHrac.zahrajKartu(aktualnyHrac,hraci,balicek);
                vyhodAkNiektoNemaZivoty(balicek);
                if (hraci.size() <= 1) {
                    break;
                }
            }

            if (hraci.size() <=1){
                break;
            }

            aktivnyHrac.vyhodKarty(balicek);
            this.dalsiHracNaRade();
        }
        this.vitazAPorazeny();
    }

    private boolean jeMenoVZozname(String meno) {
        for (Hrac hrac : this.hraci) {
            if (hrac.getMeno().equals(meno)) {
                return true;
            }
        }
        return false;
    }

    private void vypisZivotovOponentov() {
        System.out.println(ANSI_YELLOW + "*** AKTUALNE CISLA ZIVYCH PROTIHRACOV, ICH ZIVOTY A VYLOZENE KARTY: ***");
        for (int i = 0; i < this.hraci.size(); i++) {
            if(!this.hraci.get(aktualnyHrac).getMeno().equals(this.hraci.get(i).getMeno())) {
                System.out.println("PROTIHRAC - " + this.hraci.get(i).getMeno() + " - CISLO [" +(i + 1) + "]\nZIVOTY - " + this.hraci.get(i).getZivoty() + "\n");
                this.hraci.get(i).vypisKartyNaStole();
                System.out.println("--------------------------------------------------------------");
            }
        }
        System.out.println(ANSI_RESET);
    }

    private void vykonajAkciuPoZomretiHraca(int indexHraca, Balicek balicek) {
        balicek.getBalicek().addAll(hraci.get(indexHraca).getVylozeneKarty());
        balicek.getBalicek().addAll(hraci.get(indexHraca).getKartyNaRuke());
        hraci.get(indexHraca).getVylozeneKarty().clear();
        hraci.get(indexHraca).getKartyNaRuke().clear();
    }

    private void vyhodAkNiektoNemaZivoty ( Balicek balicek) {
        int i = 0;
        while (i < hraci.size()) {
            if (!hraci.get(i).jeAktivny()) {
                System.out.println(ANSI_RED + "*** Hrac " + hraci.get(i).getMeno() + " bol vyhodeny ***\n" + ANSI_RESET);
                this.vykonajAkciuPoZomretiHraca(i, balicek);
                this.vyhodenyHraci.add(this.hraci.get(i));
                if (i == this.aktualnyHrac && hraci.size() > 1) {
                    dalsiHracNaRade();
                }
                this.hraci.remove(i);
                if (i < aktualnyHrac) {
                    this.aktualnyHrac--;
                }
                if (this.hraci.size()>1){
                    this.aktivnyZivyHraci();
                }
            } else {
                i++;
            }
        }
    }

    private void aktivnyZivyHraci(){
        System.out.println("--------------------------------------------------------------\n"+ ANSI_RED +"ZOSTAVAJUCI PROTIHRACI A ICH NOVE INDEXY: \n");
        for (int i = 0; i < hraci.size(); i++) {
            if (hraci.get(i) != hraci.get(aktualnyHrac)){
                System.out.println("PROTIHRAC - " + this.hraci.get(i).getMeno() + " - CISLO [" +(i + 1) + "] ,ZIVOTY - " + this.hraci.get(i).getZivoty() + "\n");
            }
        }
        System.out.println(ANSI_RESET + "--------------------------------------------------------------");
    }

    private void dalsiHracNaRade() {
        this.aktualnyHrac++;
        this.aktualnyHrac %= this.hraci.size();
    }

    private boolean skontrolujDynamit(Balicek balicek) {
        boolean vybuchol;
        for (Karta karta : hraci.get(aktualnyHrac).getVylozeneKarty()) {
            if (karta instanceof Dynamit) {
                vybuchol = ((Dynamit) karta).vykonajEfekt(aktualnyHrac, hraci);
                if(vybuchol) {
                    balicek.pridajKartu(karta);
                }
                hraci.get(aktualnyHrac).getVylozeneKarty().remove(karta);
                if (hraci.get(aktualnyHrac).getZivoty() <= 0){
                    vyhodenyHraci.add(hraci.get(aktualnyHrac));
                    this.vykonajAkciuPoZomretiHraca(aktualnyHrac, balicek);
                    hraci.remove(hraci.get(aktualnyHrac));
                    if (aktualnyHrac == hraci.size() - 1) {
                        aktualnyHrac = 0;
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private boolean skontrolujVazenie(Balicek balicek) {
        for (Karta karta : hraci.get(aktualnyHrac).getVylozeneKarty()) {
            if (karta instanceof Vazenie) {
                boolean usiel;
                usiel = ((Vazenie) karta).vykonajEfekt();
                balicek.getBalicek().add(karta);
                hraci.get(aktualnyHrac).getVylozeneKarty().remove(karta);
                if (usiel) {
                    return false;
                } else {
                    dalsiHracNaRade();
                    return true;
                }
            }
        }

        return false;
    }

    private void vitazAPorazeny(){
        System.out.println(ANSI_RED + "--------------------------------------------------------------");
        System.out.println("HRACI VYPADLI V TOMTO PORADI: ");
        for (int i = 0; i < vyhodenyHraci.size(); i++){
            System.out.println((i+1) + ". " + vyhodenyHraci.get(i).getMeno());
        }
        System.out.println("\n*--- VITAZOM TEJTO HRY SA STAVA: " + hraci.get(0).getMeno() + " ---*" + ANSI_RESET);
    }
}
