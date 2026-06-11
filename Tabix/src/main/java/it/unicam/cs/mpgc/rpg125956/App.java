package it.unicam.cs.mpgc.rpg125956;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Tabix RPG - UniCam 125956");
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(960);
        primaryStage.setMinHeight(860);
        mostraSchermoCreazione();
        primaryStage.show();
    }

    public static void mostraSchermoCreazione() {
        SchermoCreazione schermo = new SchermoCreazione();
        Scene scena = new Scene(schermo, 420, 340);
        primaryStage.setScene(scena);
    }

    public static void mostraSchermoGioco(GestorePartita gestorePartita) {
        SchermoGioco schermo = new SchermoGioco(gestorePartita);
        Scene scena = new Scene(schermo, 960, 860);
        primaryStage.setScene(scena);
    }

    public static void mostraSchermoScontro(GestorePartita gestorePartita, Cella cella) {
        SchermoScontro schermo = new SchermoScontro(gestorePartita, cella);
        Scene scena = new Scene(schermo, 560, 480);
        primaryStage.setScene(scena);
    }
}
