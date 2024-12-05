package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.Libro;
import org.BookRecommender.Model.LibreriaModel;
import org.BookRecommender.Model.LibroModel;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.Objects;

public class DettagliLibreriaController {
    @FXML
    public GridPane bookGridPane;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public Label nomeLibLabel;

    @FXML
    private void initialize() {
        if(!(Objects.isNull(LoggedUserModel.user))) {   // Utente loggato
            loggedUserLabel.setText(LoggedUserModel.user.getMail());    // Display mail
        }
        // Set nome della libreria
        nomeLibLabel.setText(LibreriaModel.libreria.getNome());
        // Display libri della libreria del bottone clickato
        int row = 0;    // Counter
        for(Libro libro : LibreriaModel.libreria.getLibri()) {  // Scorre tutti i libri presenti nella libreria
            Label libroLabel = new Label();
            Button libroButton = new Button();

            libroLabel.setText(libro.getTitolo());
            libroButton.setText("Dettagli");

            bookGridPane.add(libroLabel, 0, row);
            bookGridPane.add(libroButton, 1, row);
            row++; // Aumenta il contatore

            // Attribuisce OnAction al button
            libroButton.setOnAction(actionEvent -> { // L'evento apre una nuova pagina FXML e fa il display dei dettagli del libro
                // Assegna il campo Libro a LibroModel
                try {
                    LibroModel.libro = new User().searchLibroByTitolo(libroLabel.getText());
                    System.out.println(libroButton.getText());
                    System.out.println(libro.getTitolo());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // Switch scene
                try {
                    new SceneSwitch(anchorPane, "/org/BookRecommender/View/dettagliLibro/dettagliLibroFromLib.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    private void goToAllLibs() throws IOException {    // Click btn Indietro
            new SceneSwitch(anchorPane, "/org/BookRecommender/View/allUsersLibs.fxml");
    }
}
