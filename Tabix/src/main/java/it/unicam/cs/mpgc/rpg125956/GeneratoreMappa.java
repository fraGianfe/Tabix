package it.unicam.cs.mpgc.rpg125956;

import java.util.Random;

public class GeneratoreMappa {


    private final int larghezza;
    private final int altezza;
    private final Random random;
    private final FabbricaNemici fabbricaNemici;
    private final FabbricaRisorse fabbricaRisorse;

    public GeneratoreMappa (int larghezza, int altezza, FabbricaNemici fabbricaNemici, FabbricaRisorse fabbricaRisorse){
        this.larghezza = larghezza;
        this.altezza = altezza;
        this.random = new Random();
        this.fabbricaNemici = fabbricaNemici;
        this.fabbricaRisorse = fabbricaRisorse;
    }

    public Mappa genera() {
        Cella[][] griglia = new Cella[altezza][larghezza];
        for (int y = 0; y < altezza; y++)
            for (int x = 0; x < larghezza; x++)
                griglia[y][x] = new Cella(x, y, scegliTipoAmbiente());
        popolaRisorseENemici(griglia);
        return new Mappa(griglia, larghezza, altezza);
    }


    private TipoAmbiente scegliTipoAmbiente() {
        int n = random.nextInt(100);
        if (n < 40) return TipoAmbiente.STANZA;
        if (n < 70) return TipoAmbiente.FORESTA;
        return TipoAmbiente.CAVERNA;
    }

    private void popolaRisorseENemici(Cella[][] griglia) {
        for (Cella[] riga : griglia)
            for (Cella cella : riga) {
                if (random.nextInt(100) < 30)
                    cella.aggiungiRisorsa(
                            fabbricaRisorse.creaPerAmbiente(cella.getTipo()));
                if (random.nextInt(100) < 20)
                    cella.aggiungiNemico(
                            fabbricaNemici.creaPerAmbiente(cella.getTipo()));
            }
    }
}
