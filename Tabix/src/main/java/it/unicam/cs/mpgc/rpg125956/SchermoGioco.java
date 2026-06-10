package it.unicam.cs.mpgc.rpg125956;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Schermata principale di gioco.
 * Mostra la mappa 10x10, lo sprite del personaggio, la barra di stato,
 * i pulsanti di movimento (cliccabili o con i tasti freccia / WASD)
 * e il pannello informativo sulla cella corrente.
 */
public class SchermoGioco extends BorderPane {

    /** Dimensione in pixel di ogni cella della mappa. */
    private static final int DIM_CELLA = 46;
    /** Dimensione (in px) di un singolo "pixel" dello sprite 8x8. */
    private static final int PIXEL_SPRITE = 5;

    private final GestorePartita gestorePartita;

    // Griglia: ogni StackPane contiene un Rectangle (sfondo) e opzionalmente il Canvas sprite
    private final StackPane[][] celle = new StackPane[10][10];

    // Sprite del personaggio (Canvas pixel-art 40x40 px)
    private final Canvas spritePersonaggio;

    // Posizione attuale dello sprite nella griglia (per spostarlo tra celle)
    private int spriteX = -1;
    private int spriteY = -1;

    private final Label labelStatus;
    private final VBox pannelloInfo;

    // ===================================================================
    //  Costruttore
    // ===================================================================

    public SchermoGioco(GestorePartita gestorePartita) {
        this.gestorePartita = gestorePartita;
        this.labelStatus    = new Label();
        this.pannelloInfo   = new VBox(8);
        this.spritePersonaggio = creaSprite();

        costruisciLayout();
        aggiornaPosizione();

        // Tastiera: si registra non appena il nodo entra in una scena
        setFocusTraversable(true);
        sceneProperty().addListener((obs, vecchia, nuova) -> {
            if (nuova != null) {
                nuova.setOnKeyPressed(e -> gestisciTasto(e.getCode()));
                Platform.runLater(this::requestFocus);
            }
        });
    }

    // ===================================================================
    //  Input da tastiera
    // ===================================================================

    private void gestisciTasto(KeyCode tasto) {
        Direzione dir = switch (tasto) {
            case UP,    W -> Direzione.NORD;
            case DOWN,  S -> Direzione.SUD;
            case LEFT,  A -> Direzione.OVEST;
            case RIGHT, D -> Direzione.EST;
            default       -> null;
        };
        if (dir != null) {
            gestorePartita.muovi(dir);
            aggiornaPosizione();
        }
    }


    //  Costruzione layout

    private void costruisciLayout() {
        // TOP — barra di stato
        labelStatus.setFont(Font.font("Monospaced", FontWeight.BOLD, 13));
        labelStatus.setStyle("-fx-text-fill: white;");
        HBox top = new HBox(labelStatus);
        top.setPadding(new Insets(8, 14, 8, 14));
        top.setAlignment(Pos.CENTER_LEFT);
        top.setStyle("-fx-background-color: #1a237e;");
        setTop(top);

        // CENTER — griglia della mappa
        GridPane griglia = new GridPane();
        griglia.setHgap(2);
        griglia.setVgap(2);
        griglia.setPadding(new Insets(12));
        griglia.setStyle("-fx-background-color: #212121;");
        costruisciGriglia(griglia);
        setCenter(griglia);

        // BOTTOM — pulsanti di movimento
        setBottom(costruisciBottoni());

        // RIGHT — pannello info cella
        pannelloInfo.setPadding(new Insets(14));
        pannelloInfo.setPrefWidth(165);
        pannelloInfo.setStyle("-fx-background-color: #eceff1;");
        setRight(pannelloInfo);
    }


    private void costruisciGriglia(GridPane griglia) {
        Mappa mappa = gestorePartita.getMappa();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Cella cella = mappa.getCella(x, y).orElse(null);
                if (cella == null) continue;

                Rectangle sfondo = new Rectangle(DIM_CELLA, DIM_CELLA);
                sfondo.setArcWidth(5);
                sfondo.setArcHeight(5);
                sfondo.setFill(colorePerTipo(cella.getTipo()));
                sfondo.setOpacity(cella.isVisitata() ? 1.0 : 0.25);
                Tooltip.install(sfondo, new Tooltip(
                    cella.getTipo().name() + " (" + x + "," + y + ")"
                ));

                StackPane pannello = new StackPane(sfondo);
                pannello.setMinSize(DIM_CELLA, DIM_CELLA);
                pannello.setMaxSize(DIM_CELLA, DIM_CELLA);
                celle[y][x] = pannello;
                griglia.add(pannello, x, y);
            }
        }
    }

    private HBox costruisciBottoni() {
        Button bNord  = creaBotone("▲ Nord",  Direzione.NORD);
        Button bSud   = creaBotone("▼ Sud",   Direzione.SUD);
        Button bOvest = creaBotone("◀ Ovest", Direzione.OVEST);
        Button bEst   = creaBotone("▶ Est",   Direzione.EST);

        Label suggerimento = new Label("  ⌨  Frecce / W-A-S-D per muoverti");
        suggerimento.setStyle("-fx-text-fill: #90a4ae; -fx-font-size: 11;");

        HBox bottom = new HBox(10, bOvest, bNord, bSud, bEst, suggerimento);
        bottom.setPadding(new Insets(10, 14, 10, 14));
        bottom.setAlignment(Pos.CENTER_LEFT);
        bottom.setStyle("-fx-background-color: #263238;");
        return bottom;
    }

    private Button creaBotone(String testo, Direzione dir) {
        Button btn = new Button(testo);
        btn.setStyle(
            "-fx-background-color: #37474f; -fx-text-fill: white;" +
            "-fx-font-size: 13; -fx-padding: 8 18; -fx-background-radius: 5;"
        );
        btn.setFocusTraversable(false); // non rubare il focus alla scena (serve per la tastiera)
        btn.setOnAction(e -> {
            gestorePartita.muovi(dir);
            aggiornaPosizione();
            requestFocus();
        });
        return btn;
    }


    private void aggiornaPosizione() {
        Esploratore esp = gestorePartita.getEsploratore();
        int px = esp.getPosizioneX();
        int py = esp.getPosizioneY();
        Personaggio pg  = gestorePartita.getPersonaggio();
        Mappa mappa     = gestorePartita.getMappa();

        // --- Sposta lo sprite nella nuova cella ---
        if (spriteX >= 0) celle[spriteY][spriteX].getChildren().remove(spritePersonaggio);
        celle[py][px].getChildren().add(spritePersonaggio);
        spriteX = px;
        spriteY = py;

        // --- Aggiorna colore/opacità di tutte le celle ---
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Cella cella = mappa.getCella(x, y).orElse(null);
                if (cella == null) continue;
                Rectangle sfondo = (Rectangle) celle[y][x].getChildren().get(0);

                if (x == px && y == py) {
                    sfondo.setFill(Color.web("#455a64")); // sfondo cella del giocatore
                    sfondo.setOpacity(1.0);
                } else if (cella.isVisitata()) {
                    sfondo.setFill(colorePerTipo(cella.getTipo()));
                    sfondo.setOpacity(1.0);
                } else {
                    sfondo.setFill(colorePerTipo(cella.getTipo()));
                    sfondo.setOpacity(0.25);
                }
            }
        }

        // --- Barra di stato ---
        labelStatus.setText(
            "👤 " + pg.getNome() +
            "    ❤ " + pg.getSalute() + "/" + pg.getSaluteMax() +
            "    ⚡ " + pg.getEnergia() + "/" + pg.getEnergiaMax() +
            "    ⭐ Lv." + pg.getLivello() +
            "    📍 (" + px + "," + py + ")"
        );

        // --- Pannello cella ---
        aggiornaInfoCella(esp.getCellaCorrente());
    }

    private void aggiornaInfoCella(Cella cella) {
        pannelloInfo.getChildren().clear();

        Label titolo = new Label("Cella corrente");
        titolo.setFont(Font.font("System", FontWeight.BOLD, 13));

        Label tipoLabel = new Label(iconaAmbiente(cella.getTipo()) + "  " + cella.getTipo().name());
        Label posLabel  = new Label("📍  (" + cella.getX() + ", " + cella.getY() + ")");

        pannelloInfo.getChildren().addAll(titolo, tipoLabel, posLabel);

        if (cella.getRisorse().isEmpty()) {
            Label nessuna = new Label("Nessuna risorsa");
            nessuna.setStyle("-fx-text-fill: #9e9e9e;");
            pannelloInfo.getChildren().add(nessuna);
        } else {
            Label titoloR = new Label("Risorse:");
            titoloR.setFont(Font.font("System", FontWeight.BOLD, 12));
            pannelloInfo.getChildren().add(titoloR);
            cella.getRisorse().forEach(r ->
                pannelloInfo.getChildren().add(new Label("  • " + r))
            );
        }
    }

    // ===================================================================
    //  Sprite pixel art 8x8
    // ===================================================================

    /**
     * Disegna uno sprite 8x8 pixel usando un Canvas JavaFX.
     * Ogni "pixel" dell'arte è grande PIXEL_SPRITE px reali.
     * Palette:
     *   0 = trasparente
     *   1 = pelle    #FFCC80
     *   2 = capelli  #4E342E
     *   3 = bocca    #EF5350
     *   4 = occhi    #1A237E
     *   5 = corpo    #1565C0 (blu)
     *   6 = stivali  #37474F (grigio scuro)
     */
    private Canvas creaSprite() {
//        int[][] pixel = {
//            { 0, 0, 2, 2, 2, 2, 0, 0 },   // capelli (top)
//            { 0, 2, 1, 1, 1, 1, 2, 0 },   // testa
//            { 0, 1, 4, 1, 1, 4, 1, 0 },   // occhi
//            { 0, 1, 1, 1, 3, 1, 1, 0 },   // bocca
//            { 0, 5, 5, 5, 5, 5, 5, 0 },   // busto
//            { 5, 5, 0, 5, 5, 0, 5, 5 },   // busto/braccia
//            { 0, 0, 5, 5, 5, 5, 0, 0 },   // gambe
//            { 0, 0, 6, 0, 0, 6, 0, 0 },   // stivali
//        };
        int[][] pixel = {
                { 0, 0, 2, 2, 2, 2, 0, 0 },   // Capelli biondi (top)
                { 0, 2, 1, 1, 1, 1, 2, 0 },   // Viso e capelli ai lati
                { 0, 1, 3, 1, 1, 3, 1, 0 },   // Occhi determinati
                { 0, 4, 1, 1, 1, 1, 4, 0 },   // Colletto della tunica e mantello
                { 0, 4, 4, 5, 5, 4, 4, 0 },   // Tunica verde con spallacci in cuoio
                { 4, 4, 4, 7, 7, 4, 4, 4 },   // Cintura con fibbia d'oro al centro
                { 0, 0, 4, 0, 0, 4, 0, 0 },   // Gambe/pantaloni verdi
                { 0, 0, 6, 0, 0, 6, 0, 0 }    // Stivali robusti da viaggio
        };
//        Color[] colori = {
//            Color.TRANSPARENT,        // 0
//            Color.web("#FFCC80"),      // 1 pelle
//            Color.web("#4E342E"),      // 2 capelli
//            Color.web("#EF5350"),      // 3 bocca
//            Color.web("#1A237E"),      // 4 occhi
//            Color.web("#1565C0"),      // 5 corpo
//            Color.web("#37474F"),      // 6 stivali
//        };
        Color[] colori = {
                Color.TRANSPARENT,          // 0
                Color.web("#FFD54F"),       // 1 pelle
                Color.web("#FFB300"),       // 2 capelli
                Color.web("#263238"),       // 3 occhi
                Color.web("#2E7D32"),       // 4 vestito verde
                Color.web("#795548"),       // 5 cuoio
                Color.web("#4E342E"),       // 6 stivali
                Color.web("#FFD700")        // 7 fibbia oro
        };

        int dimensione = 8 * PIXEL_SPRITE; // = 40px
        Canvas canvas = new Canvas(dimensione, dimensione);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setImageSmoothing(false); // pixel art: niente anti-aliasing

        for (int ry = 0; ry < 8; ry++) {
            for (int rx = 0; rx < 8; rx++) {
                int c = pixel[ry][rx];
                if (c != 0) {
                    gc.setFill(colori[c]);
                    gc.fillRect(rx * PIXEL_SPRITE, ry * PIXEL_SPRITE, PIXEL_SPRITE, PIXEL_SPRITE);
                }
            }
        }
        return canvas;
    }

    // ===================================================================
    //  Utility
    // ===================================================================

    private Color colorePerTipo(TipoAmbiente tipo) {
        return switch (tipo) {
            case STANZA  -> Color.web("#78909c");  // grigio blu
            case CAVERNA -> Color.web("#5d4037");  // marrone
            case FORESTA -> Color.web("#2e7d32");  // verde
        };
    }

    private String iconaAmbiente(TipoAmbiente tipo) {
        return switch (tipo) {
            case STANZA  -> "🏠";
            case CAVERNA -> "⛏";
            case FORESTA -> "🌲";
        };
    }
}
