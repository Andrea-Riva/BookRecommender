package org.BookRecommender.Controller.dettagliLibro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.BookRecommender.Model.LibroModel;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.SceneSwitch;

import java.io.IOException;
import java.util.Objects;

/**
* Classe controller per la visualizzazione dei dettagli di un libro selezionato.
*
*/
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
    /**
    * Inizializza la schermata dei dettagli del libro.
    * 
    * Controlla se l'utente è loggato e visualizza la sua email,
    * quindi mostra i dettagli del libro selezionato.
    */
    @FXML
    private void initialize() {
        if (Objects.isNull(LoggedUserModel.user)) {  
            loggedUserLabel.setText("Non loggato");
        } else {
            loggedUserLabel.setText(LoggedUserModel.user.getMail());    
        }
        displayLibroDetails();  
    }
     /**
     * Metodo per la visualizzazione dei dettagli del libro selezionato.
     * 
     * Recupera i dati dal modello del libro e li visualizza
     * nei rispettivi campi della schermata.
     */
    private void displayLibroDetails() {
        titoloLabel.setText("Titolo: " + LibroModel.libro.getTitolo());
        autoreLabel.setText("Autore: " + LibroModel.libro.getAutore());
        descrizioneTextArea.setText("Descrizione: " + LibroModel.libro.getDescrizione());
        categoriaLabel.setText("Categoria: " + LibroModel.libro.getCategoria());
        pubblicatoreLabel.setText("Pubblicatore: " + LibroModel.libro.getPubblicatore());
        prezzoLabel.setText("Prezzo: " + String.valueOf(LibroModel.libro.getPrezzo()));
    }
     /**
     * Torna alla schermata dei dettagli della libreria.
     * 
     * @throws IOException se si verifica un errore nel cambio di scena
     */
    @FXML
    private void goToLibreria() throws IOException {
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/library/dettagliLibreria.fxml");
    }
}
