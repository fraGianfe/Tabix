package it.unicam.cs.mpgc.rpg125956;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Rappresenta una singola cella della mappa di gioco.
 * Ogni cella ha una posizione, un tipo di ambiente e può contenere risorse.
 */
public class Cella {

    private final int x;
    private final int y;
    private final TipoAmbiente tipo;
    private boolean visitata;
    private final List<Risorsa> risorse;

    public Cella(int x, int y, TipoAmbiente tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.visitata = false;
        this.risorse = new ArrayList<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TipoAmbiente getTipo() {
        return tipo;
    }

    public boolean isVisitata() {
        return visitata;
    }

    public void setVisitata(boolean visitata) {
        this.visitata = visitata;
    }

    public void aggiungiRisorsa(Risorsa risorsa) {
        risorse.add(risorsa);
    }

    public List<Risorsa> getRisorse() {
        return Collections.unmodifiableList(risorse);
    }

    @Override
    public String toString() {
        return tipo.name() + " (" + x + "," + y + ")";
    }
}
