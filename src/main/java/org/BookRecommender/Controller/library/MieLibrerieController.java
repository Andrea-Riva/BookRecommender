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
import java.util.ArrayList;
import java.util.List;
/**
* Classe controller per la visualizzazione delle librerie dell'utente
*/

public class MieLibrerieController {
    @FXML
    public GridPane myLibsGridPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public AnchorPane anchorPane;
    
    /**
    * Inizializza la visualizzazione delle librerie appartenenti all oggetto LoggedUser che esegue il metodo
     * 
     * Il metodo recupera le librerie associate all'utente attualmente loggato e le visualizza
     * dinamicamente in una griglia
     *
     * @throws IOException se si verifica un errore durante le operazioni sui file o il cambio di scena
     */
    @FXML
    private void initialize() throws IOException {
        loggedUserLabel.setText(LoggedUserModel.user.getMail());

        ArrayList<Libreria> currentUserLibs = new ArrayList<>();    
        for(Libreria lib : new JsonUtils().getLibrerie()) {
            if(lib.getProprietario().getId() == LoggedUserModel.user.getId()) { 
                currentUserLibs.add(lib); 
            }
        }

        int row = 0;
        for(Libreria lib : currentUserLibs) {
            Button libButton = new Button();
            Label numLibri = new Label();
            libButton.setText(lib.getNome());
            numLibri.setText("Libri contenuti: " +
                    String.valueOf(lib.getLibri().size()));    
            myLibsGridPane.add(libButton, 0, row);
            myLibsGridPane.add(numLibri, 2, row);

            row++;
            libButton.setOnAction(actionEvent -> {
                try {
                    LibreriaModel.libreria = new User().searchLibByNome(libButton.getText()); 
                    System.out.println(LibreriaModel.libreria);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    new SceneSwitch(anchorPane, "/org/BookRecommender/View/library/dettagliLibFromMyLibs.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    
    /**
    * Metodo per la gestione del pulsante home
    * Effettua uno switch della scena su {@code "/org/BookRecommender/View/homePage.fxml"}
    *
    * @throws IOException in caso di errori durante l'esecuzione di tale metodo
    */
    @FXML
    private void goBackHome() throws IOException { 
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
