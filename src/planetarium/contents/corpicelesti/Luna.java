package planetarium.contents.corpicelesti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.corpicelesti.interfaces.CorpoCeleste;
import planetarium.contents.registro.Registro;
import planetarium.contents.registro.abstracts.ElementoRegistrabile;
import planetarium.contents.registro.eccezioni.SiiSerioException;
import planetarium.contents.system.posizione.Posizione;
import planetarium.contents.system.utils.NamePicker;

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
        if (massa < 0) {
            throw new SiiSerioException("La massa non può essere negativa.");
        }
        this.massa = massa;
        this.nome = nome == null || "".equals(nome.trim()) ? NamePicker.getIstance().getName(TipiCorpiCelesti.LUNA) : nome;
        this.posizione = pos == null ? new Posizione(0, 0) : pos;
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
        if (padre == null && c.getTipo() == TipiCorpiCelesti.PIANETA) {
            this.padre = c;
        }
        /*else {
            System.out.println("Operazione non consentita. Riprovare.");
        }*/
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
        return this.nome + /*", massa: " + this.massa + */ " [" + this.getTipo() + "]";
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
        padre.distruggiElemento(this); //non serve, l'istruzione precedente de-registra in modo autonomo il corpo
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
