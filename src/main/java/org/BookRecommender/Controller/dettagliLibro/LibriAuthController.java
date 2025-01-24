package org.BookRecommender.Controller.dettagliLibro;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.Libro;
import org.BookRecommender.Model.LibroModel;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
 /**
 * Classe controller per la visualizzazione dei libri di un autore specifico.
 * Visualizza i risultati in una griglia.
 */
public class LibriAuthController {
    @FXML
    public ScrollPane bookScrollPane;
    @FXML
    public GridPane bookGridPane;
    @FXML
    public Label loggedUserLabel;
    @FXML
    public AnchorPane anchorPane;
     /**
     * Inizializza la schermata di ricerca dei libri per autore.
     * 
     * Verifica se l'utente è loggato e visualizza la sua email. Effettua la ricerca dei libri
     * dell'autore selezionato e li visualizza nella griglia.
     *
     * Se l'autore non viene trovato, visualizza un messaggio di errore.
     * 
     * @throws IOException se si verifica un errore durante il caricamento della scena.
     */
    @FXML
    private void initialize() throws IOException {
        if (!(Objects.isNull(LoggedUserModel.user))) {  
            loggedUserLabel.setText(LoggedUserModel.user.getMail());    
        }
        List<Libro> libriFound = new User().searchLibriByAuth(LibroModel.autore);
        if (libriFound.isEmpty()) {  
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di ricerca");
            alert.setHeaderText("Autore non trovato");
            alert.setContentText("nessun libro di " + LibroModel.autore + " in archivio");
            alert.showAndWait();   
            new SceneSwitch(anchorPane, "org/BookRecommender/View/ricercaAvanzataPage.fxml");
        } else {    
            int row = 0;    
            for (Libro libro : libriFound) {
                Label libroLabel = new Label();
                Button libroButton = new Button();

                libroLabel.setText(libro.getTitolo());
                libroButton.setText("Dettagli");

                bookGridPane.add(libroLabel, 0, row);
                bookGridPane.add(libroButton, 1, row);
                //System.out.println(libro.getTitolo());
                row++; 

                libroButton.setOnAction(actionEvent -> { 
                    try {
                        LibroModel.libro = new User().searchLibroByTitolo(libroLabel.getText());
                        System.out.println(libroButton.getText());
                        System.out.println(libro.getTitolo());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        new SceneSwitch(anchorPane, "/org/BookRecommender/View/dettagliLibro/dettagliLibroFromAuth.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    @FXML
    private void goToHome() throws IOException {   
        new SceneSwitch(anchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
}
