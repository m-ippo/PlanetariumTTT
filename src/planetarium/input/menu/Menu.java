package planetarium.input.menu;

import planetarium.input.GestioneInput;

import java.util.ArrayList;

public class Menu {
    private ArrayList<String> menu;
    public Menu (){
        this.menu = new ArrayList<>();
        init();
    }
    private void init (){
        menu.add("Crea il sistema");
        menu.add("Inserisci un corpo celeste");
        menu.add("Rimuovi corpo celeste");
    }
    public void stampaMenu(){
        for(int i = 0; i < menu.size(); i++){
            System.out.println("["+(i+1)+"] "+menu.get(i));
        }
        int operazioneScelta;
        do{
            operazioneScelta = GestioneInput.leggiInteger("inserisci operazione: ");
        } while (operazioneScelta<1 || operazioneScelta> menu.size());



    }
}
