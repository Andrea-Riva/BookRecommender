package org.BookRecommender.Controller.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.RecensioneModel;
import org.BookRecommender.Model.SceneSwitch;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller per la pagina dei dettagli della recensione. Questa classe gestisce l'inizializzazione e l'interazione 
 * con la vista che mostra i dettagli di una recensione, inclusi i voti per vari aspetti del libro recensito.
 * 
 * La classe si occupa di caricare e visualizzare le informazioni della recensione, come il titolo del libro, 
 * i dettagli del pubblicatore, e i voti relativi a vari aspetti del libro come stile, contenuto, gradevolezza, 
 * originalità e edizione.
 */
public class ReviewDetailsController {
    @FXML
    public GridPane reviewGridPane;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public Label referredLibroLabel;
    @FXML
    public Label publisherField;
    
    /**
     * Inizializza la vista dei dettagli della recensione. Questo metodo viene chiamato automaticamente al caricamento 
     * della scena. Imposta le etichette relative all'utente loggato, al libro e al pubblicatore. Inoltre, crea 
     * dinamicamente delle etichette per i voti relativi a vari aspetti del libro e li aggiunge alla vista.
     */
    @FXML
    private void initialize() {
        if(!(Objects.isNull(LoggedUserModel.user))) {
            loggedUserLabel.setText(LoggedUserModel.user.getMail());
        }
        referredLibroLabel.setText(RecensioneModel.review.getReferredLibro().getTitolo());
        publisherField.setText(RecensioneModel.review.getPublisher().getNome() + " " +
                RecensioneModel.review.getPublisher().getCognome());
        Label stile = new Label();
        Label contenuto = new Label();
        Label gradevolezza = new Label();
        Label originalità = new Label();
        Label edizione = new Label();
        stile.setText("Stile: " +
                String.valueOf(RecensioneModel.review.getStile()));
        contenuto.setText("Contenuto: " +
                String.valueOf(RecensioneModel.review.getContenuto()));
        gradevolezza.setText("Gradevolezza: " +
                String.valueOf(RecensioneModel.review.getGradevolezza()));
        originalità.setText("Originalità: " +
                String.valueOf(RecensioneModel.review.getOriginalità()));
        edizione.setText("Edizione: " +
                String.valueOf(RecensioneModel.review.getEdizione()));
        // Aggiungi i voti al grid
        reviewGridPane.add(stile, 0, 1);
        reviewGridPane.add(contenuto, 0, 2);
        reviewGridPane.add(gradevolezza, 0, 3);
        reviewGridPane.add(originalità, 0, 4);
        reviewGridPane.add(edizione, 0, 5);
    }
    
    /**
     * Gestisce l'azione del clic sul bottone "Indietro". 
     * 
     * @throws IOException se si verifica un errore nel caricare la pagina delle recensioni
     */
    @FXML
    private void goToRecensioniLibro() throws IOException {    // Click btn Indietro
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/reviews/reviewsFromHome.fxml");
    }
}
