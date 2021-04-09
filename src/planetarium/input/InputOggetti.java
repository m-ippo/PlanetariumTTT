package planetarium.input;

import planetarium.contents.corpicelesti.Luna;
import planetarium.contents.corpicelesti.Pianeta;
import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.posizione.Posizione;

public class InputOggetti {

    public static Posizione leggiPosizione() {
        Formattazione.printOut("Inserisci posizione", true, false);
        Formattazione.incrementaIndentazioni();
        Double x = GestioneInput.leggiDouble("X: ", false);
        Double y = GestioneInput.leggiDouble("Y: ", false);
        Formattazione.decrementaIndentazioni();
        return new Posizione(x, y);
    }

    public static Pianeta leggiPianeta() {
        Formattazione.printOut("Registra nuovo pianeta: ", true, false);
        Formattazione.incrementaIndentazioni();
        String nome = GestioneInput.leggiString("Inserisci il nome: ", true);
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ", true);
        Posizione posizione = leggiPosizione();
        Formattazione.decrementaIndentazioni();
        return new Pianeta(nome, massa, posizione);
    }

    public static Luna leggiLuna() {
        Formattazione.printOut("Registra nuova luna: ", true, false);
        Formattazione.incrementaIndentazioni();
        String nome = GestioneInput.leggiString("Inserisci il nome: ", true);
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ", true);
        Posizione posizione = leggiPosizione();
        Formattazione.decrementaIndentazioni();
        return new Luna(nome, massa, posizione);
    }

    public static Stella leggiStella() {
        Formattazione.printOut("Registra la stella: ", true, false);
        Formattazione.incrementaIndentazioni();
        String nome = GestioneInput.leggiString("Inserisci il nome: ", true);
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ", true);
        Posizione posizione = leggiPosizione();
        Formattazione.decrementaIndentazioni();
        return Stella.generateIstance(nome, massa, posizione);
    }

    public static GestioneSistema leggiGestioneSistema() {
        String nomeSistema = GestioneInput.leggiString("Inserire nome del Sistema: ", true);
        Boolean sn = GestioneInput.leggiBoolean("Vuoi inserire una nuova stella? (altrimenti è generata automaticamente) ");
        if (sn) {
            Formattazione.incrementaIndentazioni();
            leggiStella();
            Formattazione.decrementaIndentazioni();
        }
        return GestioneSistema.getIstance(nomeSistema);
    }

    public static CorpoCeleste leggiCorpoCeleste() {

        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null) {
            Formattazione.printOut("Cosa vuoi inserire?", true, false);
            Formattazione.incrementaIndentazioni();
            Formattazione.printOut("[1] Pianeta", true, false);
            Formattazione.printOut("[2] Luna", true, false);
            Formattazione.incrementaIndentazioni();
            Integer valore = GestioneInput.leggiInteger("Operzione: ");
            switch (valore) {
                case 1:
                    CorpoCeleste pianeta = leggiPianeta();
                    Formattazione.incrementaIndentazioni();
                    gs.aggiungiElementoA(null, pianeta, GestioneInput.leggiBoolean("Le coordinate sono assolute? "));
                    Formattazione.decrementaIndentazioni();
                    Formattazione.decrementaIndentazioni();
                    Formattazione.decrementaIndentazioni();
                    return pianeta;
                case 2:
                    if (gs.haAlmenoUnPianeta()) {
                        CorpoCeleste luna = leggiLuna();
                        Formattazione.incrementaIndentazioni();
                        Formattazione.printOut("Seleziona un padre intorno a cui orbita (solo pianeti):", true, false);
                        CorpoCeleste padre = InputDomande.ricercaCorpoCeleste();
                        Formattazione.incrementaIndentazioni();
                        if (padre != null && padre.getTipo() == TipiCorpiCelesti.PIANETA) {
                            gs.aggiungiElementoA(padre, luna, GestioneInput.leggiBoolean("Le coordinate sono assolute? "));
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            return luna;
                        } else {
                            Formattazione.printOut("Il corpo celeste scelto è inesistente o non è un pianeta.", true, true);
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            return null;
                        }
                    } else {
                        Formattazione.printOut("Non si possono aggiungere lune se non ci sono pianeti.", true, true);
                        Formattazione.decrementaIndentazioni();
                        Formattazione.decrementaIndentazioni();
                        return null;
                    }
                default:
                    Formattazione.printOut("Operazione non valida.", true, true);
                    Formattazione.decrementaIndentazioni();
                    Formattazione.decrementaIndentazioni();
            }
            return null;
        } else {
            Formattazione.printOut("Sistema non ancora creato.", true, true);
            return null;
        }

    }

}
