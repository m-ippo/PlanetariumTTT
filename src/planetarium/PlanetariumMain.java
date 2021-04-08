package planetarium;

import planetarium.contents.corpicelesti.Luna;
import planetarium.contents.corpicelesti.Pianeta;
import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.corpicelesti.operazioni.Operazioni;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.posizione.Posizione;
import planetarium.input.GestioneInput;
import planetarium.input.InputOggetti;
import planetarium.input.menu.Menu;

/**
 * @author TTT
 */
public class PlanetariumMain {

    public static void main(String[] args) {
        Stella.generateIstance("Stella", 30, new Posizione(0, 0));
        GestioneSistema gs = GestioneSistema.getIstance(null);
        Pianeta pianeta1 = new Pianeta("Pianeta1", 5, new Posizione(0, -3));
        Pianeta pianeta2 = new Pianeta("Pianeta2", 7, new Posizione(3, 3));
        Luna luna1 = new Luna("Luna1", 1, new Posizione(-1, -4));
        Luna luna2 = new Luna("Luna2", 2, new Posizione(2, 3));
        Luna luna3 = new Luna("Luna3", 1, new Posizione(4, 4));

        gs.aggiungiElementoA(null, pianeta1, true);
        gs.aggiungiElementoA(null, pianeta2, true);
        gs.aggiungiElementoA(pianeta1, luna1, true);
        gs.aggiungiElementoA(pianeta2, luna2, true);
        gs.aggiungiElementoA(pianeta2, luna3, true);
        
        Menu nuovoMenu = new Menu();
        nuovoMenu.stampaMenu();

    }
}
