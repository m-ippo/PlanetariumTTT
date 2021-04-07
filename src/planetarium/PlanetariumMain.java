package planetarium;

import planetarium.contents.corpicelesti.Luna;
import planetarium.contents.corpicelesti.Pianeta;
import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.corpicelesti.operazioni.Operazioni;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.posizione.Posizione;
import planetarium.input.GestioneInput;

/**
 * @author TTT
 */

public class PlanetariumMain {

    public static void main(String[] args){
        GestioneInput.leggiDouble();

        //commento di prova guygf
/*
        Luna l = new Luna("luna 1", 5677984.54, new Posizione(3,6));
        Luna l1 = new Luna( "luna 2", 5677984.54, new Posizione(3,6));
        Luna l2 = new Luna("luna 3", 5677984.54, new Posizione(3,6));

        Pianeta p = new Pianeta("terra 1", 657348, new Posizione(3,6));
        Pianeta p1 = new Pianeta( "terra 2", 657348, new Posizione(3,6));
        Pianeta p2 = new Pianeta( "terra 3", 657348, new Posizione(3,6));

        Stella s = Stella.generateIstance("sole", 5679846, new Posizione(3,6));

        s.aggiungiCorpoCeleste(p);

        p.aggiungiCorpoCeleste(l);
        p.aggiungiCorpoCeleste(l1);
        p.aggiungiCorpoCeleste(l2);

        p.stampaOrbita();

        l.distruggi();

        p.stampaOrbita();*/

    }
}