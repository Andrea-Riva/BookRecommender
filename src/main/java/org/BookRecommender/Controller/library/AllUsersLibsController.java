package org.BookRecommender.Controller.library;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.JsonUtils;
import org.BookRecommender.Libreria;
import org.BookRecommender.Model.LibreriaModel;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.Objects;

/**
* Classe controller per la visualizzazione di tutte le librerie degli utenti.
*/
public class AllUsersLibsController {
    @FXML
    public GridPane libGridPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public AnchorPane anchorPane;
    
    /**
   * Inizializza la visualizzazione delle librerie disponibili.
   * 
   * Questo metodo controlla se l'utente è loggato, mostra la sua email,
   * e popola dinamicamente una griglia con tutte le librerie disponibili, mostrando
   * il nome della libreria, il proprietario e il numero di libri contenuti.
   * 
   * @throws IOException se si verifica un errore durante il caricamento dei dati.
   */
    @FXML
    private void initialize() throws IOException {
        if (!(Objects.isNull(LoggedUserModel.user))) {  
            loggedUserLabel.setText(LoggedUserModel.user.getMail());   
        }

        int row = 0;    
        for(Libreria lib : new JsonUtils().getLibrerie()) {   
            Button libButton = new Button();
            Label libLabel = new Label();
            Label numLibriLabel = new Label();
            libButton.setText(lib.getNome());  
            libLabel.setText(lib.getProprietario().getNome() + " " + lib.getProprietario().getCognome());
            numLibriLabel.setText("Libri contenuti: " +
                    lib.getLibri().size()); 
            libGridPane.add(libButton, 0, row);  
            libGridPane.add(libLabel, 1, row);
            libGridPane.add(numLibriLabel, 4, row);
            row++;  
            libButton.setOnAction(actionEvent -> {
                try {
                    LibreriaModel.libreria = new User().searchLibByNome(libButton.getText()); 
                    System.out.println(LibreriaModel.libreria);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    new SceneSwitch(anchorPane, "/org/BookRecommender/View/library/dettagliLibreria.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    private void goToHome() throws IOException {
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
