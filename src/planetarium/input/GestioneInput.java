package planetarium.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GestioneInput {

    private static final Scanner input = new Scanner(System.in);

    public static double leggiDouble() {
        double valoreLetto = 0.0;
        boolean corretto = false;

        while (!corretto) {
            try {
                valoreLetto = input.nextDouble();
                corretto = true;
            } catch (InputMismatchException ime) {
                input.nextLine();
                System.out.println("Inserire un valore corretto");
            }
        }

        return valoreLetto;
    }

}
