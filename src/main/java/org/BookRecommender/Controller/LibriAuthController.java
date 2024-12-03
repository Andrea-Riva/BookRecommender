package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.Libro;
import org.BookRecommender.Model.LibroModel;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LibriAuthController {
    @FXML
    public ScrollPane bookScrollPane;
    @FXML
    public GridPane bookGridPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private void initialize() throws IOException {
        if(!(Objects.isNull(LoggedUserModel.user))) {   // Se l'utente è loggato
            loggedUserLabel.setText(LoggedUserModel.user.getMail());    // Display mail
        }
        // Ricerca dei libri per autore specifico
        List<Libro> libriFound = new User().searchLibriByAuth(LibroModel.autore);
        // Display dei libri trovati, come in homepage
        int row = 0;    // Contatore per le row
        for(Libro libro : libriFound) {
            Label libroLabel = new Label();
            Button libroButton = new Button();

            libroLabel.setText(libro.getTitolo());
            libroButton.setText("Dettagli");

            bookGridPane.add(libroLabel, 0, row);
            bookGridPane.add(libroButton, 1, row);
            //System.out.println(libro.getTitolo());
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
                    new SceneSwitch(anchorPane, "/org/BookRecommender/View/dettagliLibroFromAuth.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    private void goToHome() throws IOException {   // Click btn home
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
