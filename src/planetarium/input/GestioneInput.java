package planetarium.input;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class GestioneInput {

    private static final Scanner input = new Scanner(System.in);

    public static Double leggiDouble(String messaggio) {
        Double valoreLetto = null;
        boolean corretto = false;
        Formattazione.printOut(messaggio, false, false);
        Formattazione.incrementaIndentazioni();
        while (!corretto) {
            try {
                valoreLetto = input.nextDouble();
                corretto = true;
            } catch (InputMismatchException ime) {
                input.nextLine();
                Formattazione.printOut("Errore! " + messaggio, false, true);
            }
        }
        Formattazione.decrementaIndentazioni();
        input.nextLine();
        return valoreLetto;
    }

    public static Integer leggiInteger(String messaggio) {
        Integer valoreLetto = null;
        boolean corretto = false;
        Formattazione.printOut(messaggio, false, false);
        Formattazione.incrementaIndentazioni();
        while (!corretto) {
            try {
                valoreLetto = input.nextInt();
                corretto = true;
            } catch (InputMismatchException ime) {
                input.nextLine();
                Formattazione.printOut("Errore! " + messaggio, false, true);
            }
        }
        Formattazione.decrementaIndentazioni();
        input.nextLine();
        return valoreLetto;
    }

    public static Long leggiLong(String messaggio) {
        Long valoreLetto = null;
        boolean corretto = false;
        Formattazione.printOut(messaggio, false, false);
        Formattazione.incrementaIndentazioni();
        while (!corretto) {
            try {
                valoreLetto = input.nextLong();
                corretto = true;
            } catch (InputMismatchException ime) {
                input.nextLine();
                Formattazione.printOut("Errore! " + messaggio, false, true);
            }
        }
        Formattazione.decrementaIndentazioni();
        input.nextLine();
        return valoreLetto;
    }

    public static String leggiString(String messaggio, boolean generabile) {
        String valoreLetto = null;
        boolean corretto = false;
        Formattazione.printOut(messaggio + (generabile ? " (Premi invio per un nome casuale) " : ""), false, false);
        Formattazione.incrementaIndentazioni();
        while (!corretto) {
            valoreLetto = input.nextLine();
            if (valoreLetto != null && (generabile ? generabile : !"".equals(valoreLetto.trim()))) {
                corretto = true;
            } else {
                Formattazione.printOut("Errore! " + messaggio, false, true);
            }
        }
        Formattazione.decrementaIndentazioni();
        return valoreLetto;
    }

    public static Boolean leggiBoolean(String messaggio) {
        Boolean valoreLetto = null;
        boolean corretto = false;
        Formattazione.printOut(messaggio + "[s/n]", false, false);
        Formattazione.incrementaIndentazioni();
        while (!corretto) {
            String temp = input.nextLine();
            if (Objects.equals(temp, "s")) {
                corretto = true;
                valoreLetto = true;
            } else if (Objects.equals(temp, "n")) {
                valoreLetto = false;
                corretto = true;
            } else {
                Formattazione.printOut("Errore! " + messaggio + "[s/n]", false, true);
            }
        }
        Formattazione.decrementaIndentazioni();
        return valoreLetto;
    }
}
