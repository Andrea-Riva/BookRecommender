package org.BookRecommender.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import org.BookRecommender.LoggedUser;
import org.BookRecommender.SecurityUtils;
import org.BookRecommender.User;

import java.awt.*;
import java.util.Objects;

public class LoginController {
    @FXML
    private javafx.scene.control.Button accediBtn;
    @FXML
    private javafx.scene.control.Button indietroBtn;
    @FXML
    private javafx.scene.control.Button noAccountBtn;
    @FXML
    private javafx.scene.control.TextField mailField;
    @FXML
    private PasswordField passField;
    @FXML
    private Label loggedLabel;

    @FXML
    private void handleLogin() throws Exception {   // Click btn Accedi
        if(mailField.getText().isEmpty() || passField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di accesso");
            alert.setHeaderText("Campi vuoti");
            alert.setContentText("Per favore, compila tutti i campi.");
            alert.showAndWait();
        }
        String userMail = mailField.getText();
        String userPass = passField.getText();
        LoggedUser log = new User().login(userMail, userPass);
        if(Objects.isNull(log)) {   // Se il login non è andato a buon fine
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di accesso");
            alert.setHeaderText("Mail o password sono sbagliate");
            alert.showAndWait();
        } else {    // Se il login è andato a buon fine
            loggedLabel.setText("Benvenuto, " + log.getNome() + "!");
        }
    }
}
