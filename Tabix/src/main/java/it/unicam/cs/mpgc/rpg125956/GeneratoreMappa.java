package it.unicam.cs.mpgc.rpg125956;

import java.util.Random;

/**
 * Genera casualmente una mappa di gioco composta da celle
 * con diversi tipi di ambiente e risorse sparse.
 */
public class GeneratoreMappa {

    private final int larghezza;
    private final int altezza;
    private final Random random;

    public GeneratoreMappa(int larghezza, int altezza) {
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.random = new Random();
    }

    public Mappa genera() {
        Cella[][] griglia = new Cella[altezza][larghezza];
        for (int y = 0; y < altezza; y++)
            for (int x = 0; x < larghezza; x++)
                griglia[y][x] = new Cella(x, y, scegliTipoAmbiente());
        popolaRisorse(griglia);
        return new Mappa(griglia, larghezza, altezza);
    }

    private TipoAmbiente scegliTipoAmbiente() {
        int n = random.nextInt(100);
        if (n < 40) return TipoAmbiente.STANZA;
        if (n < 70) return TipoAmbiente.FORESTA;
        return TipoAmbiente.CAVERNA;
    }

    private void popolaRisorse(Cella[][] griglia) {
        TipoRisorsa[] tipi = TipoRisorsa.values();
        for (Cella[] riga : griglia)
            for (Cella cella : riga)
                if (random.nextInt(100) < 35) {
                    TipoRisorsa tipo = tipi[random.nextInt(tipi.length)];
                    int quantita = 1 + random.nextInt(3);
                    cella.aggiungiRisorsa(new Risorsa(tipo, quantita));
                }
    }
}
