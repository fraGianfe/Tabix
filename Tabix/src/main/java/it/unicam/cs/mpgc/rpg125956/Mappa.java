package it.unicam.cs.mpgc.rpg125956;

import java.util.Optional;

public class Mappa {
    private final Cella[][] griglia;
    private final int larghezza;
    private final int altezza;

    public Mappa(Cella[][] griglia, int larghezza, int altezza) {
        this.griglia = griglia;
        this.larghezza = larghezza;
        this.altezza = altezza;
    }

    public Optional<Cella> getCella(int x, int y) {
        if (x < 0 || x >= larghezza || y < 0 || y >= altezza)
            return Optional.empty();
        return Optional.of(griglia[y][x]);
    }

    public int getLarghezza() { return larghezza; }
    public int getAltezza()   { return altezza; }
}