package it.unicam.cs.mpgc.rpg125956;

import java.util.ArrayList;
import java.util.List;

public class Cella implements Ambiente {

    private boolean visitata;


    public Cella(int x, int y, TipoAmbiente tipo) {
        this.visitata = false;
        List<Risorsa> risorse = new ArrayList<>();
        List<Nemico> nemici = new ArrayList<>();
        String descrizione = tipo.name().toLowerCase();

    }

    @Override
    public <Nemico> List<Nemico> getNemiciPresenti() {
        return List.of();
    }

    @Override
    public <Risorsa> List<Risorsa> getRisorseDisponibili() {
        return List.of();
    }

    public boolean isVisitata() {
        return visitata;
    }

    public void setVisitata(boolean visitata) {
        this.visitata = visitata;
    }
}
