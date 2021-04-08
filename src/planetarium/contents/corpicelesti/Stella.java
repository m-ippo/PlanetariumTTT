package planetarium.contents.corpicelesti;

import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.system.posizione.Posizione;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import planetarium.contents.registro.Registro;
import planetarium.contents.registro.abstracts.ElementoRegistrabile;
import planetarium.contents.registro.eventi.EventoRegistro;
import planetarium.contents.registro.eventi.ListenerRegistro;

/**
 * Definizione del comportamento di una stella. Inoltre è permessa una singola
 * instanza che viene generata tramite il metodo
 * {@link Stella#generateIstance(java.lang.String, double, planetarium.contents.system.posizione.Posizione)}.
 *
 * @author TTT
 */
public class Stella extends ElementoRegistrabile implements CorpoCeleste {

    private static Stella istance;

    private List<CorpoCeleste> elenco;
    private final String nome;
    private final double massa;
    private CorpoCeleste padre;
    private Posizione posizione;
    private int codiceID;

    private Posizione posizione_pesata;

    private double massa_calcolata = 0.0;
    private boolean necessita_ricalcolo = false;

    private final ListenerRegistro listener_registro = new ListenerRegistro() {
        @Override
        public void onElementoRegistrato(ElementoRegistrabile re) {
            Object o = re.getOggettoPadre();
            if (o instanceof CorpoCeleste) {
                necessita_ricalcolo = true;
            }
        }

        @Override
        public void onElementoRimosso(ElementoRegistrabile re) {
            Object o = re.getOggettoPadre();
            if (o instanceof CorpoCeleste) {
                CorpoCeleste ic = (CorpoCeleste) o;
                if (elenco.contains((CorpoCeleste) ic)) {
                    elenco.remove(ic);
                    necessita_ricalcolo = true;
                }
            }
        }
    };

    /**
     * Costruttore per la stella con nome, massa e posizione
     *
     * @param nome nome della stella
     * @param massa massa della stella
     * @param pos posizione della stella
     */
    private Stella(String nome, double massa, Posizione pos) {
        this.elenco = new ArrayList<>();
        this.nome = nome;
        this.massa = massa;
        this.posizione = pos;
        init();
    }

    private void init() {
        massa_calcolata = massa;
        posizione_pesata = Posizione.multiply(posizione, massa);
        setOggettoPadre(this);
        Registro.registraElemento(this, nome);
        EventoRegistro.getIstance().addListener(listener_registro);
    }

    /**
     * Metodo che genera l'unica istanza della stella del sistema, se questa non
     * è già presente, altrimenti ritorna quella creata in precedenza
     *
     * @param nome nome della stella
     * @param massa massa della stella
     * @param pos posizione della stella
     * @return
     */
    public static Stella generateIstance(String nome, double massa, Posizione pos) {
        if (istance == null) {
            istance = new Stella(nome, massa, pos);
        }
        //return Stella.istance;
        return istance;
    }

    /**
     * Ritorna l'istanza della stella
     *
     * @return L'unica istanza della stella.
     */
    public static Stella getIstance() {
        if (istance == null) {
            throw new NullPointerException("La stella non è stata ancora definita");
        }
        return istance;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public double getMassa() {
        return this.massa;
    }

    @Override
    public CorpoCeleste getPadre() {
        return padre;
    }

    /**
     * Metodo che ritorna l'elenco dei pianeti sull'orbita della stella
     *
     * @return elenco pianeti orbita
     */
    @Override
    public List<CorpoCeleste> getOrbita() {
        return this.elenco;
    }

    @Override
    public void setPadre(CorpoCeleste c) {
    }

    @Override
    public void aggiungiCorpoCeleste(Object c) {

        if (c != null && c instanceof CorpoCeleste) {
            CorpoCeleste as_celestial = (CorpoCeleste) c;
            if (!elenco.contains(as_celestial) && as_celestial.getTipo() == TipiCorpiCelesti.PIANETA && c instanceof ElementoRegistrabile) {
                ElementoRegistrabile as_registrable = (ElementoRegistrabile) c;
                as_celestial.setPadre(this);
                if (Registro.registraElemento(as_registrable, as_celestial.getNome())) {
                    elenco.add(as_celestial);
                    necessita_ricalcolo = true;
                }
            }
        }
        /*
        if (c != null && c.getTipo() == TipiCorpiCelesti.PIANETA && c.getPadre() == null) {
            elenco.add(c);
            c.setPadre(this);
        } else {
            System.out.println("Operazione non consentita.");
        }*/
    }

    @Override
    public TipiCorpiCelesti getTipo() {
        return TipiCorpiCelesti.STELLA;
    }

    @Override
    public String toString() {
        return this.nome +/* ", massa: " + this.massa +*/ " [" + this.getTipo() + "]";
    }

    @Override
    public void stampaOrbita() {
        for (CorpoCeleste corpoCeleste : this.elenco) {
            System.out.println(corpoCeleste.toString());
        }
    }

    @Override
    public void distruggiElemento(CorpoCeleste c) {
        if (c.getTipo() == TipiCorpiCelesti.PIANETA) {
            this.elenco.remove(c);
        }
    }

    @Override
    public void distruggi() {
        Stella.istance = null;
    }

    @Override
    public int getCodiceID() {
        return this.codiceID;
    }

    @Override
    public void setCodiceID(int codiceID) {
        this.codiceID = codiceID;
    }

    @Override
    public Posizione getPosizione() {
        return posizione;
    }

    @Override
    public boolean deveEssereRicalcolato() {
        return necessita_ricalcolo;
    }

    @Override
    public void setMassaCalcolata(double massa_calcolata) {
        this.massa_calcolata = massa_calcolata;
        necessita_ricalcolo = false;
    }

    @Override
    public double getMassaCalcolata() {
        return massa_calcolata;
    }

    @Override
    public void setPosizionePesata(Posizione p) {
        posizione_pesata = p;
    }

    @Override
    public Posizione getPosizionePesata() {
        return posizione_pesata;
    }

    @Override
    public boolean contiene(CorpoCeleste ic) {
        return ic == this ? true : elenco.stream().filter(c -> c.contiene(ic)).findFirst().isPresent();
    }

    @Override
    public Optional<CorpoCeleste> getFiglio(CorpoCeleste ic) {
        return ic == this ? Optional.of(this) : elenco.stream().filter(c -> c.contiene(ic)).findFirst();
    }

    @Override
    public void aggiornaPosizione(Posizione p) {
        this.posizione = p;
        posizione_pesata = Posizione.multiply(this.posizione, massa);
    }

}
