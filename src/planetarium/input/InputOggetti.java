package planetarium.input;

import planetarium.contents.corpicelesti.Luna;
import planetarium.contents.corpicelesti.Pianeta;
import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.system.posizione.Posizione;

public class InputOggetti {
    public static Posizione leggiPosizione(){
        System.out.println("Inserisci posizione");
        Double x = GestioneInput.leggiDouble("x: ");
        Double y = GestioneInput.leggiDouble("y: ");
        return new Posizione(x,y);
    }
    public static Pianeta leggiPianeta(){
        System.out.println("Registra nuovo pianeta: ");
        String nome = GestioneInput.leggiString("Inserisci il nome: ");
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ");
        Posizione posizione = leggiPosizione();
        return new Pianeta(nome,massa,posizione);
    }
    public static Luna leggiLuna(){
        System.out.println("Registra nuova luna: ");
        String nome = GestioneInput.leggiString("Inserisci il nome: ");
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ");
        Posizione posizione = leggiPosizione();
        return new Luna(nome,massa,posizione);
    }
    public static Stella leggiStella() {
        System.out.println("Registra la stella: ");
        String nome = GestioneInput.leggiString("Inserisci il nome: ");
        Double massa = GestioneInput.leggiDouble("Inserisci la Massa: ");
        Posizione posizione = leggiPosizione();
        return Stella.generateIstance(nome, massa, posizione);
    }

}
