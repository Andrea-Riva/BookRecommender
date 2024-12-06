package org.BookRecommender.Controller.library;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.Libreria;
import org.BookRecommender.Libro;
import org.BookRecommender.Model.LibreriaModel;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CreateLibreriaController {
    @FXML
    public GridPane bookGridPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public TextField nomeLibTextField;
    @FXML
    public TextField cercaLibriTextField;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private void initialize() {
        loggedUserLabel.setText(LoggedUserModel.user.getMail());    // Se accede a questa sezione, l'utente è sempre loggato
        LibreriaModel.libreria = new Libreria   // Inizializzazione nuova libreria
                (LoggedUserModel.user, nomeLibTextField.getText(), new ArrayList<Libro>());
        System.out.println(LibreriaModel.libreria.toString());
    }

    @FXML
    private void addLibro() throws Exception {   // On click btn aggiungi libro
        Libro libroToAdd = new User().searchLibroByTitolo(cercaLibriTextField.getText());
        boolean isInLibreria = false;  // libriToAdd.contains(libroToAdd) non funzionava
        for(Libro l : LibreriaModel.libriToAdd) {
            System.out.println(l.getTitolo());
            System.out.println(libroToAdd.getTitolo());
            if(l.getTitolo().equals(libroToAdd.getTitolo())) {
                isInLibreria = true;
            }
        }
        if(Objects.isNull(libroToAdd)) {    // Se il libro non è stato trovato
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di ricerca");
            alert.setHeaderText("Il libro non è presente nel dataset");
            alert.setContentText("Per favore, prova con un altro titolo");
            alert.showAndWait();    // Mostra un messaggio di errore
        } else if(isInLibreria) {  // Se il libro è già stato aggiunto alla lib
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di inserimento");
            alert.setHeaderText("Hai già aggiunto il libro in libreria");
            alert.setContentText("Per favore, prova con un altro titolo");
            alert.showAndWait();    // Mostra un messaggio di errore
        } else {    // Se il libro è stato trovato
            LibreriaModel.libriToAdd.add(libroToAdd);   // Aggiunge il libro al Model
            // Aggiunge dinamicamente il libro alla grid
            Label titoloLabel = new Label();
            titoloLabel.setText(libroToAdd.getTitolo());    // Rinomina il label col titolo del libro trovato
            bookGridPane.add(titoloLabel, 0, LibreriaModel.rowGridPanel);
            LibreriaModel.rowGridPanel++;
        }
        // Clear testo del TextField
        cercaLibriTextField.setText("");
    }

    @FXML
    private void creaLibreria() throws IOException {
        // Crea libreria
        LibreriaModel.libreria.setNome(nomeLibTextField.getText());
        LibreriaModel.libreria.setLibri(LibreriaModel.libriToAdd);
        LoggedUserModel.user.addLibreria(LibreriaModel.libreria);
        // Clear Model
        LibreriaModel.libreria = null;
        LibreriaModel.libriToAdd.clear();
        LibreriaModel.rowGridPanel = 0;
        // Switch Scene
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/library/allUsersLibs.fxml");
    }

    @FXML
    private void goToHome() throws IOException {   // Click btn home
        // Clear Model
        LibreriaModel.libreria = null;
        LibreriaModel.libriToAdd.clear();
        LibreriaModel.rowGridPanel = 0;
        // Switch scene
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
