package it.unicam.cs.mpgc.rpg125956;

/**
 * Rappresenta una risorsa raccoglibile nel mondo di gioco.
 * Ogni risorsa ha un tipo e una quantità disponibile.
 */
public class Risorsa {

    private final TipoRisorsa tipo;
    private final int quantita;

    public Risorsa(TipoRisorsa tipo, int quantita) {
        this.tipo = tipo;
        this.quantita = quantita;
    }

    public TipoRisorsa getTipo() {
        return tipo;
    }

    public int getQuantita() {
        return quantita;
    }

    @Override
    public String toString() {
        return tipo.name() + " x" + quantita;
    }
}
