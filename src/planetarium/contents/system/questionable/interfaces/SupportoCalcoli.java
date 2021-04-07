/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.questionable.interfaces;

import planetarium.contents.system.posizione.Posizione;
import planetarium.contents.system.questionable.Calcolatore;

/**
 *
 * @author TTT
 */
public interface SupportoCalcoli {

    /**
     * Serve per diminuire il carico di lavoro del programma: la classe
     * {@link Calcolatore} userà i dati già presenti se questo metodo resituisce
     * {@code false} altrimenti ricalcola la massa di tutti i corpi celesti che
     * sono sulla sua orbita.
     *
     * @return {@code true} se bisogna ricalcolare i valori, altrimenti
     * {@code false}.
     */
    public boolean deveEssereRicalcolato();

    /**
     * Imposta il nuovo valore della massa ricalcolata.
     *
     * @param calculated_mass La nuova massa.
     */
    public void setMassaCalcolata(double calculated_mass);

    /**
     * Ritorna il valore della massa calcolata.
     *
     * @return La massa.
     */
    public double getMassaCalcolata();

    /**
     * Imposta la massa pesata delle posizioni.
     *
     * @param p La posizione pesata del corpo.
     */
    public void setPosizionePesata(Posizione p);

    /**
     * Ritorna la massa pesata delle posizioni.
     *
     * @return La posizione in base alla massa.
     */
    public Posizione getPosizionePesata();
}
