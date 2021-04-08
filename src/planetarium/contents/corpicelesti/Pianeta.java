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
 * Definizione del comportamento di un pianeta.
 *
 * @author TTT
 */
public class Pianeta extends ElementoRegistrabile implements CorpoCeleste {

    private final List<CorpoCeleste> elenco;
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
        }

        @Override
        public void onElementoRimosso(ElementoRegistrabile re) {
            Object o = re.getOggettoPadre();
            if (o instanceof CorpoCeleste) {
                CorpoCeleste ic = (CorpoCeleste) o;
                if (elenco.contains((CorpoCeleste) ic)) {
                    necessita_ricalcolo = true;
                    elenco.remove(ic);
                }
            }
        }
    };

    /**
     * Costruttore per il pianeta con nome, massa e posizione
     *
     * @param nome nome del pianeta
     * @param massa massa del pianeta
     * @param pos posizione del pianeta
     */
    public Pianeta(String nome, double massa, Posizione pos) {
        this.elenco = new ArrayList<>();
        this.nome = nome;
        this.massa = massa;
        this.posizione = pos;
        init();
    }

    private void init() {
        massa_calcolata = massa;
        posizione_pesata = Posizione.multiply(posizione, massa);
        EventoRegistro.getIstance().addListener(listener_registro);
        setOggettoPadre(this);
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
        return this.padre;
    }

    /**
     * Metodo che ritorna le lune sull'orbita di un pianeta
     *
     * @return lune dell'orbita
     */
    @Override
    public List<CorpoCeleste> getOrbita() {
        return elenco;
    }

    @Override
    public void setPadre(CorpoCeleste c) {
        if (padre == null && c.getTipo() == TipiCorpiCelesti.STELLA) {
            this.padre = c;
        } else {
            System.err.println("Operazione non consentita. Try again.");
        }
    }

    @Override
    public void aggiungiCorpoCeleste(Object c) {

        if (c != null && c instanceof CorpoCeleste) {
            CorpoCeleste as_celestial = (CorpoCeleste) c;
            if (!elenco.contains(as_celestial) && as_celestial.getTipo() == TipiCorpiCelesti.LUNA && c instanceof ElementoRegistrabile) {
                ElementoRegistrabile as_registrable = (ElementoRegistrabile) c;
                if (Registro.registraElemento(as_registrable, as_celestial.getNome())) {
                    as_celestial.setPadre(this);
                    elenco.add(as_celestial);
                    necessita_ricalcolo = true;
                }
            }
        } else {
            System.err.println("Operazione non consentita. Try again.");
        }

        /*if (c != null && c.getTipo() == TipiCorpiCelesti.LUNA && c.getPadre() == null) {            
            this.elenco.add(c);
            c.setPadre(this);
        } else {
            System.err.println("Operazione non consentita. Try again.");
        }*/
    }

    @Override
    public TipiCorpiCelesti getTipo() {
        return TipiCorpiCelesti.PIANETA;
    }

    @Override
    public String toString() {
        return this.nome + /*", massa: " + this.massa +*/ " [" + this.getTipo() + "]";
    }

    @Override
    public void stampaOrbita() {
        //System.out.println(elenco.get(i));
        for (CorpoCeleste corpoCeleste : this.elenco) {
            System.out.println(corpoCeleste.toString());
        }
    }

    @Override
    public void distruggiElemento(CorpoCeleste c) {
        if (c.getTipo() == TipiCorpiCelesti.LUNA) {
            this.elenco.remove(c);
        }
    }

    @Override
    public void distruggi() {
        padre.distruggiElemento(this);
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
    public void setMassaCalcolata(double calculated_mass) {
        this.massa_calcolata = calculated_mass;
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
        return ic == this ? true : elenco.stream().filter(c -> c == ic).findFirst().isPresent();
    }

    @Override
    public Optional<CorpoCeleste> getFiglio(CorpoCeleste ic) {
        return ic == this ? Optional.of(this) : elenco.stream().filter(c -> c == ic).findFirst();
    }

    @Override
    public void aggiornaPosizione(Posizione p) {
        this.posizione = p;
        posizione_pesata = Posizione.multiply(this.posizione, massa);
    }
}
