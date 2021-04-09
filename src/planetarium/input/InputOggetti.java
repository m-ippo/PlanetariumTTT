package planetarium.input;

import planetarium.contents.corpicelesti.Luna;
import planetarium.contents.corpicelesti.Pianeta;
import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.custom.corpicelesti.MorteNera;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.posizione.Posizione;
import planetarium.contents.system.utils.OutputPicker;

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
                    if (!gs.massimoRaggiunto(null)) {
                        gs.aggiungiElementoA(null, pianeta, GestioneInput.leggiBoolean("Le coordinate sono assolute? "));
                    } else {
                        Formattazione.printOut("Limite massimo di pianeti raggiunti per " + Stella.getIstance().toString(), true, true);
                    }
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
                            if (!gs.massimoRaggiunto(padre)) {
                                gs.aggiungiElementoA(padre, luna, GestioneInput.leggiBoolean("Le coordinate sono assolute? "));
                            } else {
                                Formattazione.printOut("Limite massimo di lune raggiunti per " + padre.toString(), true, true);
                            }
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

    private static boolean isDS_Enabled = false;

    public static void gestisceMorteNera() {
        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null) {
            if (!isDS_Enabled) {
                Formattazione.printOut("Abilita l'utilizzo della Morte Nera.", true, false);
                Formattazione.incrementaIndentazioni();
                Formattazione.printOut("Scegli il pianeta intorno a cui orbitare e la posizione:", true, false);
                CorpoCeleste ic = InputDomande.ricercaCorpoCeleste();
                if (ic != null && ic.getTipo() == TipiCorpiCelesti.PIANETA) {
                    Posizione p = leggiPosizione();
                    Boolean b = GestioneInput.leggiBoolean("La posizione è assoluta? (relativa allo 0 del sistema, altrimenti relativa al corpo celeste padre) ");
                    MorteNera.getIstance().aggiornaPosizione(p);
                    gs.aggiungiElementoA(ic, MorteNera.getIstance(), b);
                    isDS_Enabled = true;
                    Formattazione.decrementaIndentazioni();
                    Formattazione.printOut("La Morte Nera è ora nel sistema.", true, false);
                } else {
                    Formattazione.decrementaIndentazioni();
                }
            } else {
                Formattazione.printOut("Scegli come comandare la stazione spaziale.", true, false);
                Formattazione.incrementaIndentazioni();
                Formattazione.printOut("[1] Cambia posizione.", true, false);
                Formattazione.printOut("[2] Distruggi pianeta", true, false);
                Formattazione.printOut("Inserire il numero corrispettivo all'operazione desiderato (oppure * per annullare):", true, false);
                Formattazione.incrementaIndentazioni();
                Integer op;
                do {
                    op = GestioneInput.leggiInteger("Operazione :");
                } while (op != 1 && op != 2);
                switch (op) {
                    case 1:
                        Formattazione.printOut("Aggiorna la posizione:", true, false);
                        Posizione p = leggiPosizione();
                        MorteNera.getIstance().aggiornaPosizione(p);
                        Formattazione.decrementaIndentazioni();
                        Formattazione.decrementaIndentazioni();
                        break;
                    case 2:
                        Formattazione.printOut("Scegli il corpo celeste da distruggere:", true, false);
                        CorpoCeleste ic = InputDomande.ricercaCorpoCeleste();
                        if (ic != null && ic != MorteNera.getIstance()) {
                            MorteNera.getIstance().toDeath(ic);
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            System.out.println("\n\n");
                            Formattazione.printOut(OutputPicker.getIstance().getOnDelete(), true, false);
                        }
                        break;
                }
            }
        }
    }

}
