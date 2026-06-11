package it.unicam.cs.mpgc.rpg125956;

public enum TipoNemico {
    GOBLIN   ("Goblin",    25,  8,  2, 15),
    SCHELETRO("Scheletro", 35, 13,  5, 25),
    TROLL    ("Troll",     55, 18,  8, 50);

    private final String nome;
    private final int saluteBase;
    private final int attaccoBase;
    private final int difesaBase;
    private final int xpRicompensa;

    TipoNemico(String nome, int saluteBase, int attaccoBase, int difesaBase, int xpRicompensa) {
        this.nome         = nome;
        this.saluteBase   = saluteBase;
        this.attaccoBase  = attaccoBase;
        this.difesaBase   = difesaBase;
        this.xpRicompensa = xpRicompensa;
    }

    public String getNome()        { return nome; }
    public int getSaluteBase()     { return saluteBase; }
    public int getAttaccoBase()    { return attaccoBase; }
    public int getDifesaBase()     { return difesaBase; }
    public int getXpRicompensa()   { return xpRicompensa; }
}
