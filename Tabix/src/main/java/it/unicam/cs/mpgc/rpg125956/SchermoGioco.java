package it.unicam.cs.mpgc.rpg125956;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Schermata principale di gioco.
 * Mostra la mappa 10×10, la barra di stato del personaggio,
 * i pulsanti di movimento e le informazioni sulla cella corrente.
 */
public class SchermoGioco extends BorderPane {

    private static final int DIMENSIONE_CELLA = 46;

    private final GestorePartita gestorePartita;
    private final GridPane griglia;
    private final Label labelStatus;
    private final VBox pannelloInfo;

    public SchermoGioco(GestorePartita gestorePartita) {
        this.gestorePartita = gestorePartita;
        this.griglia = new GridPane();
        this.labelStatus = new Label();
        this.pannelloInfo = new VBox(8);

        costruisciLayout();
        aggiornaPosizione();
    }

    // ------------------------------------------------------------------ layout

    private void costruisciLayout() {
        // TOP — barra stato personaggio
        labelStatus.setFont(Font.font("System", FontWeight.BOLD, 13));
        HBox top = new HBox(labelStatus);
        top.setPadding(new Insets(8, 12, 8, 12));
        top.setStyle("-fx-background-color: #263238;");
        labelStatus.setStyle("-fx-text-fill: white;");
        setTop(top);

        // CENTER — griglia mappa
        griglia.setHgap(2);
        griglia.setVgap(2);
        griglia.setPadding(new Insets(10));
        costruisciGriglia();
        setCenter(griglia);

        // BOTTOM — pulsanti di movimento
        setBottom(costruisciBottoniMovimento());

        // RIGHT — info cella corrente
        pannelloInfo.setPadding(new Insets(12));
        pannelloInfo.setPrefWidth(160);
        pannelloInfo.setStyle("-fx-background-color: #eceff1;");
        setRight(pannelloInfo);
    }

    private void costruisciGriglia() {
        Mappa mappa = gestorePartita.getMappa();
        griglia.getChildren().clear();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Cella cella = mappa.getCella(x, y).orElse(null);
                if (cella == null) continue;

                Rectangle rettangolo = new Rectangle(DIMENSIONE_CELLA, DIMENSIONE_CELLA);
                rettangolo.setArcWidth(4);
                rettangolo.setArcHeight(4);
                rettangolo.setFill(colorePerTipo(cella.getTipo()));

                // Fog of war
                if (!cella.isVisitata()) {
                    rettangolo.setOpacity(0.3);
                }

                // Tooltip con info cella
                Tooltip tooltip = new Tooltip(cella.getTipo().name() + " (" + x + "," + y + ")");
                Tooltip.install(rettangolo, tooltip);

                griglia.add(rettangolo, x, y);
            }
        }
    }

    private HBox costruisciBottoniMovimento() {
        Button btnNord  = creaBotoneMovimento("▲ Nord",  Direzione.NORD);
        Button btnSud   = creaBotoneMovimento("▼ Sud",   Direzione.SUD);
        Button btnOvest = creaBotoneMovimento("◀ Ovest", Direzione.OVEST);
        Button btnEst   = creaBotoneMovimento("▶ Est",   Direzione.EST);

        HBox bottom = new HBox(10, btnOvest, btnNord, btnSud, btnEst);
        bottom.setPadding(new Insets(10));
        bottom.setAlignment(Pos.CENTER);
        bottom.setStyle("-fx-background-color: #37474f;");
        return bottom;
    }

    private Button creaBotoneMovimento(String testo, Direzione direzione) {
        Button btn = new Button(testo);
        btn.setStyle(
                "-fx-background-color: #546e7a; -fx-text-fill: white;" +
                "-fx-font-size: 13; -fx-padding: 7 18; -fx-background-radius: 5;"
        );
        btn.setOnAction(e -> {
            gestorePartita.muovi(direzione);
            aggiornaPosizione();
        });
        return btn;
    }

    // ------------------------------------------------------------------ aggiornamento

    private void aggiornaPosizione() {
        Mappa mappa = gestorePartita.getMappa();
        Esploratore esploratore = gestorePartita.getEsploratore();
        Personaggio pg = gestorePartita.getPersonaggio();
        int px = esploratore.getPosizioneX();
        int py = esploratore.getPosizioneY();

        // Aggiorna colori griglia
        griglia.getChildren().forEach(nodo -> {
            if (nodo instanceof Rectangle r) {
                int col = GridPane.getColumnIndex(nodo);
                int riga = GridPane.getRowIndex(nodo);
                Cella cella = mappa.getCella(col, riga).orElse(null);
                if (cella == null) return;

                if (col == px && riga == py) {
                    // Posizione giocatore
                    r.setFill(Color.GOLD);
                    r.setOpacity(1.0);
                } else if (cella.isVisitata()) {
                    r.setFill(colorePerTipo(cella.getTipo()));
                    r.setOpacity(1.0);
                } else {
                    r.setFill(colorePerTipo(cella.getTipo()));
                    r.setOpacity(0.3);
                }
            }
        });

        // Aggiorna barra stato
        labelStatus.setText(
                "👤 " + pg.getNome() +
                "   ❤ " + pg.getSalute() + "/" + pg.getSaluteMax() +
                "   ⚡ " + pg.getEnergia() + "/" + pg.getEnergiaMax() +
                "   ⭐ Lv." + pg.getLivello() +
                "   📍 (" + px + "," + py + ")"
        );

        // Aggiorna pannello informazioni cella
        aggiornaInfoCella(esploratore.getCellaCorrente());
    }

    private void aggiornaInfoCella(Cella cella) {
        pannelloInfo.getChildren().clear();

        Label titolo = new Label("Cella corrente");
        titolo.setFont(Font.font("System", FontWeight.BOLD, 13));

        Label tipo = new Label("🗺  " + cella.getTipo().name());
        Label pos  = new Label("📍  (" + cella.getX() + ", " + cella.getY() + ")");

        pannelloInfo.getChildren().addAll(titolo, tipo, pos);

        if (cella.getRisorse().isEmpty()) {
            pannelloInfo.getChildren().add(new Label("Nessuna risorsa"));
        } else {
            Label titoloRisorse = new Label("Risorse:");
            titoloRisorse.setFont(Font.font("System", FontWeight.BOLD, 12));
            pannelloInfo.getChildren().add(titoloRisorse);
            cella.getRisorse().forEach(r ->
                pannelloInfo.getChildren().add(new Label("  • " + r))
            );
        }
    }

    // ------------------------------------------------------------------ utility

    private Color colorePerTipo(TipoAmbiente tipo) {
        return switch (tipo) {
            case STANZA  -> Color.LIGHTGRAY;
            case CAVERNA -> Color.web("#5d4037");
            case FORESTA -> Color.web("#2e7d32");
        };
    }
}
