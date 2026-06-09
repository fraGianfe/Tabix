package it.unicam.cs.mpgc.rpg125956;

public interface Vivente {
    void applicaDanno(int danno);
    void recuperaSalute(int quantita);
    boolean isVivo();
    int getSalute();
    int getSaluteMax();
}
