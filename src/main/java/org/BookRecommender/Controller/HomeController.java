package org.BookRecommender.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.BookRecommender.LoggedUser;

public class HomeController {
    @FXML
    private AnchorPane homeAnchorPane;
    @FXML
    private Label loggedUserLabel;
    @FXML
    private Label welcomeUserLabel;
    @FXML
    public void displayLoggedUser(LoggedUser log) {   // Display del logged user, se ha fatto login o registrazione
        String nome = log.getNome();
        String mail = log.getMail();
        loggedUserLabel.setText(mail);
        welcomeUserLabel.setText("Benvenuto, " + nome);
    }
}