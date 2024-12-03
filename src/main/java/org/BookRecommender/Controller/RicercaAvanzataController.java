package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.BookRecommender.Libro;
import org.BookRecommender.Model.LibroModel;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.Objects;

public class RicercaAvanzataController {
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Label loggedAccountLabel;
    @FXML
    public TextField authField;
    @FXML
    public TextField dataField;

    @FXML
    private void initialize() {
        if (!(Objects.isNull(LoggedUserModel.user))) {   // Se l'utente è loggato
            loggedAccountLabel.setText(LoggedUserModel.user.getMail()); // Display della mail
        }
    }

    @FXML
    private void ricercaByAutoreData() throws IOException {
        String auth = authField.getText();      // Campo autore
        String data = dataField.getText();      // Campo data
        Libro found = new User().searchLibroByDataAuth(auth, data); // Effettua il metodo di ricerca
        if (Objects.isNull(found)) { // Se il libro non è stato trovato
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di ricerca");
            alert.setHeaderText("Libro non trovato");
            alert.setContentText("Non è stato trovato nessun libro di " + auth +
                    " pubblicato in data " + data);
            alert.showAndWait();
        } else {    // Se il libro è stato trovato
            LibroModel.libro = found;   // Assegna al model il libro trovato
            new SceneSwitch(anchorPane, "/org/BookRecommender/View/dettagliLibroPage.fxml");    // Apre la pagina coi dettagli del libro
        }
    }

    @FXML
    private void ricercaByAutore() throws IOException {
        LibroModel.autore = authField.getText();  // Campo autore
        System.out.println(LibroModel.autore);
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/libriAuthPage.fxml");
    }

    @FXML
    private void goToHome() throws IOException {   // On click btn Home
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
