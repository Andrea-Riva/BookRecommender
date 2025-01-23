package org.BookRecommender.Controller.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.Model.LibroModel;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.RecensioneModel;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.Recensione;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Classe per il controller della pagina recensioni di un libro. Gestisce l'inizializzazione e la visualizzazione 
 * delle recensioni degli utenti per un determinato libro. Il controller carica tutte le recensioni per il libro
 * selezionato e le visualizza in una griglia. Ogni recensione è associata a un bottone che permette all'utente
 * di visualizzare i dettagli della recensione.
 */
public class ReviewsFromHomeController {
    @FXML
    public GridPane reviewGridPane;
    @FXML
    public Label referredLibroLabel;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public AnchorPane anchorPane;
    
    /**
     * Inizializza la vista delle recensioni per un libro. Questo metodo viene chiamato automaticamente al 
     * caricamento della scena. 
     * 
     * @throws IOException se si verifica un errore durante l'inizializzazione
     */
    @FXML
    private void initialize() throws IOException {
        if (!(Objects.isNull(LoggedUserModel.user))) {  
            loggedUserLabel.setText(LoggedUserModel.user.getMail());   
        }
        referredLibroLabel.setText(LibroModel.libro.getTitolo());   
        List<Recensione> allRecensioni = new User().visualizzaRecensioneByLibro(LibroModel.libro.getTitolo());  
        int row = 0;   
        for (Recensione review : allRecensioni) {    
            Button userButton = new Button();    
            Label votoLabel = new Label(); 

            userButton.setText(review.getPublisher().getNome() + " " + review.getPublisher().getCognome());  
            votoLabel.setText("Voto complessivo: " +
                    String.valueOf(review.getVotoFinale()));  
            reviewGridPane.add(userButton, 0, row);
            reviewGridPane.add(votoLabel, 1, row);
            userButton.setOnAction(actionEvent -> {               
                try {
                    String[] nomeCognome = userButton.getText().split(" ");
                    System.out.println(nomeCognome[0] + " " + nomeCognome[1]);  
                    RecensioneModel.review =
                            new User().searchRecensioneByUserTitolo(nomeCognome[0], nomeCognome[1], LibroModel.libro.getTitolo());
                    System.out.println(RecensioneModel.review.toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    new SceneSwitch(anchorPane, "/org/BookRecommender/View/reviews/reviewDetails.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    private void goToHome() throws IOException {  
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
