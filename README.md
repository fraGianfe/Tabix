# Tabix RPG

Tabix è un gioco di ruolo (RPG) con esplorazione a turni sviluppato in Java con interfaccia grafica JavaFX.
Il giocatore crea un personaggio e lo guida attraverso una mappa 16×16 generata proceduralmente,
raccogliendo risorse, affrontando nemici casuali (Goblin, Scheletro, Troll) in combattimento a turni
e guadagnando esperienza per salire di livello.

---

## Come eseguire il progetto

### Prerequisiti
- Java 21 (LTS)
- Gradle (il wrapper `gradlew` è incluso nel repository)

### Istruzioni

```bash
git clone <url-del-repository>
cd Tabix
```

### Build del progetto

```bash
./gradlew build
```

### Esecuzione

```bash
./gradlew run
```

---

## Uso di strumenti di AI

Durante lo sviluppo del progetto è stato utilizzato **Claude** come supporto nelle seguenti attività:

- Comprensione e raffinamento della struttura a classi e interfacce del progetto
- Supporto nella progettazione del sistema ad eventi (`GestoreEventi`) e del pattern Repository
- Suggerimenti sulla generazione procedurale della mappa e sulla distribuzione di risorse e nemici
- Chiarimento di errori di compilazione e di problemi legati all'integrazione con JavaFX
- Revisione e miglioramento iterativo del codice, sempre compreso, discusso e adattato manualmente

In ogni caso, il codice prodotto è stato compreso, verificato e integrato personalmente.
L'AI è stata usata come strumento di supporto e confronto, non come sostituto del ragionamento dello sviluppatore.

> Per una descrizione più dettagliata, consultare la **Wiki del repository**.

Visualizzazione di README_template.md.
