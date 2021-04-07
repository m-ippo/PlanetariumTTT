/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.posizione;

import java.util.LinkedList;
import java.util.Random;
import javax.swing.text.Position;
import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.registro.abstracts.ElementoRegistrabile;
import planetarium.contents.registro.eventi.EventoRegistro;
import planetarium.contents.registro.eventi.ListenerRegistro;

/**
 * Gestisce i calcoli delle posizioni nel sistema.
 *
 * @author TTT
 */
public class Griglia {

    /**
     * Centro del sistema di riferimento.
     */
    private final Posizione absolute_zero;
    private double maximum_x = 0;
    private double maximum_y = 0;

    private final LinkedList<Posizione> posizioni = new LinkedList<>();

    public Griglia(Posizione absolute_zero) {
        this.absolute_zero = absolute_zero;
        init();
    }

    public Griglia(float x, float y) {
        this(new Posizione(x, y));
    }

    private void init() {
        EventoRegistro.getIstance().addListener(new ListenerRegistro() {
            @Override
            public void onElementoRegistrato(ElementoRegistrabile re) {
                if (re.getOggettoPadre() instanceof CorpoCeleste) {
                    CorpoCeleste c = (CorpoCeleste) re.getOggettoPadre();
                    registraPosizione(c.getPosizione());
                    if (c.getPosizione().getX() > maximum_x) {
                        maximum_x = c.getPosizione().getX();
                    }
                    if (c.getPosizione().getY() > maximum_y) {
                        maximum_y = c.getPosizione().getY();
                    }
                }
            }

            @Override
            public void onElementoRimosso(ElementoRegistrabile re) {
                if (re.getOggettoPadre() instanceof CorpoCeleste) {
                    CorpoCeleste c = (CorpoCeleste) re.getOggettoPadre();
                    eliminaPosizione(c.getPosizione());
                }
            }
        });
    }

    /**
     * Ritorna la posizione assoluta (nel sistema) ripsetto ad un corpo celeste.
     *
     * @param relative Il corpo su cui orbita.
     * @param x La posizione x relativamente al corpo intorno a cui orbita.
     * @param y La posizione y relativamente al corpo intorno a cui orbita.
     * @return La posizione assoluta nel sistema.
     */
    public Posizione getAbsolutePositionRelativeTo(CorpoCeleste relative, float x, float y) {
        return new Posizione(absolute_zero.getX() + x + relative.getPosizione().getX(), absolute_zero.getY() + y + relative.getPosizione().getY());
    }

    /**
     * Ritorna la posizione assoluta (nel sistema) ripsetto ad un corpo celeste.
     *
     * @param relative Il corpo su cui orbita.
     * @param p La posizione relativa al corpo intorno a cui orbita.
     * @return La posizione assoluta nel sistema.
     */
    public Posizione getAbsolutePositionRelativeTo(CorpoCeleste relative, Posizione p) {
        return new Posizione(absolute_zero.getX() + p.getX() + relative.getPosizione().getX(), absolute_zero.getY() + p.getY() + relative.getPosizione().getY());
    }

    /**
     * Restituisce la posizione relativamente al proprio padre. Se il padre è
     * nulla allora vengono considereate le coordinate definite come zero
     * assoluto del sistema.
     *
     * @param ic Il corpo celeste
     * @return La posizione relativa al padre.
     */
    public Posizione getPosizioneRelativeToParent(CorpoCeleste ic) {
        if (ic.getPadre() != null) {
            return new Posizione(ic.getPosizione().getX() - ic.getPadre().getPosizione().getX(), ic.getPosizione().getY() - ic.getPadre().getPosizione().getY());
        } else {
            return new Posizione(absolute_zero.getX() + ic.getPosizione().getX(), absolute_zero.getY() + ic.getPosizione().getY());
        }
    }

    /**
     * Posizione di un corpo celeste relativamente alla stella.
     *
     * @param ic Il corpo celeste.
     * @return La posizione relativa alla stella del sistema.
     */
    public Posizione getPosizioneRelativeToStar(CorpoCeleste ic) {
        Stella in = Stella.getIstance();
        return new Posizione(ic.getPosizione().getX() - in.getPosizione().getX(), ic.getPosizione().getY() - in.getPosizione().getY());
    }

    /**
     * Registra una nuova posizione.
     *
     * @param p La posizione da registrare.
     */
    public void registraPosizione(Posizione p) {
        posizioni.add(p);
    }

    /**
     * Rimuove una posizione.
     *
     * @param p La posizione da rimuovere
     */
    public void eliminaPosizione(Posizione p) {
        posizioni.removeFirstOccurrence(p);
    }

    /**
     * Controlla se è già presente una certa posizione.
     *
     * @param p La posizione da controllare.
     * @return {@code true} nel caso esista già una posizione, altrimenti
     * {@code false}.
     */
    public boolean esistePosizione(Posizione p) {
        return posizioni.contains(p);
    }

    /**
     * Ritorna una posizione libera nel sistema.
     *
     * @return Nuova {@link Position}.
     */
    public Posizione getPosizioneLiberaCasuale() {
        Posizione new_pos;
        Random r = new Random();
        do {
            new_pos = new Posizione(r.nextInt((int) maximum_x + 10), r.nextInt((int) maximum_y + 10));
        } while (!esistePosizione(new_pos));
        return new_pos;
    }

}
