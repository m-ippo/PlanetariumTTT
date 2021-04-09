/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.input.menu.utili;

/**
 * Semplice rappresentazione di una coppia.
 *
 * @author TTT
 * @param <K> Chive
 * @param <V> Valore
 */
public class Coppia<K, V> {

    private final K chiave;
    private final V valore;

    public Coppia(K k, V v) {
        chiave = k;
        valore = v;
    }

    public K getChiave() {
        return chiave;
    }

    public V getValore() {
        return valore;
    }

}
