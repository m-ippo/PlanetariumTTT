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
        Formattazione.printOut("Inserisci posizione",true,false);
        Formattazione.incrementaIndentazioni();
        Double x = GestioneInput.leggiDouble("x: ");
        Double y = GestioneInput.leggiDouble("y: ");
        Formattazione.decrementaIndentazioni();
        return new Posizione(x, y);
    }

    public static Pianeta leggiPianeta() {
        Formattazione.printOut("Registra nuovo pianeta: ",true,false);
        Formattazione.incrementaIndentazioni();
        String nome = GestioneInput.leggiString("Inserisci il nome: ");
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ");
        Posizione posizione = leggiPosizione();
        Formattazione.decrementaIndentazioni();
        return new Pianeta(nome, massa, posizione);
    }

    public static Luna leggiLuna() {
        Formattazione.printOut("Registra nuova luna: ",true,false);
        Formattazione.incrementaIndentazioni();
        String nome = GestioneInput.leggiString("Inserisci il nome: ");
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ");
        Posizione posizione = leggiPosizione();
        Formattazione.decrementaIndentazioni();
        return new Luna(nome, massa, posizione);
    }

    public static Stella leggiStella() {
        Formattazione.printOut("Registra la stella: ",true,false);
        Formattazione.incrementaIndentazioni();
        String nome = GestioneInput.leggiString("Inserisci il nome: ");
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ");
        Posizione posizione = leggiPosizione();
        Formattazione.decrementaIndentazioni();
        return Stella.generateIstance(nome, massa, posizione);
    }

    public static GestioneSistema leggiGestioneSistema() {
        String nomeSistema = GestioneInput.leggiString("Inserire nome del Sistema: ");
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
            Formattazione.printOut("Cosa vuoi inserire?",true,false);
            Formattazione.incrementaIndentazioni();
            Formattazione.printOut("[1] Pianeta",true,false);
            Formattazione.printOut("[2] Luna",true,false);
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
                        CorpoCeleste padre = ricercaCorpoCeleste();
                        Formattazione.incrementaIndentazioni();
                        if (padre != null && padre.getTipo() == TipiCorpiCelesti.PIANETA) {
                            gs.aggiungiElementoA(padre, luna, GestioneInput.leggiBoolean("Le coordinate sono assolute? "));
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            return luna;
                        } else {
                            Formattazione.printOut("Il corpo celeste è inesistente o non è un pianeta ",true,true);
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            Formattazione.decrementaIndentazioni();
                            return null;
                        }
                    } else {
                        Formattazione.printOut("Non si possono aggiungere lune se non ci sono pianeti ", true , true);
                        Formattazione.decrementaIndentazioni();
                        Formattazione.decrementaIndentazioni();
                        return null;
                    }
                default:
                    Formattazione.printOut("Operazione non valida",true,true);
                    Formattazione.decrementaIndentazioni();
                    Formattazione.decrementaIndentazioni();
            }
            return null;
        } else {
           Formattazione.printOut("Sistema non ancora creato",true,true);
            return null;
        }

    }

    public static CorpoCeleste ricercaCorpoCeleste() {
        Formattazione.printOut("Come vuoi cercare il corpo celeste?",true,false);
        Formattazione.incrementaIndentazioni();
        Formattazione.printOut("[1] Codice ID",true,false);
        Formattazione.printOut("[2] Nome",true,false);
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
            CorpoCeleste ccl= Interrogabile.getCorpoCelesteByName(GestioneInput.leggiString("Inserisci nome: "));
            Formattazione.decrementaIndentazioni();
            return ccl;
        }

    }

    public static void stampaLune() {
        GestioneSistema gs = GestioneSistema.getIstance();
        if (gs != null){
            if (gs.haAlmenoUnPianeta()){
                Formattazione.printOut("Cerca il pianeta di cui vedere le lune", true,false);
                Formattazione.incrementaIndentazioni();
                CorpoCeleste cl = ricercaCorpoCeleste();
                Formattazione.decrementaIndentazioni();
                if (cl != null && cl.getTipo()==TipiCorpiCelesti.PIANETA){
                    StringBuilder sd = new StringBuilder();
                    cl.getOrbita().forEach((luna)->{
                        sd.append(luna.getNome()).append(", ");
                    });
                    sd.delete(sd.length()-2, sd.length());
                    Formattazione.printOut("Lista delle lune orbitanti intorno a "+cl.getNome(),true,false);
                    Formattazione.incrementaIndentazioni();
                    Formattazione.printOut(sd.toString(),true,false);
                    Formattazione.decrementaIndentazioni();
                }else {
                    Formattazione.printOut("Errore! il corpo celeste non esiste o non è un pianeta", true, true);
                }
            }else{
                Formattazione.printOut("Errore! inseriere almeno un pianeta ",true,true);
            }
        }else {
            Formattazione.printOut("Sistema non ancora creato",true,true);
        }

    }
}
