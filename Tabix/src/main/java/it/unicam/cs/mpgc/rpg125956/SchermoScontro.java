package it.unicam.cs.mpgc.rpg125956;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;

public class SchermoScontro extends BorderPane {

    private final GestorePartita gestorePartita;
    private final Cella cellaCorrente;
    private final Nemico nemico;
    private final Random random = new Random();

    private final ProgressBar hpBarGiocatore = new ProgressBar(1.0);
    private final ProgressBar hpBarNemico    = new ProgressBar(1.0);
    private final Label labelHpGiocatore     = new Label();
    private final Label labelHpNemico        = new Label();

    private final VBox logBox   = new VBox(4);
    private final Button btnAttacca = new Button("⚔  Attacca");
    private final Button btnFuggi   = new Button("🏃  Fuggi");
    private final HBox  boxAzioni   = new HBox(16, btnAttacca, btnFuggi);

    public SchermoScontro(GestorePartita gestorePartita, Cella cellaCorrente) {
        this.gestorePartita = gestorePartita;
        this.cellaCorrente  = cellaCorrente;
        this.nemico = cellaCorrente.getNemico().orElseThrow();

        costruisciLayout();
        aggiornaBarreHP();
    }

    // ───────────────────────────────────────────────────────────────────────
    //  Layout
    // ───────────────────────────────────────────────────────────────────────

    private void costruisciLayout() {
        setStyle("-fx-background-color: #1a1a2e;");

        // TOP — titolo
        Label titolo = new Label("⚔  SCONTRO!");
        titolo.setFont(Font.font("Monospaced", FontWeight.BOLD, 22));
        titolo.setStyle("-fx-text-fill: #ef5350;");
        HBox top = new HBox(titolo);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(18, 0, 10, 0));
        setTop(top);

        // CENTER — pannelli giocatore vs nemico + log
        VBox center = new VBox(14,
                creaRigaVersus(),
                creaLogPanel(),
                creaRigaAzioni()
        );
        center.setPadding(new Insets(0, 40, 20, 40));
        setCenter(center);
    }

    private HBox creaRigaVersus() {
        Personaggio pg = gestorePartita.getPersonaggio();

        // Pannello giocatore
        hpBarGiocatore.setPrefWidth(160);
        hpBarGiocatore.setStyle("-fx-accent: #66bb6a;");
        VBox panelPG = pannelloStats(
                "👤  " + pg.getNome(),
                hpBarGiocatore,
                labelHpGiocatore,
                "⚔ ATK " + pg.getStatistiche().getAttacco() +
                "   🛡 DEF " + pg.getStatistiche().getDifesa(),
                "#16213e"
        );

        // VS
        Label vs = new Label("VS");
        vs.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
        vs.setStyle("-fx-text-fill: #ffd54f;");
        VBox vsBox = new VBox(vs);
        vsBox.setAlignment(Pos.CENTER);
        vsBox.setPrefWidth(50);

        // Pannello nemico
        hpBarNemico.setPrefWidth(160);
        hpBarNemico.setStyle("-fx-accent: #ef5350;");
        VBox panelNemico = pannelloStats(
                icona(nemico.getTipo()) + "  " + nemico.getNome(),
                hpBarNemico,
                labelHpNemico,
                "⚔ ATK " + nemico.getAttacco() +
                "   🛡 DEF " + nemico.getDifesa(),
                "#1a0000"
        );

        HBox riga = new HBox(16, panelPG, vsBox, panelNemico);
        riga.setAlignment(Pos.CENTER);
        return riga;
    }

    private VBox pannelloStats(String nome, ProgressBar bar, Label labelHp,
                               String statsText, String bgColor) {
        Label lblNome  = new Label(nome);
        lblNome.setFont(Font.font("Monospaced", FontWeight.BOLD, 14));
        lblNome.setStyle("-fx-text-fill: #eceff1;");

        labelHp.setFont(Font.font("Monospaced", 12));
        labelHp.setStyle("-fx-text-fill: #b0bec5;");

        Label lblStats = new Label(statsText);
        lblStats.setFont(Font.font("Monospaced", 11));
        lblStats.setStyle("-fx-text-fill: #90a4ae;");

        VBox box = new VBox(6, lblNome, bar, labelHp, lblStats);
        box.setPadding(new Insets(12));
        box.setPrefWidth(200);
        box.setStyle("-fx-background-color: " + bgColor + ";" +
                     "-fx-background-radius: 8; -fx-border-color: #37474f;" +
                     "-fx-border-radius: 8; -fx-border-width: 1;");
        return box;
    }

    private ScrollPane creaLogPanel() {
        logBox.setPadding(new Insets(8));
        logBox.setStyle("-fx-background-color: #0d0d1a;");

        ScrollPane scroll = new ScrollPane(logBox);
        scroll.setPrefHeight(170);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #0d0d1a; -fx-border-color: #37474f;");
        scroll.vvalueProperty().bind(logBox.heightProperty());

        aggiungiLog("⚔  Uno " + nemico.getNome() + " ti blocca la strada!", "#ffd54f");
        return scroll;
    }

    private HBox creaRigaAzioni() {
        stilizzaBotone(btnAttacca, "#c62828", "#ef5350");
        stilizzaBotone(btnFuggi,   "#1565c0", "#42a5f5");

        btnAttacca.setOnAction(e -> eseguiTurnoAttacco());
        btnFuggi  .setOnAction(e -> eseguiTurnoFuga());

        boxAzioni.setAlignment(Pos.CENTER);
        return boxAzioni;
    }

    private void stilizzaBotone(Button btn, String bg, String hover) {
        btn.setStyle(
            "-fx-background-color: " + bg + "; -fx-text-fill: white;" +
            "-fx-font-size: 14; -fx-font-weight: bold;" +
            "-fx-padding: 10 28; -fx-background-radius: 8;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: " + hover + "; -fx-text-fill: white;" +
            "-fx-font-size: 14; -fx-font-weight: bold;" +
            "-fx-padding: 10 28; -fx-background-radius: 8;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: " + bg + "; -fx-text-fill: white;" +
            "-fx-font-size: 14; -fx-font-weight: bold;" +
            "-fx-padding: 10 28; -fx-background-radius: 8;"
        ));
    }

    // ───────────────────────────────────────────────────────────────────────
    //  Logica di combattimento
    // ───────────────────────────────────────────────────────────────────────

    private void eseguiTurnoAttacco() {
        Personaggio pg = gestorePartita.getPersonaggio();

        // Turno del giocatore
        int dannoANemico = Math.max(1, pg.getStatistiche().getAttacco() - nemico.getDifesa());
        nemico.applicaDanno(dannoANemico);
        aggiungiLog("Hai colpito " + nemico.getNome() + " per " + dannoANemico + " danni.", "#a5d6a7");

        if (!nemico.isVivo()) {
            onVittoria();
            return;
        }

        // Turno del nemico
        int dannoAGiocatore = Math.max(1, nemico.getAttacco() - pg.getStatistiche().getDifesa());
        pg.applicaDanno(dannoAGiocatore);
        aggiungiLog(nemico.getNome() + " ti ha colpito per " + dannoAGiocatore + " danni.", "#ef9a9a");

        aggiornaBarreHP();

        if (!pg.isVivo()) {
            onSconfitta();
        }
    }

    private void eseguiTurnoFuga() {
        Personaggio pg = gestorePartita.getPersonaggio();
        boolean fuggito = random.nextInt(100) < 50;

        if (fuggito) {
            aggiungiLog("Sei riuscito a fuggire!", "#fff176");
            disabilitaAzioni();
            mostraBottoneContinua("🏃  Sei fuggito");
        } else {
            aggiungiLog("Fuga fallita!", "#ef9a9a");
            int danno = Math.max(1, nemico.getAttacco() - pg.getStatistiche().getDifesa());
            pg.applicaDanno(danno);
            aggiungiLog(nemico.getNome() + " ti ha colpito per " + danno + " danni.", "#ef9a9a");
            aggiornaBarreHP();
            if (!pg.isVivo()) onSconfitta();
        }
    }

    private void onVittoria() {
        Personaggio pg = gestorePartita.getPersonaggio();
        int xp = nemico.getXpRicompensa();
        pg.aggiungiEsperienza(xp);
        cellaCorrente.rimuoviNemico();
        aggiornaBarreHP();
        aggiungiLog("Hai sconfitto " + nemico.getNome() + "! +" + xp + " XP", "#ffd54f");
        disabilitaAzioni();
        mostraBottoneContinua("🏆  Vittoria!");
    }

    private void onSconfitta() {
        aggiornaBarreHP();
        aggiungiLog("Sei stato sconfitto...", "#ef5350");
        disabilitaAzioni();
        mostraBottoneContinua("💀  Sconfitta");
    }

    // ───────────────────────────────────────────────────────────────────────
    //  Helpers UI
    // ───────────────────────────────────────────────────────────────────────

    private void aggiornaBarreHP() {
        Personaggio pg = gestorePartita.getPersonaggio();
        hpBarGiocatore.setProgress((double) pg.getSalute() / pg.getSaluteMax());
        labelHpGiocatore.setText("❤  " + pg.getSalute() + " / " + pg.getSaluteMax());

        hpBarNemico.setProgress((double) nemico.getSalute() / nemico.getSaluteMax());
        labelHpNemico.setText("❤  " + nemico.getSalute() + " / " + nemico.getSaluteMax());
    }

    private void aggiungiLog(String messaggio, String colore) {
        Label lbl = new Label(messaggio);
        lbl.setStyle("-fx-text-fill: " + colore + "; -fx-font-family: Monospaced; -fx-font-size: 12;");
        logBox.getChildren().add(lbl);
    }

    private void disabilitaAzioni() {
        btnAttacca.setDisable(true);
        btnFuggi.setDisable(true);
    }

    private void mostraBottoneContinua(String etichetta) {
        Label risultato = new Label(etichetta);
        risultato.setFont(Font.font("Monospaced", FontWeight.BOLD, 16));
        risultato.setStyle("-fx-text-fill: #ffd54f;");

        Button btnContinua = new Button("Continua →");
        btnContinua.setStyle(
            "-fx-background-color: #2e7d32; -fx-text-fill: white;" +
            "-fx-font-size: 14; -fx-font-weight: bold;" +
            "-fx-padding: 10 28; -fx-background-radius: 8;"
        );
        btnContinua.setOnAction(e -> App.mostraSchermoGioco(gestorePartita));

        boxAzioni.getChildren().setAll(risultato, btnContinua);
    }

    private String icona(TipoNemico tipo) {
        return switch (tipo) {
            case GOBLIN    -> "👺";
            case SCHELETRO -> "💀";
            case TROLL     -> "👹";
        };
    }
}
