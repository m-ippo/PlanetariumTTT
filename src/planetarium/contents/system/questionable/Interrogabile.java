/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.questionable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.registro.Registro;
import planetarium.contents.registro.abstracts.ElementoRegistrabile;

/**
 * Classe di metodi statici che permettono di trovare gli elementi nel
 * planetario.
 *
 * @author TTT
 */
public class Interrogabile {

    private Interrogabile() {
    }

    /**
     * Genera una {@link LinkedList} che parte dal corpo di partenza e arriva
     * fino al corpo di arrivo.
     *
     * @param from Il corpo di partenza
     * @param to Il corpo di arrivo
     * @return La lista ordinata di passaggi.
     */
    public static LinkedList<CorpoCeleste> trovaPercorso(CorpoCeleste from, CorpoCeleste to) {
        LinkedList<CorpoCeleste> to_ret = new LinkedList<>();
        if (from == to) {
            to_ret.add(from);
            return to_ret;
        } else {
            getToCelestial(from, to, to_ret);
        }
        return to_ret;
    }

    /**
     * Dalla lista generata dal metodo {@link Interrogabile#findPath(planetarium.contents.celestials.interfaces.CorpoCeleste, planetarium.contents.celestials.interfaces.CorpoCeleste)
     * } genera la stringa corrispondente.
     *
     * @param from Il corpo celeste da cui si parte.
     * @param to Il corpo celeste a cui si deve arrivare.
     * @return La stringa che rappresenta il percorso.
     */
    public static String generaPercorsoObbligatorio(CorpoCeleste from, CorpoCeleste to) {
        LinkedList<CorpoCeleste> findPath = trovaPercorso(from, to);
        StringBuilder sb = new StringBuilder();
        findPath.stream().forEach(ic -> sb.append(ic.getNome()).append(" > "));
        sb.delete(sb.length() - 3, sb.length() - 1);
        return sb.toString();
    }

    /**
     * Restituisce una lista non modificabile di lune che ruotano intorno ad un
     * corpo celeste.
     *
     * @param ic Il corpo celeste intorno a cui orbitano le lune.
     * @return Lista di lune.
     */
    public static List<CorpoCeleste> getLune(CorpoCeleste ic) {
        return searchByTipiCorpiCelesti(ic, TipiCorpiCelesti.LUNA);
    }

    /**
     * Restituisce una lista non modificabile di pianeti che ruotano intorno ad
     * un corpo celeste.
     *
     * @param ic Il corpo celeste intorno a cui orbitano i pianeti.
     * @return Lista di pianeti.
     */
    public static List<CorpoCeleste> getPianeti(CorpoCeleste ic) {
        return searchByTipiCorpiCelesti(ic, TipiCorpiCelesti.PIANETA);
    }

    private static List<CorpoCeleste> searchByTipiCorpiCelesti(CorpoCeleste ic, TipiCorpiCelesti ct) {
        ArrayList<CorpoCeleste> ics = new ArrayList<>();
        ic.getOrbita().stream().filter(t -> t.getTipo()== ct).forEach(t -> ics.add(t));
        return Collections.unmodifiableList(ics);
    }

    /**
     * Dato un ID cerca di restituire un corpo celestiale. Nel caso non esista
     * l'elemento all'ID specificato o non estende l'interfaccia
     * {@link CorpoCeleste} allora viene ritornato il valore {@code null}.
     *
     * @param ID L'ID di riferimento
     * @return L'istanza di un {@link CorpoCeleste}, altrimenti {@code null}
     */
    public static CorpoCeleste getCorpoCelesteByID(long ID) {
        ElementoRegistrabile re = Registro.getElemento(ID);
        if (re != null && re.getOggettoPadre() instanceof CorpoCeleste) {
            return (CorpoCeleste) re.getOggettoPadre();
        }
        return null;
    }

    /**
     * Dato un nome cerca di restituire un corpo celestiale. Nel caso non esista
     * l'elemento con il nome specificato o non estende l'interfaccia
     * {@link CorpoCeleste} allora viene ritornato il valore {@code null}.
     *
     * @param name Il nome di censimento.
     * @return L'istanza di un {@link CorpoCeleste}, altrimenti {@code null}
     */
    public static CorpoCeleste getCorpoCelesteByName(String name) {
        ElementoRegistrabile re = Registro.getElemento(name);
        if (re != null && re.getOggettoPadre() instanceof CorpoCeleste) {
            return (CorpoCeleste) re.getOggettoPadre();
        }
        return null;
    }

    /**
     * Calcola il percorso partendo da un elemento e procedendo a ritroso.
     *
     * @param id L'ID dell'elemento
     * @return La stringa che rappresenta il percorso al corpo.
     */
    public static String getPercorsoPerCorpoCeleste(long id) {
        ElementoRegistrabile entry = Registro.getElemento(id);
        if (entry != null) {
            Object o = entry.getOggettoPadre();
            if (o != null && o instanceof CorpoCeleste) {
                CorpoCeleste as_celestial = (CorpoCeleste) o;
                return getToParentPath(as_celestial);
            }
        }
        return "Nessun dato al record : " + id;
    }

    /**
     * Calcola il percorso partendo da un elemento e procedendo a ritroso.
     *
     * @param ic Il corpo celeste da trovare
     * @return La stringa che rappresenta il percorso al corpo.
     */
    public static String getPercorsoPerCorpoCeleste(CorpoCeleste ic) {
        if (ic != null) {
            return getToParentPath(ic);
        }
        return "Nessun corpo celeste trovato.";
    }

    private static String getToParentPath(CorpoCeleste ic) {
        String s = ic.toString();
        if (ic.getPadre()!= null) {
            s = getToParentPath(ic.getPadre()) + " > " + s;
        }
        return s;
    }

    private static void getToCelestial(CorpoCeleste from, CorpoCeleste to, LinkedList<CorpoCeleste> path) {
        if (from != null && to != null && path != null) {
            if (to == from) {
                path.add(from);
                return;
            }
            if (from.contiene(to)) {
                path.add(from);
                getToCelestial(from.getFiglio(to).get(), to, path);
            } else {
                path.add(from);
                getToCelestial(from.getPadre(), to, path);
            }
        }
    }

}
