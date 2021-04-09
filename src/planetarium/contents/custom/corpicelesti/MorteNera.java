/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.custom.corpicelesti;

import planetarium.contents.corpicelesti.Luna;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.registro.Registro;
import planetarium.contents.system.GestioneSistema;
import planetarium.contents.system.posizione.Posizione;
import planetarium.contents.system.utils.AudioPlayer;
import planetarium.contents.system.utils.NamePicker;
import planetarium.input.Formattazione;

/**
 * E' proprio lei. Inclusi gli effetti audio!
 *
 * @author TTT
 */
public class MorteNera extends Luna {

    private final static MorteNera istance = new MorteNera(NamePicker.getIstance().getName(TipiCorpiCelesti.MORTE_NERA), 147, GestioneSistema.getPosizioneCasuale());

    final AudioPlayer ap = new AudioPlayer();
    private static boolean destroyed = false;

    private MorteNera(String name, double mass, Posizione position) {
        super(name, mass, position);
    }

    public static MorteNera getIstance() {
        return destroyed ? null : istance;
    }

    private CorpoCeleste parent;

    @Override
    public void setPadre(CorpoCeleste ic) {
        if (ic != null) {
            parent = ic;
        }
    }

    @Override
    public CorpoCeleste getPadre() {
        return parent;
    }

    @Override
    public TipiCorpiCelesti getTipo() {
        return TipiCorpiCelesti.MORTE_NERA;
    }

    @Override
    public void distruggi() {
        Formattazione.printOut("Abbandonare la stazione!", true, true);
        ap.playAudioJoined("/planetarium/resources/audio/dtds.wav");
        destroyed = true;
        Registro.rimuoviElemento(this);
    }

    public void toDeath(CorpoCeleste toDestroy) {
        if (toDestroy != null) {
            ap.playAudioJoined("/planetarium/resources/audio/dsdoa.wav");
            toDestroy.distruggi();
        } else {
            Formattazione.printOut("Nessun oggetto agganciato: impossibile iniziare la sequenza!", true, true);
        }
    }

}
