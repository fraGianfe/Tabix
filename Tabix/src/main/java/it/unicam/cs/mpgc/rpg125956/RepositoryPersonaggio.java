package it.unicam.cs.mpgc.rpg125956;

public class RepositoryPersonaggio implements Repository<Personaggio, String> {
    private static final String PERCORSO = "dati/personaggi.json";
    private final ObjectMapper mapper; // Jackson

    public RepositoryPersonaggio() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public Optional<Personaggio> trovaPerId(String nome) {
        return trovaTutti().stream()
                .filter(p -> p.getNome().equals(nome))
                .findFirst();
    }

    @Override

    public List<Personaggio> trovaTutti() {
        try {
            File file = new File(PERCORSO);
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file,
                    new TypeReference<List<Personaggio>>() {});
        } catch (IOException e) {
            throw new ErrorePersistenza("Errore lettura personaggi", e);
        }
    }

    @Override
    public void salva(Personaggio personaggio) {
        List<Personaggio> lista = trovaTutti();
        lista.removeIf(p -> p.getNome().equals(personaggio.getNome()));
        lista.add(personaggio);
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(PERCORSO), lista);
        } catch (IOException e) {
            throw new ErrorePersistenza("Errore salvataggio personaggio", e);
        }
    }

    @Override
    public void elimina(String nome) {
        List<Personaggio> lista = trovaTutti();
        lista.removeIf(p -> p.getNome().equals(nome));
        try {
            mapper.writeValue(new File(PERCORSO), lista);
        } catch (IOException e) {
            throw new ErrorePersistenza("Errore eliminazione personaggio", e);
        }
    }
}