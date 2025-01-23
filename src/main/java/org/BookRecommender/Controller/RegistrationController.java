package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.SecurityUtils;
import org.BookRecommender.User;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

/**
*Classe per il Controller della registrazione
*/
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
    
    /**
    * Metodo per l'handling della registrazione
    * Tutti i campi richiesti devono essere compilati, altrimenti viene visualizzato un messaggio di errore
    *
    * @throws Exception in caso di errore durante il processo di registrazione
    */
    @FXML
    private void handleRegistrazione() throws Exception {   
        if (nomeField.getText().isEmpty() || cognomeField.getText().isEmpty() || cfField.getText().isEmpty() ||
                mailField.getText().isEmpty() || passwordField.getText().isEmpty()) {   
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore di registrazione");
            alert.setHeaderText("Campi vuoti");
            alert.setContentText("Per favore, compila tutti i campi.");
            alert.showAndWait();
        } else {    
            String mail = mailField.getText();  
            String password = passwordField.getText();  
            new User().register(nomeField.getText(), cognomeField.getText(), cfField.getText(),
                    mailField.getText(), passwordField.getText());  
            System.out.println("Registrazione eseguita con successo");  
            LoggedUserModel.user = new User().login(mail, password);  e
            switchHome();
        }
    }
    
    /**
    * Metodo per gestire la creazione di una password casuale (Button genera password)
    * Richiama il metodo  {@link SecurityUtils#genera}
    * La password generata viene copiata su tastiera
    */
    @FXML
    private void generatePasswordField() {  
        String genPass = new SecurityUtils().genera();
        passwordField.setText(genPass);
        genPassBtn.setText("Password copiata");
        StringSelection selection = new StringSelection(genPass);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
    
   /**
   * Cambia la scena per visualizzare la pagina di login.  
   *
   * @throws IOException se si verifica un errore nel caricare la pagina di login
   */
    @FXML
    private void switchAccesso() throws IOException { // Click btn ho già un account
        new SceneSwitch(registrazioneAnchorPane, "/org/BookRecommender/View/loginPage.fxml");
    }
    
    /**
   * Cambia la scena per visualizzare la homepage. 
   * 
   * @throws IOException se si verifica un errore nel caricare la homepage
   */
    @FXML
    private void switchHome() throws IOException {  // Click btn continua senza account
        new SceneSwitch(registrazioneAnchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
