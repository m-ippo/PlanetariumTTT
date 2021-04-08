/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.registro;

import java.util.HashMap;
//import planetarium.console.GeneralFormatter;
import planetarium.contents.registro.abstracts.ElementoRegistrabile;
import planetarium.contents.registro.eventi.EventoRegistro;
import planetarium.contents.registro.eccezioni.EccezioneRegistro;
import planetarium.input.Formattazione;

/**
 * Classe non istanziabile che permette di registrare nuovi elementi nel
 * planetario.
 *
 * @author TTT
 */
public final class Registro {

    private static final Registro register_key = new Registro();
    private static long currentID = 0;

    private static final HashMap<Long, ElementoRegistrabile> registry = new HashMap<>();
    private static final HashMap<String, Long> secondary_registry = new HashMap<>();

    private Registro() {

    }

    /**
     * Serve solo come test.
     *
     * @return <code>true</code>, sempre
     */
    public boolean onCall() {
        return true;
    }

    /**
     * Registra un nuovo elemento e gli assegna l'ID.L'oggetto deve estendere la
     * classe astratta {@link ElementoRegistrabile}.
     *
     * @param re L'oggetto da registrare.
     * @param entry_name Il nome con cui l'oggetto viene registrato (unico,
     * altrimenti la registrazione fallisce).
     * @return <code>true</code> se la registrazione avviene con successo,
     * altrimenti <code>false</code>.
     */
    public static boolean registraElemento(ElementoRegistrabile re, String entry_name) {
        try {
            if (re != null && !re.isRegistrato()
                    && entry_name != null && !"".equals(entry_name.trim()) && !secondary_registry.containsKey(entry_name)) {
                re.registra(currentID, register_key);
                re.setNomeElemento(entry_name, register_key);
                secondary_registry.put(entry_name, currentID);
                registry.put(currentID++, re);
                EventoRegistro.getIstance().elementRegistered(re, register_key);
                return true;
            }
            return false;
        } catch (EccezioneRegistro ex) {
            return false;
        }
    }

    /**
     * Rimuove un elemento dal registro. Gli elementi rimossi non vengono più
     * considerati.
     *
     * @param re L'elemento che deve esser rimosso dal registro ed escluso dai
     * calcoli.
     * @return <code>true</code> se viene rimosso con successo, altrimenti
     * <code>false</code>.
     */
    public static boolean rimuoviElemento(ElementoRegistrabile re) {
        if (re != null && registry.containsValue(re)) {
            registry.remove(re.getID());
            secondary_registry.remove(re.getNomeElemento());
            re.rimuovi(register_key);
            EventoRegistro.getIstance().elementRemoved(re, register_key);
            return true;
        }
        return false;
    }

    /**
     * Ritorna un {@link ElementoRegistrabile} dato l'ID corrispondente.
     *
     * @param ID L'ID da cercare.
     * @return {@code null} se non esiste nessun elemento con quell'ID
     * altrimenti un riferimento ad un {@link ElementoRegistrabile}.
     */
    public static ElementoRegistrabile getElemento(long ID) {
        return registry.get(ID);
    }

    /**
     * Ritorna un {@link ElementoRegistrabile} dato il nome di registrazione
     * corrispondente.
     *
     * @param name Il nome da cercare.
     * @return {@code null} se non esiste nessun elemento con quel nome
     * altrimenti un riferimento ad un {@link ElementoRegistrabile}.
     */
    public static ElementoRegistrabile getElemento(String name) {
        Long val_id = secondary_registry.get(name);
        return val_id != null ? registry.get(val_id) : null;
    }

    /**
     * Stampa una rappresentazione a lista del registro:
     * <p>
     * ID | {@link ElementoRegistrabile#toString() }
     */
    public static void stampaRegistro() {
        registry.forEach((t, u) -> {
            Formattazione.printOut("ID: " + t + "\t\t" + u.toString(), true, false);
        });
    }

}
