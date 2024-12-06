package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.JsonUtils;
import org.BookRecommender.Libro;
import org.BookRecommender.Model.LibroModel;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomeController {
    @FXML
    public Button creaLibButton;
    @FXML
    public Button logoutButton;
    @FXML
    public Button loginButton;
    @FXML
    private AnchorPane homeAnchorPane;
    @FXML
    private Label loggedUserLabel;
    @FXML
    private GridPane bookGridPane;
    @FXML
    private TextField ricercaTextField;

    @FXML
    public void initialize() throws IOException {   // Display delle user info e di tutti i libri presenti nel dataset
        if (!(Objects.isNull(LoggedUserModel.user))) {  // Se l'utente è loggato allora display schermata di benvenuto
            loggedUserLabel.setText(LoggedUserModel.user.getMail());
            creaLibButton.setVisible(true);    // Visualizza crea libreria se l'utente è loggato
            logoutButton.setVisible(true);
            loginButton.setVisible(false);

        } else {
            creaLibButton.setVisible(false);    // Nasconde crea libreria se l'utente non è loggato
            logoutButton.setVisible(false);     // Nasconde logout se l'utente non è loggato
            loginButton.setVisible(true);
        }
        // Display dinamico dei libri
        bookGridPane.setHgap(10);  // Distanza tra le colonne
        bookGridPane.setVgap(10);  // Distanza tra le righe

        List<Libro> allLibri = new JsonUtils().getLibri();  // Get tutti i libri nel dataset

        int row = 0;    // Contatore per la riga del grid
        for (Libro libro : allLibri) {   // Display libri nel grid
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
                    new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/dettagliLibro/dettagliLibroPage.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    public void ricercaByTitolo() throws Exception { // Click btn cerca
        LibroModel.libro = new User().searchLibroByTitolo(ricercaTextField.getText());  // Trova il libro
        // Gestisce il caso nel quale il titolo non è stato trovato
        if(Objects.isNull(LibroModel.libro)) {  // Se il libro non è stato trovato
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di ricerca");
            alert.setHeaderText("Libro non trovato");
            alert.setContentText("Il libro non è presente nella biblioteca");
            alert.showAndWait();    // Lancia un alert di non successo della ricerca
        } else {
            new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/dettagliLibro/dettagliLibroPage.fxml");    // Switch scena
        }
    }

    @FXML
    public void goBack() throws IOException {   // Click btn Logout
        LoggedUserModel.user = null;   // Effettua il logout
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
    @FXML
    public void goToRicercaAvanzata() throws IOException {  // Click Btn ricerca avanzata
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/ricercaAvanzataPage.fxml");
    }

    @FXML
    public void goToAllUsersLibs() throws IOException {    // Click btn visualizza librerie
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/library/allUsersLibs.fxml");
    }

    @FXML
    private void goToLogin() throws IOException {  // Click btn login
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/loginPage.fxml");
    }

    @FXML
    private void goToCreazioneLib() throws IOException {    // Click btn crea libreria
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/library/createLibreria.fxml");
    }
}