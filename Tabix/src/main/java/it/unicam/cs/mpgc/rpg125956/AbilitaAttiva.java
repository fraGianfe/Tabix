package it.unicam.cs.mpgc.rpg125956;

public class AbilitaAttiva implements Abilita {

    private String nome;
    private String descrizione;
    private int costEnergia;
    private int dannoBase;
    private TipoAbilita tipo;

    @Override
    public void esegui(Personaggio personaggio) {
        if (personaggio.getEnergia() < costEnergia) return;
        personaggio.decrementaEnergia(costEnergia);
        // effetto specifico in base al tipo
    }

    @Override public boolean isPassiva() { return false; }

    @Override
    public String getNome() {
        return nome;
    }
    @Override
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public int getCostEnergia() {
        return costEnergia;
    }
    public void setCostEnergia(int costEnergia) {
        this.costEnergia = costEnergia;
    }
    public int getDannoBase() {
        return dannoBase;
    }
    public void setDannoBase(int dannoBase) {
        this.dannoBase = dannoBase;
    }

}


