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

public class MieLibrerieController {
    @FXML
    public GridPane myLibsGridPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private void initialize() throws IOException {
        loggedUserLabel.setText(LoggedUserModel.user.getMail());

        ArrayList<Libreria> currentUserLibs = new ArrayList<>();    // Lista di tutte le librerie dell'utente
        // Popolazione della lista currentUserLibs
        for(Libreria lib : new JsonUtils().getLibrerie()) {
            if(lib.getProprietario().getId() == LoggedUserModel.user.getId()) { // Se i proprietari concidono { Object o.equals(Object o) non funziona parte 2 }
                currentUserLibs.add(lib);   // Aggiunge la libreria alla lista delle librerie dell'utente
            }
        }

        // Display dinamico delle informazioni
        int row = 0;
        for(Libreria lib : currentUserLibs) {
            Button libButton = new Button();
            Label numLibri = new Label();
            libButton.setText(lib.getNome());
            numLibri.setText("Libri contenuti: " +
                    String.valueOf(lib.getLibri().size()));    // Il numero di libri contenuti nella libreria parsato a stringa

            myLibsGridPane.add(libButton, 0, row);
            myLibsGridPane.add(numLibri, 2, row);

            row++;
            // Assegna l'action al button
            libButton.setOnAction(actionEvent -> {
                // Assegna il campo Libreria a LibreriaModel
                try {
                    LibreriaModel.libreria = new User().searchLibByNome(libButton.getText()); // Cerca la libreria con lo stesso nome del button
                    System.out.println(LibreriaModel.libreria);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Switch scene
                try {
                    new SceneSwitch(anchorPane, "/org/BookRecommender/View/library/dettagliLibFromMyLibs.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    private void goBackHome() throws IOException { // Click btn home
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
