/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.input.menu.utili;

import java.io.IOException;
import java.io.InputStream;
import planetarium.input.Formattazione;

/**
 * Stampa l'Aiuto, magari si può fare altro, però boh.
 *
 * @author TTT
 */
public class Aiuto {

    private static final Aiuto h = new Aiuto();

    private Aiuto() {

    }

    public static Aiuto getIstance() {
        return h;
    }

    public void printHelp() {
        try (InputStream create = getClass().getResourceAsStream("/planetarium/resources/phrases/help")) {
            System.out.println(new String(create.readAllBytes()));
        } catch (IOException ioe) {
            Formattazione.printOut("E niente: non c'è nessuna pagina di help: " + ioe.getMessage(), true, true);
        }
    }

}
