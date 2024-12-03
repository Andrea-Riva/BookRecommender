package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.BookRecommender.Model.LibroModel;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.SceneSwitch;

import java.io.IOException;
import java.util.Objects;

public class DettagliFromLibController {
    @FXML
    public TextArea descrizioneTextArea;
    @FXML
    AnchorPane anchorPane;
    @FXML
    public Label titoloLabel;
    @FXML
    public Label autoreLabel;
    @FXML
    public Label categoriaLabel;
    @FXML
    public Label pubblicatoreLabel;
    @FXML
    public Label prezzoLabel;
    @FXML
    private Label loggedUserLabel;

    @FXML
    private void initialize() {
        if (Objects.isNull(LoggedUserModel.user)) {  // Non loggato
            loggedUserLabel.setText("Non loggato");
        } else {
            loggedUserLabel.setText(LoggedUserModel.user.getMail());    // Display dell'utente correntemente loggato
        }
        displayLibroDetails();  // Display dei dettagli del libro
    }

    private void displayLibroDetails() {
        // Display delle informazioni tramite label
        titoloLabel.setText("Titolo: " + LibroModel.libro.getTitolo());
        autoreLabel.setText("Autore: " + LibroModel.libro.getAutore());
        descrizioneTextArea.setText("Descrizione: " + LibroModel.libro.getDescrizione());
        categoriaLabel.setText("Categoria: " + LibroModel.libro.getCategoria());
        pubblicatoreLabel.setText("Pubblicatore: " + LibroModel.libro.getPubblicatore());
        prezzoLabel.setText("Prezzo: " + String.valueOf(LibroModel.libro.getPrezzo()));
    }

    @FXML
    private void goToLibreria() throws IOException {
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/dettagliLibreria.fxml");
    }
}
