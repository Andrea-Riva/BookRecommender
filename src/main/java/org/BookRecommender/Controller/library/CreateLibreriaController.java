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

/**
* Classe controller per la creazione di una libreria personale.
*/
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
    
    /**
    * Metodo per inizializzare la sezione della libreria per l'utente LoggedUser
    * Il nome della libreria è scelto arbitrariamente dall'utente mediante l'apposito TextField
    */
    @FXML
    private void initialize() {
        loggedUserLabel.setText(LoggedUserModel.user.getMail());    
        LibreriaModel.libreria = new Libreria  
                (LoggedUserModel.user, nomeLibTextField.getText(), new ArrayList<Libro>());
        System.out.println(LibreriaModel.libreria.toString());
    }
    
    /**
    * Metodo per aggiungere un libro ad una libreria utente.
    * Questo metodo ricerca il libro nel dataset utilizzando il titolo.
    * Il libro viene aggiunto solo se non è presente nella libreria mediante
    * il controllo {@code  boolean isInLibreria = false;}, se il libro non 
    * viene trovato, appare un messaggio di errore, allo stesso modo se il
    * libro è già presente.
    *
    * @throes Eception se si verifica un errore nella ricerca del libro
    */
    @FXML
    private void addLibro() throws Exception {  
        Libro libroToAdd = new User().searchLibroByTitolo(cercaLibriTextField.getText());
        boolean isInLibreria = false;  
        for(Libro l : LibreriaModel.libriToAdd) {
            System.out.println(l.getTitolo());
            System.out.println(libroToAdd.getTitolo());
            if(l.getTitolo().equals(libroToAdd.getTitolo())) {
                isInLibreria = true;
            }
        }
        if(Objects.isNull(libroToAdd)) {    
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di ricerca");
            alert.setHeaderText("Il libro non è presente nel dataset");
            alert.setContentText("Per favore, prova con un altro titolo");
            alert.showAndWait();    
        } else if(isInLibreria) {  
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di inserimento");
            alert.setHeaderText("Hai già aggiunto il libro in libreria");
            alert.setContentText("Per favore, prova con un altro titolo");
            alert.showAndWait();    
        } else {    
            LibreriaModel.libriToAdd.add(libroToAdd);  
            Label titoloLabel = new Label();
            titoloLabel.setText(libroToAdd.getTitolo());    
            bookGridPane.add(titoloLabel, 0, LibreriaModel.rowGridPanel);
            LibreriaModel.rowGridPanel++;
        }
        cercaLibriTextField.setText("");
    }
    
    /**
    * Metodo per creare una libreria, il nome è specificato dall'utente nel TextField.
    *
    * @throws IOException se si verifica un errore nel cambio di scena
    */
    @FXML
    private void creaLibreria() throws IOException {
        LibreriaModel.libreria.setNome(nomeLibTextField.getText());
        LibreriaModel.libreria.setLibri(LibreriaModel.libriToAdd);
        LoggedUserModel.user.addLibreria(LibreriaModel.libreria);
        LibreriaModel.libreria = null;
        LibreriaModel.libriToAdd.clear();
        LibreriaModel.rowGridPanel = 0;
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/library/allUsersLibs.fxml");
    }
    
    /**
   * Torna alla homepage dell'utente.
   * 
   * Questo metodo svuota il modello della libreria e cambia la scena alla homepage.
   * 
   * @throws IOException se si verifica un errore nel cambio di scena
   */
    @FXML
    private void goToHome() throws IOException {   
        LibreriaModel.libreria = null;
        LibreriaModel.libriToAdd.clear();
        LibreriaModel.rowGridPanel = 0;
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
