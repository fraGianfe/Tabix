package it.unicam.cs.mpgc.rpg125956;

public class Nemico implements Vivente {

    private final TipoNemico tipo;
    private int salute;

    public Nemico(TipoNemico tipo) {
        this.tipo   = tipo;
        this.salute = tipo.getSaluteBase();
    }

    @Override
    public void applicaDanno(int danno) {
        salute = Math.max(0, salute - danno);
    }

    @Override
    public void recuperaSalute(int quantita) {
        salute = Math.min(tipo.getSaluteBase(), salute + quantita);
    }

    @Override public boolean isVivo()      { return salute > 0; }
    @Override public int getSalute()       { return salute; }
    @Override public int getSaluteMax()    { return tipo.getSaluteBase(); }

    public TipoNemico getTipo()            { return tipo; }
    public String getNome()                { return tipo.getNome(); }
    public int getAttacco()                { return tipo.getAttaccoBase(); }
    public int getDifesa()                 { return tipo.getDifesaBase(); }
    public int getXpRicompensa()           { return tipo.getXpRicompensa(); }
}
