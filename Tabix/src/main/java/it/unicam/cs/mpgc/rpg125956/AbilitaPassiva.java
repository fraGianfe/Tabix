package it.unicam.cs.mpgc.rpg125956;

public class AbilitaPassiva {
    private String nome;
    private String descrizione;
    private String attributoPotenziato;
    private int bonus;

    @Override
    public void esegui(Personaggio personaggio) {
        // applicata automaticamente, es. modifica statistiche
    }

    @Override public boolean isPassiva() { return true;}
    }
