package planetarium.contents.corpicelesti.interfaces;

import java.util.List;
import planetarium.contents.corpicelesti.enums.TipiCorpiCelesti;
import planetarium.contents.system.posizione.Posizione;
import planetarium.contents.system.questionable.interfaces.SupportoCalcoli;
import planetarium.contents.system.questionable.interfaces.SupportoQuery;

/**
 * Interfaccia per la gestione dei corpi celesti
 */
public interface CorpoCeleste extends SupportoCalcoli, SupportoQuery {

    /**
     * Ritorna il nome del corpo celeste
     *
     * @return nome
     */
    public String getNome();

    /**
     * Ritorna il valore della masasa
     *
     * @return massa
     */
    public double getMassa();

    /**
     * Ritorna il corpo celeste al quale appartiene
     *
     * @return corpo celeste
     */
    public CorpoCeleste getPadre();

    public List<CorpoCeleste> getOrbita();

    /**
     * Imposta il corpo celeste attorno al quale orbita
     *
     * @param c Il corpo celeste che fa da padre questo.
     */
    public void setPadre(CorpoCeleste c);

    /**
     * Ritorna il codice identificativo del corpo celeste
     *
     * @return codice ID
     */
    public int getCodiceID();

    /**
     * Metodo per impostare il codice identificativo del corpo celeste
     *
     * @param codiceID Il codice univoco
     */
    public void setCodiceID(int codiceID);

    /**
     * Aggiunge un corpo celeste alla sua orbita, controllando se il tipo di
     * relazione è corretto (stella-pianeta, pianeta-luna), se positivo aggiorna
     * il corpo celeste padre del corpo aggiunto all'orbita. Se il @param
     * appartiene ad un'altra orbita stampa a video che l'operazione non è
     * possibile.
     *
     * @param c corpo celeste
     */
    public void aggiungiCorpoCeleste(Object c);

    /**
     * Ritorna il tipo di corpo celeste: stella, pianeta o luna
     *
     * @return tipo corpo celeste
     */
    public TipiCorpiCelesti getTipo();

    /**
     * Stampa a video tutti i corpi celesti che orbitano attorno a esso
     */
    public void stampaOrbita();

    /**
     * Distrugge il corpo celeste se sta nell'orbita
     *
     * @param c Corpo celeste da distruggere
     */
    public void distruggiElemento(CorpoCeleste c);

    /**
     * Distrugge il corpo celeste selezionato e la sua orbita
     */
    public void distruggi();

    /**
     * Ritorna la posizione del corpo celeste.
     *
     * @return Posizione nel sistema del corpo celeste
     */
    public Posizione getPosizione();

    /**
     * Aggiorna la posizione assoluta del corpo celeste.
     *
     * @param p La posizione assoluta (con riferimento allo zero assoluto di
     * {@link Grid}).
     */
    public void aggiornaPosizione(Posizione p);

}
