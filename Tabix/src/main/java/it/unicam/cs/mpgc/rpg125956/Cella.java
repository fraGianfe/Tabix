package it.unicam.cs.mpgc.rpg125956;

import java.util.ArrayList;
import java.util.List;

public class Cella implements Ambiente {

    private final int x;
    private final int y;
    private final TipoAmbiente tipo;
    private boolean visitata;
    private List<Risorsa> risorse;
    private List<Nemico> nemici;
    private String descrizione;


    public Cella(int x, int y, TipoAmbiente tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.visitata = false;
        this.risorse= new ArrayList<>();
        this.nemici= new ArrayList<>();
        this.descrizione = tipo.name().toLowerCase();

    }

}
