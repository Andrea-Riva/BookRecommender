package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.BookRecommender.LoggedUser;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
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
            // Passa i dati del login e cambia scena
            switchHome();
        }
    }

    @FXML
    private void switchRegistration() throws IOException { // Click btn indietro
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/BookRecommender/View/registrationPage.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) accediBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void switchHome() throws IOException {  // Click btn continua senza account
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/BookRecommender/View/homePage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) accediBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
