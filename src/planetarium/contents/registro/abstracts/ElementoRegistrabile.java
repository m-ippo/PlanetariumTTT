/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.registro.abstracts;

import planetarium.contents.registro.Registro;
import planetarium.contents.registro.eccezioni.EccezioneRegistro;

/**
 * Classe che deve essere implementata da tutti gli oggetti che si vogliono fare
 * registrare al planetario.
 *
 * @author TTT
 */
public abstract class ElementoRegistrabile {

    private long ID = -1;
    private boolean registrato = false;
    private Object me;
    private String nome_elemento = null;

    /**
     * L'ID è assegnato tramite {@link }. Di default l'ID che viene ritornato è
     * -1, altrimenti se il corpo celeste è stato registrato viene ritornato
     * l'ID relativo.
     *
     * @return L'ID del corpo celeste se è stato registrato, altrimenti -1.
     */
    public final Long getID() {
        return ID;
    }

    /**
     * Un oggetto deve essere registrato prima di poter essere usato nel
     * planetario. Un corpo cleste viene registrato quando gli viene assegnato
     * un ID.
     *
     * @return <code>true</code> se è stato registrato, altrimenti
     * <code>false</code>
     */
    public final boolean isRegistrato() {
        return registrato;
    }

    /**
     * Registra il corpo celestiale. Solo la classe {@link Registro} tramite il
     * metodo {@link Registro#registerCelestial(planetarium.contents.registry.abstracts.RegistrableEntry, java.lang.String)
     * } può registrare l'ID.
     *
     * @param id Il nuovo ID
     * @param register L'unica instanza di registra.
     */
    public final void registra(long id, Registro register) {
        if (register != null && register.onCall()) {
            if (id >= 0) {
                registrato = true;
                ID = id;
                return;
            } else {
                throw new EccezioneRegistro("L'ID assegnato non è valido.");
            }
        }
        throw new EccezioneRegistro("Il registro non è valido.");
    }

    /**
     * Serve per rimuovere l'oggetto dal registro.
     *
     * @param register Il registro.
     */
    public final void rimuovi(Registro register) {
        if (register != null && register.onCall()) {
            if (registrato) {
                registrato = false;
                ID = -1;
                return;
            } else {
                throw new EccezioneRegistro("L'elemento non è stato registrato.");
            }
        }
        throw new EccezioneRegistro("Il registro non è valido.");
    }

    /**
     * Ritorna l'oggetto che viene definito registrabile.
     *
     * @return L'oggetto.
     */
    public final Object getOggettoPadre() {
        return me;
    }

    /**
     * Imposta l'oggetto che viene definito registrabile.
     *
     * @param o L'oggetto che viene restituito per eseguire i cast.
     */
    public final void setOggettoPadre(Object o) {
        if (me == null) {
            me = o;
        }
    }

    /**
     * Imposta (solo una volta) il nome dell'entrata.
     *
     * @param name Il nome.
     * @param register L'unica instanza di registra.
     */
    public final void setNomeElemento(String name, Registro register) {
        if (register != null && register.onCall() && nome_elemento == null) {
            nome_elemento = name;
        }
    }

    /**
     * Restituisce il nome con cui è stato registrato quest'oggetto
     * all'assegnazione del codice identificativo del registro.
     *
     * @return Il nome dell'entry
     */
    public final String getNomeElemento() {
        return nome_elemento;
    }

}
