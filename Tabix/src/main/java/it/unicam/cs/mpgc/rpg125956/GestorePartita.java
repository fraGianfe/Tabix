package it.unicam.cs.mpgc.rpg125956;

/**
 * Coordina i componenti principali della partita:
 * personaggio, mappa ed esploratore.
 * È il punto d'ingresso della logica di gioco per l'interfaccia grafica.
 */
public class GestorePartita {

    private final Personaggio personaggio;
    private final Mappa mappa;
    private final Esploratore esploratore;
    private final GestoreEventi gestoreEventi;

    public GestorePartita(String nomePersonaggio, int larghezza, int altezza) {
        this.gestoreEventi = new GestoreEventi();
        this.personaggio = new Personaggio(nomePersonaggio);
        this.mappa = new GeneratoreMappa(larghezza, altezza).genera();
        this.esploratore = new Esploratore(mappa, gestoreEventi);
        // Segna la cella di partenza come visitata
        esploratore.getCellaCorrente().setVisitata(true);
    }

    public boolean muovi(Direzione direzione) {
        return esploratore.muovi(direzione);
    }

    public Cella getCellaCorrente() {
        return esploratore.getCellaCorrente();
    }

    public Personaggio getPersonaggio() {
        return personaggio;
    }

    public Mappa getMappa() {
        return mappa;
    }

    public Esploratore getEsploratore() {
        return esploratore;
    }

    public GestoreEventi getGestoreEventi() {
        return gestoreEventi;
    }
}
