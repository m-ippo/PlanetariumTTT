package planetarium.input;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class GestioneInput {

    private static final Scanner input = new Scanner(System.in);

    public static Double leggiDouble(String messaggio) {
        Double valoreLetto = null;
        boolean corretto = false;
        System.out.print(messaggio);

        while (!corretto) {
            try {
                valoreLetto = input.nextDouble();
                corretto = true;
            } catch (InputMismatchException ime) {
                input.nextLine();
                System.err.print("Errore! "+ messaggio);
            }
        }
        input.nextLine();
        return valoreLetto;
    }
    public static Integer leggiInteger(String messaggio) {
        Integer valoreLetto = null;
        boolean corretto = false;
        System.out.print(messaggio);

        while (!corretto) {
            try {
                valoreLetto = input.nextInt();
                corretto = true;
            } catch (InputMismatchException ime) {
                input.nextLine();
                System.err.print("Errore! "+ messaggio);
            }
        }
        input.nextLine();
        return valoreLetto;
    }

    public static Long leggiLong(String messaggio) {
        Long valoreLetto = null;
        boolean corretto = false;
        System.out.print(messaggio);

        while (!corretto) {
            try {
                valoreLetto = input.nextLong();
                corretto = true;
            } catch (InputMismatchException ime) {
                input.nextLine();
                System.err.print("Errore! "+messaggio);
            }
        }
        input.nextLine();
        return valoreLetto;
    }

    public static String leggiString(String messaggio) {
        String valoreLetto = null;
        boolean corretto = false;
        System.out.print(messaggio);

        while (!corretto) {

            valoreLetto = input.nextLine();
            if(valoreLetto!=null && valoreLetto.trim()!=""){
                corretto=true;
            }else{
                System.err.print("Errore! "+messaggio);
            }
        }

        return valoreLetto;
    }

    public static Boolean leggiBoolean(String messaggio) {
        Boolean valoreLetto = null;
        boolean corretto = false;
        System.out.print(messaggio + "[s/n]");
        while (!corretto) {
            String temp = input.nextLine();
            if(Objects.equals(temp, "s")){
                corretto=true;
                valoreLetto=true;

            }else if (Objects.equals(temp, "n")){
                valoreLetto=false;
                corretto=true;

            }
            else{
                System.err.print("Errore! "+messaggio + "[s/n]");
            }
        }

        return valoreLetto;
    }
}
