package planetarium.input;

import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.posizione.Posizione;
import planetarium.contents.system.questionable.Calcolatore;
import planetarium.contents.system.questionable.Interrogabile;

public class InputDomande {

    public static CorpoCeleste ricercaCorpoCeleste() {
        Formattazione.printOut("Come vuoi cercare il corpo celeste?", true, false);
        Formattazione.incrementaIndentazioni();
        Formattazione.printOut("[1] Codice ID", true, false);
        Formattazione.printOut("[2] Nome", true, false);
        Formattazione.incrementaIndentazioni();
        Integer scelta;
        do {
            scelta = GestioneInput.leggiInteger("Operazione: ");
        } while (scelta != 1 && scelta != 2);
        Formattazione.decrementaIndentazioni();
        if (scelta == 1) {
            CorpoCeleste ccl = Interrogabile.getCorpoCelesteByID(GestioneInput.leggiLong("Inserisci ID: "));
            Formattazione.decrementaIndentazioni();
            return ccl;

        } else {
            CorpoCeleste ccl = Interrogabile.getCorpoCelesteByName(GestioneInput.leggiString("Inserisci nome: ", false));
            Formattazione.decrementaIndentazioni();
            return ccl;
        }

    }

    public static void stampaLune() {
        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null) {
            if (gs.haAlmenoUnPianeta()) {
                Formattazione.printOut("Cerca il pianeta di cui vedere le lune:", true, false);
                Formattazione.incrementaIndentazioni();
                CorpoCeleste cl = ricercaCorpoCeleste();
                Formattazione.decrementaIndentazioni();
                if (cl != null && cl.getTipo() == TipiCorpiCelesti.PIANETA) {
                    StringBuilder sd = new StringBuilder();
                    cl.getOrbita().forEach((luna) -> {
                        sd.append(luna.getNome()).append(", ");
                    });
                    sd.delete(sd.length() - 2, sd.length());
                    Formattazione.printOut("Lista delle lune orbitanti intorno a " + cl.getNome(), true, false);
                    Formattazione.incrementaIndentazioni();
                    Formattazione.printOut(sd.toString(), true, false);
                    Formattazione.decrementaIndentazioni();
                } else {
                    Formattazione.printOut("Errore! Il corpo celeste non esiste o non è un pianeta.", true, true);
                }
            } else {
                Formattazione.printOut("Errore! Inseriere almeno un pianeta.", true, true);
            }
        } else {
            Formattazione.printOut("Sistema non ancora creato.", true, true);
        }

    }

    public static void gerarchia() {
        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null) {
            if (gs.haAlmenoUnPianeta()) {
                Formattazione.printOut("Visualizza l'ordine di appartenenza di un corpo celeste: ", true, false);
                Formattazione.incrementaIndentazioni();
                CorpoCeleste abc = ricercaCorpoCeleste();
                Formattazione.decrementaIndentazioni();
                if (abc != null) {
                    Formattazione.printOut("La gerarchia è: " + Interrogabile.getPercorsoPerCorpoCeleste(abc), true, false);
                } else {
                    Formattazione.printOut("Errore! Il corpo celeste non esiste.", true, true);
                }

            } else {
                Formattazione.printOut("Errore! Inseriere almeno un pianeta.", true, true);
            }
        } else {
            Formattazione.printOut("Sistema non ancora creato.", true, true);
        }
    }

    public static void percorso() {
        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null) {
            if (gs.haAlmenoUnPianeta()) {
                Formattazione.printOut("Calcola percorso tra due corpi celesti ", true, false);
                Formattazione.incrementaIndentazioni();
                Formattazione.printOut("Scegli corpo celeste 1:", true, false);
                CorpoCeleste da = ricercaCorpoCeleste();
                Formattazione.printOut("Scegli corpo celeste 2:", true, false);
                CorpoCeleste a = ricercaCorpoCeleste();
                Formattazione.decrementaIndentazioni();
                if (da != null && a != null) {
                    Formattazione.printOut("Il percorso è: " + Interrogabile.generaPercorsoObbligatorio(da, a), true, false);
                    Formattazione.printOut("La distanza totale è: " + Calcolatore.calcolaDistanza(Interrogabile.trovaPercorso(da, a)), true, false);
                } else {
                    Formattazione.printOut("Errore! Uno dei due corpi celesti non esiste.", true, true);
                }

            } else {
                Formattazione.printOut("Errore! Inseriere almeno un pianeta.", true, true);
            }
        } else {
            Formattazione.printOut("Sistema non ancora creato.", true, true);
        }
    }

    public static void mostraCC() {
        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null) {
            Formattazione.printOut("Seleziona corpo celeste per informazioni ", true, false);
            Formattazione.incrementaIndentazioni();
            CorpoCeleste ricerca = ricercaCorpoCeleste();
            if (ricerca != null) {
                System.out.println();
                Formattazione.printOut("Trovato: " + ricerca.getNome(), true, false);
                Formattazione.incrementaIndentazioni();
                Formattazione.printOut("Massa: " + ricerca.getMassa(), true, false);
                Formattazione.printOut("Tipo: " + ricerca.getTipo(), true, false);
                Formattazione.printOut("POsizione Assoluta: " + ricerca.getPosizione(), true, false);
                if (ricerca.getTipo() != TipiCorpiCelesti.STELLA) {
                    Formattazione.printOut("Padre: " + ricerca.getPadre(), true, false);
                    Formattazione.printOut("Posizione Relativa: " + gs.getGriglia().getPosizioneRelativeToParent(ricerca), true, false);
                }
                Formattazione.decrementaIndentazioni();

            } else {
                Formattazione.printOut("Errore! il corpo celeste non esiste ", true, true);

            }
            Formattazione.decrementaIndentazioni();
        } else {
            Formattazione.printOut("Sistema non ancora creato", true, true);
        }
    }

    public static void calcolaMassa() {
        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null) {
            Double massa = Calcolatore.ricalcolaValori(gs.getUnica_Stella());
            Posizione posizione = gs.getUnica_Stella().getPosizionePesata();
            Formattazione.incrementaIndentazioni();
            Formattazione.printOut("La somma delle masse vale: " + massa, true, false);
            Formattazione.printOut("La somma delle posizioni in base alla massa vale: " + posizione, true, false);
            Formattazione.printOut("Il centro di massa vale: " + Posizione.divide(posizione, massa), true, false);
            Formattazione.decrementaIndentazioni();
        } else {
            Formattazione.printOut("Sistema non ancora creato", true, true);
        }

    }

    public static void controllaCollisioni() {
        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null) {

            if (gs.haAlmenoUnPianeta()) {
                Formattazione.printOut("Controlla la collisione tra due corpi celesti", true, false);
                Formattazione.incrementaIndentazioni();
                CorpoCeleste c1 = ricercaCorpoCeleste();
                CorpoCeleste c2 = ricercaCorpoCeleste();
                Formattazione.decrementaIndentazioni();
                if (c1 != null && c2 != null) {
                    Boolean a = Calcolatore.puoCollidere(c1, c2);
                    Formattazione.printOut("Controllo collisione tra " + c1 + " e " + c2 + ":", true, false);
                    if (a) {
                        Formattazione.printOut("Le orbite hanno almeno un punto di collisione ", true, false);
                    } else {
                        Formattazione.printOut("I due corpi non collidono ", true, false);
                    }
                } else {
                    Formattazione.printOut("Errore! uno dei corpi celesti non esiste ", true, true);
                }
            } else {
                Formattazione.printOut("Errore! inseriere almeno un pianeta ", true, true);
            }
        } else {
            Formattazione.printOut("Sistema non ancora creato", true, true);
        }
    }
}
