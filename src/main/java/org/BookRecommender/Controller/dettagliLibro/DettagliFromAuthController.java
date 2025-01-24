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
* Classe controller per il display dei dettagli di un libro.
*/
public class DettagliFromAuthController {
    @FXML
    public Label titoloLabel;
    @FXML
    public Label autoreLabel;
    @FXML
    public TextArea descrizioneTextArea;
    @FXML
    public Label categoriaLabel;
    @FXML
    public Label pubblicatoreLabel;
    @FXML
    public Label prezzoLabel;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public AnchorPane anchorPane;

    /**
    * Metodo initialize, effettua un controllo per determinare se l'utente è di tipo LoggedUser,
    * in caso contrario viene visualizzato il messaggio "Non loggato".
    * Se l'utente risulta loggato, effettua il display dei dettagli del lbro.
    */
    @FXML
    public void initialize() {
        if (Objects.isNull(LoggedUserModel.user)) {  
            loggedUserLabel.setText("Non loggato");
        } else {
            loggedUserLabel.setText(LoggedUserModel.user.getMail());    
        }
        displayLibroDetails();  
    }
    
    /**
    * Metodo per il display delle informazioni di un libro.
    */
    public void displayLibroDetails() {
        titoloLabel.setText("Titolo: " + LibroModel.libro.getTitolo());
        autoreLabel.setText("Autore: " + LibroModel.libro.getAutore());
        descrizioneTextArea.setText("Descrizione: " + LibroModel.libro.getDescrizione());
        categoriaLabel.setText("Categoria: " + LibroModel.libro.getCategoria());
        pubblicatoreLabel.setText("Pubblicatore: " + LibroModel.libro.getPubblicatore());
        prezzoLabel.setText("Prezzo: " + String.valueOf(LibroModel.libro.getPrezzo()));
    }
    
    /**
    * Metodo per il cambio di scena su {@code /org/BookRecommender/View/dettagliLibro/libriAuthPage.fxml}
    */
    @FXML
    public void goBackLibriAutore() throws IOException {
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/dettagliLibro/libriAuthPage.fxml");
    }
}
