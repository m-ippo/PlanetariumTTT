package planetarium.input;

public class Formattazione {
    private static int INDENTAZIONI = 0;
    public static void incrementaIndentazioni(){
        INDENTAZIONI++;
    }
    public static void decrementaIndentazioni(){
        INDENTAZIONI--;
    }
    public static String getIndentazioni(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<INDENTAZIONI;i++){
            sb.append("\t");
        }return sb.toString();
    }
    public static void printOut(String messaggio,boolean nuovaLinea, boolean errore){
        String msg = getIndentazioni()+messaggio+(nuovaLinea?"\n":"");
        if (errore){
            System.err.print(msg);
        }else {
            System.out.print(msg);
        }
    }
}
