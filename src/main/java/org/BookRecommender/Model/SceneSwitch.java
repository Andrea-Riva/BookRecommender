package org.BookRecommender.Model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.BookRecommender.App;

import java.io.IOException;
import java.util.Objects;

public class SceneSwitch {
    public SceneSwitch(AnchorPane currentAnchorPane, String fxml) throws IOException {  // Cambio schermata
        AnchorPane nextAnchorPane = FXMLLoader.load(Objects.requireNonNull(App.class.getResource(fxml))); // Richiede il file FXML al path specificato
        currentAnchorPane.getChildren().removeAll();    // Rimuove il FXML precedente
        currentAnchorPane.getChildren().setAll(nextAnchorPane);   // Aggiunge il FXML attuale
    }
}
