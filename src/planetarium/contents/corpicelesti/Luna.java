package planetarium.contents.corpicelesti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.registro.Registro;
import planetarium.contents.registro.abstracts.ElementoRegistrabile;
import planetarium.contents.system.posizione.Posizione;

public class Luna extends ElementoRegistrabile implements CorpoCeleste {

    private final String nome;
    private final double massa;
    private CorpoCeleste padre;
    private Posizione posizione;
    private int codiceID;

    private Posizione posizione_pesata;

    /**
     * Costruttore per la luna, solo con nome, massa e posizione
     *
     * @param nome nome della luna
     * @param massa massa della luna
     * @param pos posizione del pianeta
     */
    public Luna(String nome, double massa, Posizione pos) {
        this.nome = nome;
        this.massa = massa;
        this.posizione = pos;
        init();
    }

    private void init() {
        posizione_pesata = Posizione.multiply(posizione, massa);
        setOggettoPadre(this);
        aggiornaPosizione(posizione);
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

    @Override
    public void setPadre(CorpoCeleste c) {
        if (c.getTipo() == TipiCorpiCelesti.PIANETA) {
            this.padre = c;
        } else {
            System.out.println("Operazione non consentita. Try again.");
        }
    }

    @Override
    public void aggiungiCorpoCeleste(Object c) {
    }

    @Override
    public TipiCorpiCelesti getTipo() {
        return TipiCorpiCelesti.LUNA;
    }

    @Override
    public String toString() {
        return this.nome + ", massa: " + this.massa + " [" + this.getTipo() + "]";
    }

    @Override
    public void stampaOrbita() {
    }

    @Override
    public void distruggiElemento(CorpoCeleste c) {
    }

    @Override
    public void distruggi() {
        Registro.rimuoviElemento(this);
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

    //modifiche
    @Override
    public Posizione getPosizione() {
        return posizione;
    }

    @Override
    public List<CorpoCeleste> getOrbita() {
        return Collections.unmodifiableList(new ArrayList<>());
    }

    @Override
    public boolean deveEssereRicalcolato() {
        return false;
    }

    @Override
    public void setMassaCalcolata(double calculated_mass) {
    }

    @Override
    public double getMassaCalcolata() {
        return massa;
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
        return ic == this;
    }

    @Override
    public Optional<CorpoCeleste> getFiglio(CorpoCeleste ic) {
        return Optional.ofNullable(contiene(ic) ? this : null);
    }

    @Override
    public void aggiornaPosizione(Posizione p) {
        this.posizione = p;
        posizione_pesata = Posizione.multiply(posizione, massa);
    }

}
