/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.input.menu.utili;

import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.input.Formattazione;

/**
 *
 * @author TTT
 */
public class TreeSystem {

    public TreeSystem() {

    }

    public void printTree() {
        Formattazione.printOut("Struttura del sistema:", true, false);
        System.out.println(printTree(Stella.getIstance()));
    }

    public String printTree(CorpoCeleste ic) {
        if (ic == null) {
            Formattazione.printOut("Nessun corpo celeste selezionato", true, true);
            return "";
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        printCelestialOrbit(ic, indent, sb);
        return sb.toString();
    }

    private void printCelestialOrbit(CorpoCeleste ic, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(ic);
        sb.append("\n");
        ic.getOrbita().forEach(c -> {
            if (c.getOrbita().size() > 0) {
                printCelestialOrbit(c, indent + 1, sb);
            } else {
                printCelestial(c, indent + 1, sb);
            }
        });
    }

    private void printCelestial(CorpoCeleste ic, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(ic);
        sb.append("\n");
    }

    private String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }

}
