package it.unicam.cs.mpgc.rpg125956;

public class GestoreRisorse {
    private final Inventario inventario;

    public GestoreRisorse(Inventario inventario) {
        this.inventario = inventario;
    }

    public void raccogli(Risorsa risorsa, int quantita) {
        inventario.aggiungi(risorsa, quantita);
    }

    public boolean consuma(Risorsa risorsa, int quantita) {
        if (inventario.getQuantita(risorsa) < quantita) return false;
        inventario.rimuovi(risorsa, quantita);
        return true;
    }

    public int contaRisorsa(Risorsa risorsa) {
        return inventario.getQuantita(risorsa);
    }

    public Map<Risorsa, Integer> getInventarioCompleto() {
        return inventario.getTutte();
    }
}
