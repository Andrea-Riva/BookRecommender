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
 * Classe controller per la visualizzazione dei dettagli di un libro.
 * 
 * Nella classe viene gestita l'inizializzazione dell'interfaccia utente e il display
 * delle informazioni di un libro selezionato.
 */
public class DettagliLibroController {
    @FXML
    public TextArea descrizioneTextArea;
    @FXML
    AnchorPane dettagliAnchorPane;
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
     * Il metodo inizializza la schermata dei dettagli del libro.
     * 
     * Controlla se l'utente è loggato e visualizza la sua email, in caso di successo  
     * mostra i dettagli del libro selezionato.
     */
    public void initialize() {
        if(Objects.isNull(LoggedUserModel.user)) {  
            loggedUserLabel.setText("Non loggato");
        } else {
            loggedUserLabel.setText(LoggedUserModel.user.getMail());   
        }
        displayLibroDetails();  
    }
    public void displayLibroDetails() {
        titoloLabel.setText("Titolo: " + LibroModel.libro.getTitolo());
        autoreLabel.setText("Autore: " + LibroModel.libro.getAutore());
        descrizioneTextArea.setText("Descrizione: " + LibroModel.libro.getDescrizione());
        categoriaLabel.setText("Categoria: " + LibroModel.libro.getCategoria());
        pubblicatoreLabel.setText("Pubblicatore: " + LibroModel.libro.getPubblicatore());
        prezzoLabel.setText("Prezzo: " + String.valueOf(LibroModel.libro.getPrezzo()));
    }
    
    /**
    * Metodo per tornare alla pagina delle recensioni, la directory opsitante è:
    * {@code "/org/BookRecommender/View/reviews/reviewsFromHome.fxml"}
    *
    * @throws Exception in caso di errori durante il cambio di scena
    */
    @FXML
    private void goToRecensioni() throws Exception { 
        new SceneSwitch(dettagliAnchorPane, "/org/BookRecommender/View/reviews/reviewsFromHome.fxml");  
    }

    /**
    * Metodo per tornare alla homepage utente.
    *
    * @throws IOException in caso di errore durante il cambio di scena
    */
    @FXML
    public void goBackHome() throws IOException {   
        new SceneSwitch(dettagliAnchorPane, "/org/BookRecommender/View/homePage.fxml"); 
    }
}
