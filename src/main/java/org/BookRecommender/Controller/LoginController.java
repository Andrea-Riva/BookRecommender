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
/**
* Classe per il Controller del login
*/
public class LoginController {
    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private javafx.scene.control.Button accediBtn;
    @FXML
    private javafx.scene.control.TextField mailField;
    @FXML
    private PasswordField passField;
    
    /**
    * Metodo per l'handling del login
    * Se il campo mailField o passField è vuoto viene  visualizzato u ìn messaggio di errore
    * Se le informazion inserite sono errate viene visualizzato un messaggio di errore
    * Se il login è andato a buon fine avviene il passaggio dei parametri dell'utente tra Controller
    *
    * @throws Exception se si verifica un errore durante il login
    */
    @FXML
    private void handleLogin() throws Exception {   
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
        if (Objects.isNull(log)) {   
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di accesso");
            alert.setHeaderText("Mail o password sono sbagliate");
            alert.showAndWait();
        } else {    
            LoggedUserModel.user = log;
            System.out.println(LoggedUserModel.user.toString());
            switchHomepage();
        }
    }
   /**
   * Cambia la scena per visualizzare la pagina di registrazione. Questo metodo viene chiamato quando l'utente 
   * clicca sul link o bottone di registrazione. La scena viene cambiata alla pagina di registrazione.
   * 
   * @throws IOException se si verifica un errore nel caricare la pagina di registrazione
   */
    @FXML
    private void switchRegistrazione() throws IOException {
        new SceneSwitch(loginAnchorPane, "/org/BookRecommender/View/registrationPage.fxml");
    }
    /**
   * Cambia la scena per visualizzare la homepage. Questo metodo viene chiamato dopo un login avvenuto con successo. 
   * La scena viene cambiata alla homepage dell'applicazione.
   * 
   * @throws IOException se si verifica un errore nel caricare la homepage
   */
    @FXML
    private void switchHomepage() throws IOException {
        new SceneSwitch(loginAnchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
