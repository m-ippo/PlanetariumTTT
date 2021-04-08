package planetarium.input.menu;

import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.registro.Registro;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.utils.OutputPicker;
import planetarium.input.Formattazione;
import planetarium.input.GestioneInput;
import planetarium.input.InputOggetti;
import planetarium.input.menu.utili.Coppia;
import planetarium.input.menu.utili.FuturaAzioneMenu;

import java.util.ArrayList;
import planetarium.input.menu.utili.TreeSystem;

public class Menu {

    private ArrayList<Coppia<String, FuturaAzioneMenu>> menu;

    public Menu() {
        this.menu = new ArrayList<>();
        init();
    }

    private void init() {
        Formattazione.incrementaIndentazioni();
        menu.add(new Coppia<>("Esci", () -> {
            System.exit(0);
        }));
        if (GestioneSistema.getIstance() != null) {
            aggiungiOpzioniBase();
        } else {
            menu.add(new Coppia<>("Crea il sistema ", () -> {
                GestioneSistema lgs = InputOggetti.leggiGestioneSistema();
                if (lgs != null) {
                    aggiungiOpzioniBase();
                }
            }));
        }
    }

    private void aggiungiOpzioniBase() {

        menu.add(new Coppia<>("Inserisci un corpo celeste", () -> {
            CorpoCeleste cc = InputOggetti.leggiCorpoCeleste();
            if (cc != null) {
                Formattazione.printOut("Corpo registrato correttamente ", true, false);
                Formattazione.printOut(OutputPicker.getIstance().getOnCreate(), true, false);
            } else {
                Formattazione.printOut("Errore nella registrazione ", true, true);
            }
        }));
        menu.add(new Coppia<>("Rimuovi corpo celeste", () -> {
            CorpoCeleste rimuovi = InputOggetti.ricercaCorpoCeleste();
            if (rimuovi != null) {
                Boolean risposta = GestioneInput.leggiBoolean("Sei sicuro di volerlo eliminare? ");
                if (risposta) {
                    rimuovi.distruggi();
                    Formattazione.printOut(rimuovi.getNome() + " eliminato correttamente! ", true, false);
                    Formattazione.printOut(OutputPicker.getIstance().getOnDelete(), true, false);
                }
            }
        }));

        menu.add(new Coppia<>("Mostra luna di un pianeta ", () -> {
            InputOggetti.stampaLune();
        }));
        menu.add(new Coppia<>("Visualizza gerarchia corpo celeste ", () -> {
            InputOggetti.gerarchia();
        }));
        menu.add(new Coppia<>("Visualizza percorso tra due punti ", () -> {
            InputOggetti.percorso();
        }));
        menu.add(new Coppia<>("Mostra registro", () -> {
            Formattazione.printOut("Registro: ", true, false);
            Formattazione.incrementaIndentazioni();
            Registro.stampaRegistro();
            Formattazione.decrementaIndentazioni();
        }));
        menu.add(new Coppia<>("Mostra sistema", () -> {
            TreeSystem ts = new TreeSystem();
            ts.printTree();
        }));
        menu.add(new Coppia<>("Trova corpo celeste",()->{
            InputOggetti.mostraCC();
        }));
        menu.add(new Coppia<>("Calcola somma delle masse e somma pesata delle posizioni",()->{
            InputOggetti.calcolaMassa();
        }));
        menu.add(new Coppia<>("Calcola collisioni ",()->{
            InputOggetti.controllaCollisioni();
        }));
    }

    public void stampaMenu() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
        for (int i = 0; i < menu.size(); i++) {
            Formattazione.printOut("[" + (i + 1) + "] " + menu.get(i).getChiave(), true, false);
        }
        Formattazione.incrementaIndentazioni();
        int operazioneScelta;
        do {
            operazioneScelta = GestioneInput.leggiInteger("inserisci operazione: ");
        } while (operazioneScelta < 1 || operazioneScelta > menu.size());
        Formattazione.decrementaIndentazioni();
        eseguiOperazione(operazioneScelta - 1);
    }

    private void eseguiOperazione(int operazione) {
        menu.get(operazione).getValore().onSelezionato();
        stampaMenu();
    }


}
