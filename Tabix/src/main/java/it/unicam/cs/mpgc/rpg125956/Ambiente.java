package it.unicam.cs.mpgc.rpg125956;

import java.util.List;

public interface Ambiente {
    String getNome();
    TipoAmbiente getTipo();
    <Risorsa> List<Risorsa> getRisorseDisponibili();
    <Nemico> List<Nemico> getNemiciPresenti();
    String getDescrizione();
}
