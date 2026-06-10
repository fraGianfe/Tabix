package it.unicam.cs.mpgc.rpg125956;

import java.util.ArrayList;
import java.util.List;

public class Personaggio {

    private String nome;
    private Inventario inventario;
    private Statistiche statistiche;
    private int livello;
    private int salute;
    private int saluteMax;
    private int energia;
    private int energiaMax;
    private int esperienza;
    private int esperienzaPerLivello;


    public Personaggio(String nome) {
        this.nome = nome;
        this.livello = 1;
        this.saluteMax = 100;
        this.salute = saluteMax;
        this.energiaMax = 50;
        this.energia = energiaMax;
        this.esperienza = 0;
        this.esperienzaPerLivello = 100;
        List<Abilita> abilita = new ArrayList<>();
        this.inventario = new Inventario();
        this.statistiche = new Statistiche();
    }


    @Override
    public void applicaDanno(int danno) {
        int dannoEffettivo = Math.max(0, danno - statistiche.getDifesa());
        this.salute = Math.max(0, this.salute - dannoEffettivo);
    }


    @Override
    public void recuperaSalute(int quantita) {
        this.salute = Math.min(saluteMax, this.salute + quantita);
    }

    @Override
    public boolean isVivo() {
        return salute > 0;
    }

    public void incrementaSalute(int quantita) { recuperaSalute(quantita); }

    public void decrementaSalute(int quantita) { applicaDanno(quantita); }

    public void incrementaEnergia(int quantita) {
        this.energia = Math.min(energiaMax, this.energia + quantita);
    }

    public void decrementaEnergia(int quantita) {
        this.energia = Math.max(0, this.energia - quantita);
    }

    public void aggiungiEsperienza(int xp) {
        this.esperienza += xp;
        while (this.esperienza >= this.esperienzaPerLivello) {
            this.esperienza -= this.esperienzaPerLivello;
            incrementaLivello();
        }
    }

    private void incrementaLivello() {
        this.livello++;
        this.saluteMax += 20;
        this.energiaMax += 10;
        this.salute = saluteMax;
        this.energia = energiaMax;
        this.esperienzaPerLivello = (int)(esperienzaPerLivello * 1.5);
        statistiche.incrementaPerLivello(livello);
        sbloccoAbilitaPerLivello();
    }


    @Override
    public int calcolaDanno() {
        return statistiche.getAttacco() + (int)(Math.random() * 10);
    }

    public int getEnergia() {
        return energia;
    }
    public int getSalute() {
        return salute;
    }
    public int getSaluteMax() {
        return saluteMax;
    }
    public int getLivello() {
        return livello;
    }
    public int getEnergiaMax() {
        return energiaMax;
    }

    public String getNome() {
        return nome;}

    public Inventario getInventario() {
        return inventario;
    }
}

