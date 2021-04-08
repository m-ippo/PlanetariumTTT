package planetarium.contents.system;

import planetarium.contents.corpicelesti.Luna;
import planetarium.contents.corpicelesti.Pianeta;
import planetarium.contents.corpicelesti.Stella;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.registro.abstracts.ElementoRegistrabile;
import planetarium.contents.registro.eventi.EventoRegistro;
import planetarium.contents.registro.eventi.ListenerRegistro;
import planetarium.contents.system.posizione.Griglia;
import planetarium.contents.system.posizione.Posizione;
import planetarium.contents.system.questionable.Interrogabile;
import planetarium.contents.system.utils.NamePicker;

/**
 * Gestisce l'aggiunta e l'eliminazione dei corpi celesti semplificandone la
 * registrazione. Aiuta a mantenere in memoria le posizioni e a registrarle.
 *
 * @author TTT
 */
public class GestioneSistema {

    private static GestioneSistema istance;
    private final String nome;
    private final Stella unica_stella;
    private static final Griglia sistema_coordinate = new Griglia(0, 0);

    private int contatore_pianeti = 0;
    private int moon_counter = 0;

    private GestioneSistema(String name) {
        this.nome = name == null || "".equals(name.trim()) ? NamePicker.getInstance().getName(null) : name;
        unica_stella = Stella.generateIstance("", (Math.random() + 1) * 1000, new Posizione(0, 0));
        init();
    }

    public static GestioneSistema getIstance(String name) {
        if (istance == null) {
            istance = new GestioneSistema(name);
        }
        return istance;
    }
    public static GestioneSistema getIstance(){
        return istance;
    }
    public static void destroy() {
        istance = null;
    }

    private void init() {
        EventoRegistro.getIstance().addListener(new ListenerRegistro() {
            @Override
            public void onElementoRegistrato(ElementoRegistrabile re) {
                if (re.getOggettoPadre() instanceof Pianeta) {
                    contatore_pianeti++;
                } else if (re.getOggettoPadre() instanceof Luna) {
                    moon_counter++;
                }
            }

            @Override
            public void onElementoRimosso(ElementoRegistrabile re) {
                if (re.getOggettoPadre() instanceof Pianeta) {
                    contatore_pianeti--;
                } else if (re.getOggettoPadre() instanceof Luna) {
                    moon_counter--;
                }
            }
        });
    }

    /**
     * Aggiungi un nuovo elemento al sistema specificando l'ID del corpo celeste
     * su cui il nuovo elemento orbita. Inoltre deve essere specificato se si
     * considerano le coordinate in modo assoluto o relativo.
     *
     * @param ID L'ID del padre
     * @param ic Il nuovo corpo da registrare
     * @param absolute_coords {@code true} Se le coordinate sono riferite al
     * centro del sistema oppure {@code false} se si riferiscono al padre.
     */
    public void aggiungiElementoA(long ID, CorpoCeleste ic, boolean absolute_coords) {
        if (ID >= 0) {
            CorpoCeleste parent = Interrogabile.getCorpoCelesteByID(ID);
            if (parent != null) {
                if (!absolute_coords) {
                    ic.aggiornaPosizione(sistema_coordinate.getAbsolutePositionRelativeTo(parent, ic.getPosizione()));
                }
                parent.aggiungiCorpoCeleste(ic);
            }
        }
    }

    /**
     * Aggiungi un nuovo elemento al sistema specificando il corpo celeste su
     * cui il nuovo elemento orbita. Inoltre deve essere specificato se si
     * considerano le coordinate in modo assoluto o relativo.
     *
     * @param parent Il padre.
     * @param ic Il nuovo corpo da registrare.
     * @param absolute_coords {@code true} Se le coordinate sono riferite al
     * centro del sistema oppure {@code false} se si riferiscono al padre.
     */
    public void aggiungiElementoA(CorpoCeleste parent, CorpoCeleste ic, boolean absolute_coords) {
        if (parent != null) {
            if (!absolute_coords) {
                ic.aggiornaPosizione(sistema_coordinate.getAbsolutePositionRelativeTo(parent, ic.getPosizione()));
            }
            parent.aggiungiCorpoCeleste(ic);
        } else {
            unica_stella.aggiungiCorpoCeleste(ic);
        }
    }

    /**
     * Rimuove un'elemento dal sistema.
     *
     * @param ID L'ID dell'elemento
     */
    public void distruggiElemento(long ID) {
        CorpoCeleste el = Interrogabile.getCorpoCelesteByID(ID);
        if (el != null) {
            el.distruggi();
        }
    }

    public void distruggiElemento(CorpoCeleste ic) {
        if (ic != null) {
            ic.distruggi();
        }
    }

    /**
     * Restituisce il nome assegnato a questo sistema.
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * Restituisce la stella di questo sistema.
     *
     * @return
     */
    public Stella getUnica_Stella() {
        return unica_stella;
    }

    /**
     * Restituisce una posizione casuale nel sistema.
     *
     * @return
     */
    public static Posizione getPosizioneCasuale() {
        return sistema_coordinate.getPosizioneLiberaCasuale();
    }

    /**
     * Controlla se esiste almeno un pianeta nel sistema. Tiene traccia se
     * esistono o sono stati distrutti: un sistema a cui viene aggiunto un
     * pianeta e poi lo stesso viene distrutto non ha pianeti, solo una stella.
     *
     * @return {@code true} se esiste almeno un pianet registrato.
     */
    public boolean haAlmenoUnPianeta() {
        return contatore_pianeti > 0;
    }

    public Griglia getGriglia() {
        return sistema_coordinate;
    }

}
