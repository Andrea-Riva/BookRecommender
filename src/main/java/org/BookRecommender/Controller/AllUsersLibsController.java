package org.BookRecommender.Controller;

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

public class AllUsersLibsController {
    @FXML
    public GridPane libGridPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private void initialize() throws IOException {
        if (!(Objects.isNull(LoggedUserModel.user))) {   // Se l'utente è loggato
            loggedUserLabel.setText(LoggedUserModel.user.getMail());    // Display mail
        }

        int row = 0;    // Contatore per le row
        for(Libreria lib : new JsonUtils().getLibrerie()) {   // Per ogni libreria presente
            System.out.println(lib.toString());
            Button libButton = new Button();
            Label libLabel = new Label();
            libButton.setText(lib.getNome());  // Il bottone avrà il nome della libreria
            libLabel.setText("Proprietario: " + lib.getProprietario().getNome() + " " + lib.getProprietario().getCognome());
            libGridPane.add(libButton, 0, row);   // Aggiunta bottone alla grid
            libGridPane.add(libLabel, 1, row);
            row++;  // Incremento contatore
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
