package planetarium.input.menu;

import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.system.GestioneSistema;
import planetarium.input.Formattazione;
import planetarium.input.GestioneInput;
import planetarium.input.InputOggetti;
import planetarium.input.menu.utili.Coppia;
import planetarium.input.menu.utili.FuturaAzioneMenu;

import java.util.ArrayList;

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
        menu.add(new Coppia<>("Crea il sistema ", () -> {
            GestioneSistema lgs = InputOggetti.leggiGestioneSistema();
            if (lgs!=null){
                aggiungiOpzioniBase();
            }

        }));


    }

    private void aggiungiOpzioniBase(){
        menu.add(new Coppia<>("Inserisci un corpo celeste", () -> {
            CorpoCeleste cc = InputOggetti.leggiCorpoCeleste();
            if (cc != null) {
                Formattazione.printOut("Corpo registrato correttamente ",true,false);
            } else {
                Formattazione.printOut("Errore nella registrazione ",true,true);
            }
        }));
        menu.add(new Coppia<>("Rimuovi corpo celeste",()->{
            CorpoCeleste rimuovi = InputOggetti.ricercaCorpoCeleste();
            if (rimuovi!=null){
                Boolean risposta = GestioneInput.leggiBoolean("Sei sicuro di volerlo eliminare? ");
                if (risposta){
                    rimuovi.distruggi();
                    Formattazione.printOut(rimuovi.getNome()+" eliminato correttamente! ",true,false);
                }
            }
        }));

        menu.add(new Coppia<>("Mostra luna di un pianeta ",()->{
            InputOggetti.stampaLune();
        }));
    }


    public void stampaMenu() {
        for (int i = 0; i < menu.size(); i++) {
            Formattazione.printOut("[" + (i + 1) + "] " + menu.get(i).getChiave(),true,false);
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
