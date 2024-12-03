package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import org.BookRecommender.LoggedUser;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private javafx.scene.control.Button accediBtn;
    @FXML
    private javafx.scene.control.TextField mailField;
    @FXML
    private PasswordField passField;

    @FXML
    private void handleLogin() throws Exception {   // Click btn Accedi
        if (mailField.getText().isEmpty() || passField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di accesso");
            alert.setHeaderText("Campi vuoti");
            alert.setContentText("Per favore, compila tutti i campi.");
            alert.showAndWait();
        }
        String userMail = mailField.getText();
        String userPass = passField.getText();
        LoggedUser log = new User().login(userMail, userPass);
        if (Objects.isNull(log)) {   // Se il login non è andato a buon fine
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di accesso");
            alert.setHeaderText("Mail o password sono sbagliate");
            alert.showAndWait();
        } else {    // Se il login è andato a buon fine
            // Passaggio dei parametri dell'utente tra Controller
            LoggedUserModel.user = log;
            System.out.println(LoggedUserModel.user.toString());
            switchHomepage();
        }
    }
    @FXML
    private void switchRegistrazione() throws IOException {
        new SceneSwitch(loginAnchorPane, "/org/BookRecommender/View/registrationPage.fxml");
    }
    @FXML
    private void switchHomepage() throws IOException {
        new SceneSwitch(loginAnchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}