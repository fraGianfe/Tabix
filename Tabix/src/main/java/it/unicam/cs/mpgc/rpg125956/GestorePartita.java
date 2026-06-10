package it.unicam.cs.mpgc.rpg125956;

public class GestorePartita {

    private final Personaggio personaggio;
    private final Esploratore esploratore;
    private final GestoreRisorse gestoreRisorse;
    private final GestoreNemici gestoreNemici;
    private final GestoreCombattimento gestoreCombattimento;
    private final Repository<Personaggio, String> repositoryPersonaggio;

    public GestorePartita(Personaggio personaggio, GeneratoreMappa generatoreMappa, GestoreEventi gestoreEventi, Repository<Personaggio, String> repositoryPersonaggio) {
        this.personaggio = personaggio;
        this.repositoryPersonaggio = repositoryPersonaggio;
        Mappa mappa = generatoreMappa.genera();
        this.esploratore = new Esploratore(mappa, gestoreEventi);
        this.gestoreRisorse = new GestoreRisorse(personaggio.getInventario());
        this.gestoreNemici = new GestoreNemici();
        this.gestoreCombattimento = new GestoreCombattimento(gestoreEventi);
    }

    public void salva() {
        repositoryPersonaggio.salva(personaggio);
    }

    // getter per i controller JavaFX
    public Personaggio getPersonaggio() { return personaggio; }
    public Esploratore getEsploratore() { return esploratore; }
    public GestoreRisorse getGestoreRisorse() { return gestoreRisorse; }
    public GestoreNemici getGestoreNemici() { return gestoreNemici; }
    public GestoreCombattimento getGestoreCombattimento() { return gestoreCombattimento; }
}
