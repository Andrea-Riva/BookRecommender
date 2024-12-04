package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.RecensioneModel;
import org.BookRecommender.Model.SceneSwitch;

import java.io.IOException;
import java.util.Objects;

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

    @FXML
    private void initialize() {
        if(!(Objects.isNull(LoggedUserModel.user))) {
            loggedUserLabel.setText(LoggedUserModel.user.getMail());
        }
        // Set fields per libro e pubblicatore
        referredLibroLabel.setText(RecensioneModel.review.getReferredLibro().getTitolo());
        publisherField.setText(RecensioneModel.review.getPublisher().getNome() + " " +
                RecensioneModel.review.getPublisher().getCognome());
        // Crea dinamicamente i label per i voti
        Label stile = new Label();
        Label contenuto = new Label();
        Label gradevolezza = new Label();
        Label originalità = new Label();
        Label edizione = new Label();
        // Display voti
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

    @FXML
    private void goToRecensioniLibro() throws IOException {    // Click btn Indietro
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/reviewsFromHome.fxml");
    }
}
