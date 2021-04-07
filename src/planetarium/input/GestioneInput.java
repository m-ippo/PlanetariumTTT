package planetarium.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GestioneInput {
    private static final Scanner input = new Scanner(System.in);
    public static double leggiDouble () {
        double valoreLetto;
        try {
            valoreLetto = input.nextDouble();

        } catch (InputMismatchException ime){
            System.out.println("Inserire un valore corretto");
            valoreLetto = 0;
        }

        return valoreLetto;
    }


}
