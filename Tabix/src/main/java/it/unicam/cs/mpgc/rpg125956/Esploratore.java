package it.unicam.cs.mpgc.rpg125956;

public class Esploratore {
    private final Mappa mappa;
    private int posizioneX;
    private int posizioneY;
    private final GestoreEventi gestoreEventi;

    public Esploratore(Mappa mappa, GestoreEventi gestoreEventi) {
        this.mappa = mappa;
        this.gestoreEventi = gestoreEventi;
        this.posizioneX = 0;
        this.posizioneY = 0;
    }

    public boolean muovi(Direzione direzione) {
        int nuovoX = posizioneX + direzione.getDeltaX();
        int nuovoY = posizioneY + direzione.getDeltaY();

        return mappa.getCella(nuovoX, nuovoY).map(cella -> {
            posizioneX = nuovoX;
            posizioneY = nuovoY;
            cella.setVisitata(true);
            gestoreEventi.pubblica("MOSSA:" + direzione.name());
            return true;
        }).orElse(false);
    }

    public Cella getCellaCorrente() {
        return mappa.getCella(posizioneX, posizioneY).orElseThrow();
    }

    public int getPosizioneX() {
        return posizioneX;
    }

    public int getPosizioneY() {
        return posizioneY;
    }
}
