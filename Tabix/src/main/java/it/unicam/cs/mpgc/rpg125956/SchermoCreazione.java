package it.unicam.cs.mpgc.rpg125956;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Schermata di creazione del personaggio.
 * Permette all'utente di inserire il nome e visualizzare le statistiche iniziali.
 */
public class SchermoCreazione extends VBox {

    public SchermoCreazione() {
        super(16);
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);

        // Titolo
        Label titolo = new Label("⚔  Crea il tuo Personaggio");
        titolo.setFont(Font.font("System", FontWeight.BOLD, 20));

        // Campo nome
        Label etichettaNome = new Label("Nome del personaggio:");
        TextField campNome = new TextField();
        campNome.setPromptText("Inserisci un nome...");
        campNome.setMaxWidth(280);

        // Statistiche iniziali
        Label etichetteStats = new Label("Statistiche iniziali:");
        etichetteStats.setFont(Font.font("System", FontWeight.BOLD, 13));

        Label stats = new Label(
                "❤  Salute:  100 / 100\n" +
                "⚡  Energia:  50 / 50\n" +
                "⭐  Livello:  1\n" +
                "⚔  Attacco:  10   🛡  Difesa:  5"
        );
        stats.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10; -fx-border-radius: 6; -fx-background-radius: 6;");

        // Bottone
        Button btnInizia = new Button("Inizia Avventura");
        btnInizia.setStyle(
                "-fx-background-color: #2e7d32; -fx-text-fill: white;" +
                "-fx-font-size: 14; -fx-padding: 8 24; -fx-background-radius: 6;"
        );
        btnInizia.setOnAction(e -> {
            String nome = campNome.getText().trim();
            if (nome.isEmpty()) {
                Alert avviso = new Alert(Alert.AlertType.WARNING);
                avviso.setTitle("Nome mancante");
                avviso.setHeaderText(null);
                avviso.setContentText("Inserisci un nome per il personaggio prima di iniziare.");
                avviso.showAndWait();
                return;
            }
            GestorePartita partita = new GestorePartita(nome, 10, 10);
            App.mostraSchermoGioco(partita);
        });

        getChildren().addAll(titolo, etichettaNome, campNome, etichetteStats, stats, btnInizia);
    }
}
