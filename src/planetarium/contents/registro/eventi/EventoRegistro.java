/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.registro.eventi;

import java.util.ArrayList;
import planetarium.contents.registro.Registro;
import planetarium.contents.registro.abstracts.ElementoRegistrabile;

/**
 * Classe che scatena gli eventi riguardanti il registro.
 *
 * @author TTT
 */
public final class EventoRegistro {

    private static final EventoRegistro re = new EventoRegistro();
    private final ArrayList<ListenerRegistro> listeners;

    private EventoRegistro() {
        listeners = new ArrayList<>();
    }

    /**
     * Restituisce l'unica istanza di questa classe.
     *
     * @return L'istanza di questa classe.
     */
    public static EventoRegistro getIstance() {
        return re;
    }

    /**
     * Aggiungi un {@link ListenerRegistro}.
     *
     * @param rl Listener
     */
    public void addListener(ListenerRegistro rl) {
        listeners.add(rl);
    }

    /**
     * Rimuovi un {@link ListenerRegistro}.
     *
     * @param rl Listener
     */
    public void removeListener(ListenerRegistro rl) {
        listeners.remove(rl);
    }

    /**
     * Avverte tutti i listeners in ascolto che un elemento è stato registrato.
     *
     * @param re L'elemento aggiunto.
     * @param r L'istanza del registro
     */
    public void elementRegistered(ElementoRegistrabile re, Registro r) {
        if (r != null && r.onCall()) {
            listeners.forEach(l -> l.onElementoRegistrato(re));
        }
    }

    /**
     * Avverte tutti i listeners in ascolto che un elemento è stato rimosso dal
     * registro.
     *
     * @param re L'elemento rimosso.
     * @param r L'istanza del registro
     */
    public void elementRemoved(ElementoRegistrabile re, Registro r) {
        if (r != null && r.onCall()) {
            listeners.forEach(l -> l.onElementoRegistrato(re));
        }
    }
}
