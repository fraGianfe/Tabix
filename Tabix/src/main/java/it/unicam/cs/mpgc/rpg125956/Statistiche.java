package it.unicam.cs.mpgc.rpg125956;

public class Statistiche {
    private int attacco;
    private int difesa;
    private int velocita;
    private int fortuna;

    public Statistiche() {
        this.attacco = 10;
        this.difesa = 5;
        this.velocita = 5;
        this.fortuna = 3;
    }

    public void incrementaPerLivello(int livello) {
        attacco += 3;
        difesa += 2;
        velocita += 1;
        fortuna += 1;
    }

    public int getAttacco() {
        return attacco;
    }
    public int getDifesa() {
        return difesa;
    }
    public int getVelocita() {
        return velocita;
    }
    public int getFortuna() {
        return fortuna;
    }
}