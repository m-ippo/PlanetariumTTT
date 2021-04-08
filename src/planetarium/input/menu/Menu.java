package planetarium.input.menu;

import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
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
        menu.add(new Coppia<>("Esci", () -> {
            System.exit(0);
        }));
        menu.add(new Coppia<>("Crea il sistema ", () -> {
            InputOggetti.leggiGestioneSistema();
        }));
        menu.add(new Coppia<>("Inserisci un corpo celeste", () -> {
            CorpoCeleste cc = InputOggetti.leggiCorpoCeleste();
            if (cc != null) {
                System.out.println("Corpo registrato correttamente ");
            } else {
                System.err.println("Errore nella registrazione ");
            }
        }));

    }

    public void stampaMenu() {
        for (int i = 0; i < menu.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + menu.get(i).getChiave());
        }
        int operazioneScelta;
        do {
            operazioneScelta = GestioneInput.leggiInteger("inserisci operazione: ");
        } while (operazioneScelta < 1 || operazioneScelta > menu.size());
        eseguiOperazione(operazioneScelta - 1);
    }

    private void eseguiOperazione(int operazione) {
        menu.get(operazione).getValore().onSelezionato();
        stampaMenu();
    }
}
