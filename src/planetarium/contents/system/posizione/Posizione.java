package planetarium.contents.system.posizione;

/**
 * Rappresentazione di coordinate 2D
 *
 * @author TTT
 */
public class Posizione {

    private final double x;
    private final double y;

    /**
     * Costruttore per la posizione
     *
     * @param x valore asse x
     * @param y valore asse y
     */
    public Posizione(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Ritorna un boolean per vedere se due posizioni coincidono
     *
     * @param o altra posizione
     * @return true se coincidono, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Posizione posizione = (Posizione) o;
        return Double.compare(posizione.x, x) == 0 && Double.compare(posizione.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    /**
     * Ritorna il valore dell'asse x
     *
     * @return valore x
     */
    public double getX() {
        return x;
    }

    /**
     * Ritorna il valore dell'asse y
     *
     * @return valore y
     */
    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + " , " + y + ")";
    }

    /**
     * Crea una nuova posizione pesata moltiplicando una posizione per la massa.
     *
     * @param p La posizione da moltiplicare
     * @param mass La massa
     * @return La nuova posizione in base alla massa.
     */
    public static Posizione multiply(Posizione p, double mass) {
        return new Posizione(p.getX() * mass, p.getY() * mass);
    }

    /**
     * Crea una nuova posizione pesata dividendo una posizione per la massa.
     *
     * @param p La posizione da moltiplicare
     * @param mass La massa
     * @return La nuova posizione in base alla massa.
     */
    public static Posizione divide(Posizione p, double mass) {
        return new Posizione(p.getX() / mass, p.getY() / mass);
    }

    /**
     * Crea una nuova posizione come risultato della somma di altre due
     * posizioni.
     *
     * @param p1 Posizione 1.
     * @param p2 Posizione 2.
     * @return Posizione sommata.
     */
    public static Posizione sum(Posizione p1, Posizione p2) {
        return p1 == null ? (p2 == null ? null : p2) : (p2 == null ? new Posizione(p1.getX(), p1.getY()) : new Posizione(p1.getX() + p2.getX(), p1.getY() + p2.getY()));
    }
}
