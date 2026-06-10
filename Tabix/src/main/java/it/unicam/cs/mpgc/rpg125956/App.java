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
        primaryStage.setMinWidth(820);
        primaryStage.setMinHeight(660);
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
        Scene scena = new Scene(schermo, 820, 650);
        primaryStage.setScene(scena);
    }
}
