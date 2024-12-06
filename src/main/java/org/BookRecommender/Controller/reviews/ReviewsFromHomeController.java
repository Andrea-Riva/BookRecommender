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

public class ReviewsFromHomeController {
    @FXML
    public GridPane reviewGridPane;
    @FXML
    public Label referredLibroLabel;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private void initialize() throws IOException {
        if (!(Objects.isNull(LoggedUserModel.user))) {   // Utente loggato
            loggedUserLabel.setText(LoggedUserModel.user.getMail());    // Display mail account
        }
        referredLibroLabel.setText(LibroModel.libro.getTitolo());   // Display libro recensito
        List<Recensione> allRecensioni = new User().visualizzaRecensioneByLibro(LibroModel.libro.getTitolo());  // Get tutte le recensioni di quel libro
        int row = 0;    // Counter per le row
        for (Recensione review : allRecensioni) {    // Itera tutte le recensioni
            Button userButton = new Button();    // Bottone per la recensione specifica
            Label votoLabel = new Label();  // Voto finale

            userButton.setText(review.getPublisher().getNome() + " " + review.getPublisher().getCognome());  // Nome e cognome utente
            votoLabel.setText("Voto complessivo: " +
                    String.valueOf(review.getVotoFinale()));  // Voto finale del libro
            // Agguinge elementi al grid
            reviewGridPane.add(userButton, 0, row);
            reviewGridPane.add(votoLabel, 1, row);
            // Attribuisce un action allo userButton
            userButton.setOnAction(actionEvent -> { // L'evento apre una nuova pagina FXML e fa il display dei dettagli del libro
                // Assegna il campo Recensione a RecensioneModel
                try {
                    // Split della stringa del button
                    String[] nomeCognome = userButton.getText().split(" ");
                    System.out.println(nomeCognome[0] + " " + nomeCognome[1]);  // Debug
                    // Assegna a RecensioneModel la recensione dell'utente su quel libro
                    RecensioneModel.review =
                            new User().searchRecensioneByUserTitolo(nomeCognome[0], nomeCognome[1], LibroModel.libro.getTitolo());
                    System.out.println(RecensioneModel.review.toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // Switch scene
                try {
                    new SceneSwitch(anchorPane, "/org/BookRecommender/View/reviews/reviewDetails.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    private void goToHome() throws IOException {   // Click btn Home
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
