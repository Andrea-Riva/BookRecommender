package org.BookRecommender.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.BookRecommender.LoggedUser;
import org.BookRecommender.Model.UserInfo;

public class HomeController {
    @FXML
    private AnchorPane homeAnchorPane;
    @FXML
    private Label loggedUserLabel;
    @FXML
    private Label welcomeUserLabel;
    @FXML
    public void initialize() {   // Display del logged user, se ha fatto login o registrazione
        loggedUserLabel.setText(UserInfo.user.getMail());
        welcomeUserLabel.setText("Benvenuto, " + UserInfo.user.getNome());
    }
}