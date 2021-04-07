/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.questionable;

import java.util.LinkedList;
import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.system.posizione.Posizione;

/**
 * Classe di metodi statici che permettono il calcolo tramite i corpi celesti.
 *
 * @author TTT
 */
public class Calcolatore {

    public static final double THRESHOLD = 0.000001;

    /**
     * Calcola le masse pesate.
     *
     * @param ic Il corpo da controllare (e la sua orbita).
     * @return un array di 3 componenti: La massa totale, la massa lungo l'asse
     * X e una lungo Y.
     * @deprecated Da sostituire con un metodo che faccia utilizzo
     * dell'interfaccia {@link CalcSupport}
     */
    @Deprecated
    public static double[] calcMassaPesata(CorpoCeleste ic) {
        double sommaMasse = ic.getMassa();
        double sommaX = ic.getMassa() * ic.getPosizione().getX();
        double sommaY = ic.getMassa() * ic.getPosizione().getY();
        if (ic.getOrbita().size() > 0) {
            for (CorpoCeleste sic : ic.getOrbita()) {
                double[] triplet = calcMassaPesata(sic);
                sommaMasse += triplet[0];
                sommaX += triplet[1];
                sommaY += triplet[2];
            }
        }
        return new double[]{sommaMasse, sommaX, sommaY};
    }

    /**
     * Calcola la somma delle masse solo se ci sono state delle modifiche al
     * sistema.
     *
     * @param ic
     * @return
     */
    public static double ricalcolaValori(CorpoCeleste ic) {
        double final_mass = ic.getMassa();
        if (ic.deveEssereRicalcolato()) {
            Posizione final_wpos = Posizione.multiply(ic.getPosizione(), final_mass);
            for (CorpoCeleste sic : ic.getOrbita()) {
                final_mass += ricalcolaValori(sic);
                final_wpos = Posizione.sum(final_wpos, sic.getPosizionePesata());
            }
            ic.setMassaCalcolata(final_mass);
            ic.setPosizionePesata(final_wpos);
        } else {
            /*for (CorpoCeleste sic : ic.getOrbita()) {
                if (sic.deveEssereRicalcolato()) {
                    final_mass += ricalcolaValori(sic);
                }
            }*/
            final_mass = ic.getMassaCalcolata();
        }
        return final_mass;
    }

    /**
     * Calcola la distanza tra le posizioni dei centri di due corpi celestiali.
     *
     * @param ic1 Il celestiale 1
     * @param ic2 Il celestiale 2
     * @return La distanza da percorrere.
     */
    public static double calcolaDistanza(CorpoCeleste ic1, CorpoCeleste ic2) {
        return Math.sqrt(Math.pow(ic1.getPosizione().getX() - ic2.getPosizione().getX(), 2.0) + Math.pow(ic1.getPosizione().getY() - ic2.getPosizione().getY(), 2.0));
    }

    /**
     * Calcola la lunghezza del percorso da fare dato un array ordinato (secondo
     * il percorso scelto) dei pianeti.
     *
     * @param ordered_list Lista ordinata di pianeti
     * @return La somma delle distanze fatte a coppie di celesti.
     */
    public static double calcolaDistanza(LinkedList<CorpoCeleste> ordered_list) {
        double somma = 0.0;
        for (int i = 0; i < ordered_list.size() - 1; i++) {
            somma += Calcolatore.calcolaDistanza(ordered_list.get(i), ordered_list.get(i + 1));
        }
        return somma;
    }

    /**
     * Calcola il raggio dell'orbita di un celeste intorno al suo corpo "padre".
     *
     * @param ic Il corpo celeste.
     * @return La distanza del corpo celeste dal padre, altrimenti 0.
     */
    public static double calcolaRaggioOrbita(CorpoCeleste ic) {
        if (ic.getPadre()!= null) {
            CorpoCeleste parent = ic.getPadre();
            return Calcolatore.calcolaDistanza(ic, parent);
        }
        return 0.0;
    }

    /**
     * Controlla se due corpi possono collidere tra loro. Due corpi collidono se
     * la loro distanza dalla stella è uguale.
     *
     * @param ic1 Il primo corpo celeste
     * @param ic2 Il secondo corpo celeste
     * @return {@code true} nel caso i due corpi potrebbero collidere.
     */
    public static boolean puoCollidere(CorpoCeleste ic1, CorpoCeleste ic2) {
        Stella main = Stella.getIstance();
        return Math.abs(Calcolatore.calcolaDistanza(main, ic1) - Calcolatore.calcolaDistanza(main, ic2)) < THRESHOLD;
    }

}
