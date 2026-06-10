package it.unicam.cs.mpgc.rpg125956;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Punto d'ingresso dell'applicazione JavaFX.
 * Gestisce il cambio di schermata tra creazione personaggio e gioco.
 */
public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("RPG - UniCam 125956");
        primaryStage.setResizable(false);
        mostraSchermoCreazione();
        primaryStage.show();
    }

    public static void mostraSchermoCreazione() {
        SchermoCreazione schermo = new SchermoCreazione();
        Scene scena = new Scene(schermo, 400, 320);
        primaryStage.setScene(scena);
    }

    public static void mostraSchermoGioco(GestorePartita gestorePartita) {
        SchermoGioco schermo = new SchermoGioco(gestorePartita);
        Scene scena = new Scene(schermo, 700, 560);
        primaryStage.setScene(scena);
    }
}
