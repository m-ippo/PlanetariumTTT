package planetarium.input;

import planetarium.contents.corpicelesti.Luna;
import planetarium.contents.corpicelesti.Pianeta;
import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.posizione.Posizione;
import planetarium.contents.system.questionable.Interrogabile;

public class InputOggetti {

    public static Posizione leggiPosizione() {
        System.out.println("Inserisci posizione");
        Double x = GestioneInput.leggiDouble("x: ");
        Double y = GestioneInput.leggiDouble("y: ");
        return new Posizione(x, y);
    }

    public static Pianeta leggiPianeta() {
        System.out.println("Registra nuovo pianeta: ");
        String nome = GestioneInput.leggiString("Inserisci il nome: ");
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ");
        Posizione posizione = leggiPosizione();
        return new Pianeta(nome, massa, posizione);
    }

    public static Luna leggiLuna() {
        System.out.println("Registra nuova luna: ");
        String nome = GestioneInput.leggiString("Inserisci il nome: ");
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ");
        Posizione posizione = leggiPosizione();
        return new Luna(nome, massa, posizione);
    }

    public static Stella leggiStella() {
        System.out.println("Registra la stella: ");
        String nome = GestioneInput.leggiString("Inserisci il nome: ");
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ");
        Posizione posizione = leggiPosizione();
        return Stella.generateIstance(nome, massa, posizione);
    }

    public static GestioneSistema leggiGestioneSistema() {
        String nomeSistema = GestioneInput.leggiString("Inserire nome del Sistema: ");
        Boolean sn = GestioneInput.leggiBoolean("Vuoi inserire una nuova stella? (altrimenti è generata automaticamente) ");
        if (sn) {
            leggiStella();
        }
        return GestioneSistema.getIstance(nomeSistema);
    }

    public static CorpoCeleste leggiCorpoCeleste() {

        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null) {
            System.out.println("Cosa vuoi inserire? \n\t[1] Pianeta \n\t[2] Luna");
            Integer valore = GestioneInput.leggiInteger("Operzione: ");
            switch (valore) {
                case 1:
                    CorpoCeleste pianeta = leggiPianeta();
                    gs.aggiungiElementoA(null, pianeta, GestioneInput.leggiBoolean("Le coordinate sono assolute? "));
                    return pianeta;
                case 2:
                    if (gs.haAlmenoUnPianeta()) {
                        CorpoCeleste luna = leggiLuna();
                        CorpoCeleste padre = ricercaCorpoCeleste();
                        if (padre != null && padre.getTipo() == TipiCorpiCelesti.PIANETA) {
                            gs.aggiungiElementoA(padre, luna, GestioneInput.leggiBoolean("Le coordinate sono assolute? "));
                            return luna;
                        } else {
                            System.err.println("Il corpo celeste è inesistente o non è un pianeta ");
                            return null;
                        }
                    } else {
                        System.err.println("Non si possono aggiungere lune se non ci sono pianeti ");
                        return null;
                    }
                default:
                    System.err.println("Operazione non valida");
            }
            return null;
        } else {
            System.err.println("Sistema non ancora creato");
            return null;
        }

    }

    public static CorpoCeleste ricercaCorpoCeleste() {
        System.out.println("Come vuoi cercare il corpo celeste? \n\t[1] Codice ID \n\t[2] Nome");
        Integer scelta;
        do {
            scelta = GestioneInput.leggiInteger("Operazione: ");
        } while (scelta != 1 && scelta != 2);
        if (scelta == 1) {
            return Interrogabile.getCorpoCelesteByID(GestioneInput.leggiLong("Inserisci ID: "));
        } else {
            return Interrogabile.getCorpoCelesteByName(GestioneInput.leggiString("Inserisci nome: "));
        }

    }
}
