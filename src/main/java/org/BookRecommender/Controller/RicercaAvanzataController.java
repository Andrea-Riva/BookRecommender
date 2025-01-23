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

/**
* Classe per il controller della Ricerca Avanzata
*/
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
        if (!(Objects.isNull(LoggedUserModel.user))) {   
            loggedAccountLabel.setText(LoggedUserModel.user.getMail()); 
        }
    }
    
    /**
    * Metodo per la ricerca tramite Autore e Data
    * Richiama {@link User#searchLibroByDataAuth}
    *
    * Se il libro non viene trovato viene visualizzato un messaggio di errore
    * Altrimenti assegna al model il libro trovato e visualizza la pagina con i dettagli del libro
    *
    * @throws IOException in caso di errori durante la ricerca
    */
    @FXML
    private void ricercaByAutoreData() throws IOException {
        String auth = authField.getText();      
        String data = dataField.getText();      
        Libro found = new User().searchLibroByDataAuth(auth, data); 
        if (Objects.isNull(found)) { 
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di ricerca");
            alert.setHeaderText("Libro non trovato");
            alert.setContentText("Non è stato trovato nessun libro di " + auth +
                    " pubblicato in data " + data);
            alert.showAndWait();
        } else {    
            LibroModel.libro = found;   
            new SceneSwitch(anchorPane, "/org/BookRecommender/View/dettagliLibro/dettagliLibroPage.fxml");    
        }
    }

    @FXML
    private void ricercaByAutore() throws IOException {
        LibroModel.autore = authField.getText();  // Campo autore
        System.out.println(LibroModel.autore);
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/dettagliLibro/libriAuthPage.fxml");
    }
    
    /**
    * Metodo per la gestione del ritorno alla home page
    *
    * @throws IOException in caso di errori durante l'esecuzione del metodo
    */
    @FXML
    private void goToHome() throws IOException {   
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
