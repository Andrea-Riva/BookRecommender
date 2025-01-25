package org.BookRecommender.Controller.library;

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
/**
* Classe controller per i dettagli della libreria di un utente.
*/
public class DettagliLibreriaController {
    @FXML
    public GridPane bookGridPane;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public Label nomeLibLabel;
    /**
   * Inizializza la visualizzazione dei dettagli della libreria e dei suoi libri.
   * 
   * Questo metodo verifica se un utente è loggato, visualizza la sua email,
   * imposta il nome della libreria selezionata e popola una griglia con i libri contenuti.
   * Ogni libro è rappresentato da un'etichetta e un pulsante per visualizzarne i dettagli.
   */

    @FXML
    private void initialize() {
        if(!(Objects.isNull(LoggedUserModel.user))) {   
            loggedUserLabel.setText(LoggedUserModel.user.getMail());    
        }
        nomeLibLabel.setText(LibreriaModel.libreria.getNome());
        int row = 0;    
        for(Libro libro : LibreriaModel.libreria.getLibri()) {  
            Label libroLabel = new Label();
            Button libroButton = new Button();

            libroLabel.setText(libro.getTitolo());
            libroButton.setText("Dettagli");

            bookGridPane.add(libroLabel, 0, row);
            bookGridPane.add(libroButton, 1, row);
            row++; 

            libroButton.setOnAction(actionEvent -> {
                try {
                    LibroModel.libro = new User().searchLibroByTitolo(libroLabel.getText());
                    System.out.println(libroButton.getText());
                    System.out.println(libro.getTitolo());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    new SceneSwitch(anchorPane, "/org/BookRecommender/View/dettagliLibro/dettagliLibroFromLib.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    
    /**
    * Metodo per la gestione del pulsante "indietro"
    * Effettua uno switch della scena visualizzado tutte le librerie.
    *
    * @throws exception in caso di anomalie durante l'esecuzione del metodo
    */
    @FXML
    private void goToAllLibs() throws IOException {    
            new SceneSwitch(anchorPane, "/org/BookRecommender/View/library/allUsersLibs.fxml");
    }
    
      /**
    * Metodo per la gestione del pulsante "MyLibs"
    * Effettua uno switch della scena visualizzado le librerie dell'utente.
    *
    * @throws exception in caso di anomalie durante l'esecuzione del metodo
    */
    @FXML
    private void goToMyLibs() throws IOException {  
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/library/mieLibrerie.fxml");
    }
}
