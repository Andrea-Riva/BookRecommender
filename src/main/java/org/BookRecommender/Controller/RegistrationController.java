package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.SecurityUtils;
import org.BookRecommender.User;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

public class RegistrationController {
    @FXML
    private AnchorPane registrazioneAnchorPane;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField cfField;
    @FXML
    private TextField mailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private javafx.scene.control.Button genPassBtn;
    @FXML
    private javafx.scene.control.Button accediBtn;

    @FXML
    private void handleRegistrazione() throws Exception {   // Click btn registrazione
        // Verifica se tutti i campi sono stati compilati
        if (nomeField.getText().isEmpty() || cognomeField.getText().isEmpty() || cfField.getText().isEmpty() ||
                mailField.getText().isEmpty() || passwordField.getText().isEmpty()) {   // Se qualcosa non è stato compilato
            // Mostra un messaggio di errore
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore di registrazione");
            alert.setHeaderText("Campi vuoti");
            alert.setContentText("Per favore, compila tutti i campi.");
            alert.showAndWait();
        } else {    // Tutti i campi sono stati compilati correttamente
            String mail = mailField.getText();  // Mail per il login
            String password = passwordField.getText();  // Password per il login
            new User().register(nomeField.getText(), cognomeField.getText(), cfField.getText(),
                    mailField.getText(), passwordField.getText());  // Esegui la registrazione
            System.out.println("Registrazione eseguita con successo");  // Debug console
            switchHome();
        }
    }

    @FXML
    private void generatePasswordField() {  // Click btn genera password
        String genPass = new SecurityUtils().genera();
        passwordField.setText(genPass);
        genPassBtn.setText("Password copiata");
        // Copia password su tastiera
        StringSelection selection = new StringSelection(genPass);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    @FXML
    private void switchAccesso() throws IOException { // Click btn ho già un account
        new SceneSwitch(registrazioneAnchorPane, "/org/BookRecommender/View/loginPage.fxml");
    }

    @FXML
    private void switchHome() throws IOException {  // Click btn continua senza account
        new SceneSwitch(registrazioneAnchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}