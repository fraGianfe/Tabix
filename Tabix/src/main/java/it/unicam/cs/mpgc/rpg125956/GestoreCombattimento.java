package it.unicam.cs.mpgc.rpg125956;

public class GestoreCombattimento {
    private final GestoreEventi gestoreEventi;

    public GestoreCombattimento(GestoreEventi gestoreEventi) {
        this.gestoreEventi = gestoreEventi;
    }

    public RisultatoCombattimento attacca(Attaccante attaccante, Vivente bersaglio) {
        int danno = attaccante.calcolaDanno();
        bersaglio.applicaDanno(danno);

        RisultatoCombattimento risultato = new RisultatoCombattimento(danno, bersaglio.isVivo());
        gestoreEventi.pubblica(new EventoCombattimento(risultato));
        return risultato;
    }
}
