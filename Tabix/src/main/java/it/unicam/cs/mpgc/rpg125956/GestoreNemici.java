package it.unicam.cs.mpgc.rpg125956;

import java.util.ArrayList;
import java.util.Collections;

public class GestoreNemici {
    private final List<Nemico> nemiciAttivi;
    private final FabbricaNemici fabbrica;

    public GestoreNemici(FabbricaNemici fabbrica) {
        this.fabbrica = fabbrica;
        this.nemiciAttivi = new ArrayList<>();
    }

    public Nemico creaNemica(TipoNemico tipo) {
        Nemico nemico = fabbrica.crea(tipo);
        nemiciAttivi.add(nemico);
        return nemico;
    }

    public void aggiornaComportamento(Personaggio personaggio) {
        nemiciAttivi.stream()
                .filter(Vivente::isVivo)
                .forEach(n -> n.aggiornаComportamento(personaggio));
    }

    public void eliminaMorti() {
        nemiciAttivi.removeIf(n -> !n.isVivo());
    }

    public List<Nemico> getNemiciAttivi() {
        return Collections.unmodifiableList(nemiciAttivi);
    }
}
