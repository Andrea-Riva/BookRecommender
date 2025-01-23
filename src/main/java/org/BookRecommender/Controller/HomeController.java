package org.BookRecommender.Controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.BookRecommender.JsonUtils;
import org.BookRecommender.Libro;
import org.BookRecommender.Model.LibroModel;
import org.BookRecommender.Model.SceneSwitch;
import org.BookRecommender.Model.LoggedUserModel;
import org.BookRecommender.User;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Controller per la gestione della home page dell'applicazione.
 * Gestisce la visualizzazione dei libri e le interazioni dell'utente con la home.
 */
public class HomeController {
    @FXML
    public Button creaLibButton;
    @FXML
    public Button logoutButton;
    @FXML
    public Button loginButton;
    @FXML
    public Button mieLibrerieButton;
    @FXML
    private AnchorPane homeAnchorPane;
    @FXML
    private Label loggedUserLabel;
    @FXML
    private GridPane bookGridPane;
    @FXML
    private TextField ricercaTextField;
    
    /**
    * Metodo per la gestione della home page dell'applicazione
    * La visualizzazione dipende dal tipo dell'oggetto che segue il metodo (LoggedUser o User)
    * Le due visualizzazioni vengono selezionato con il costrutto if-else iniziale
    *
    * Il Display dei libri avviene dinamicamente, la distanza tra colonne e righe è data da
    * {@code  bookGridPane.setHgap(10);  
    *         bookGridPane.setVgap(10);}
    */
    @FXML
    public void initialize() throws IOException {   
        if (!(Objects.isNull(LoggedUserModel.user))) {  
            loggedUserLabel.setText(LoggedUserModel.user.getMail());
            creaLibButton.setVisible(true);    
            logoutButton.setVisible(true);
            loginButton.setVisible(false);
            mieLibrerieButton.setVisible(true);

        } else {    
            creaLibButton.setVisible(false);    
            logoutButton.setVisible(false);     
            loginButton.setVisible(true);
            mieLibrerieButton.setVisible(false);
        }
        bookGridPane.setHgap(10);  
        bookGridPane.setVgap(10);  

        List<Libro> allLibri = new JsonUtils().getLibri();  

        int row = 0;    
        for (Libro libro : allLibri) {   
            Label libroLabel = new Label();
            Button libroButton = new Button();

            libroLabel.setText(libro.getTitolo());
            libroButton.setText("Dettagli");

            bookGridPane.add(libroLabel, 0, row);
            bookGridPane.add(libroButton, 1, row);
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
                    new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/dettagliLibro/dettagliLibroPage.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    /**
     * Metodo per la ricerca di un libro per titolo.
     * Mostra un alert se il libro non viene trovato.
     * @throws Exception Se si verifica un errore durante la ricerca.
     */
    @FXML
    public void ricercaByTitolo() throws Exception { // Click btn cerca
        LibroModel.libro = new User().searchLibroByTitolo(ricercaTextField.getText());  // Trova il libro
        // Gestisce il caso nel quale il titolo non è stato trovato
        if(Objects.isNull(LibroModel.libro)) {  // Se il libro non è stato trovato
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di ricerca");
            alert.setHeaderText("Libro non trovato");
            alert.setContentText("Il libro non è presente nella biblioteca");
            alert.showAndWait();    // Lancia un alert di non successo della ricerca
        } else {
            new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/dettagliLibro/dettagliLibroPage.fxml");    // Switch scena
        }
    }
    /**
     * Effettua il logout dell'utente e ritorna alla home page.
     * @throws IOException Se si verifica un errore durante il cambio di scena.
     */
    @FXML
    public void goBack() throws IOException {  
        LoggedUserModel.user = null;   
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/homePage.fxml");
    }
     /**
     * Naviga alla pagina di ricerca avanzata.
     * @throws IOException Se si verifica un errore durante il cambio di scena.
     */
    @FXML
    public void goToRicercaAvanzata() throws IOException {  
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/ricercaAvanzataPage.fxml");
    }
     /**
     * Naviga alla pagina delle librerie di tutti gli utenti.
     * @throws IOException Se si verifica un errore durante il cambio di scena.
     */
    @FXML
    public void goToAllUsersLibs() throws IOException {    
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/library/allUsersLibs.fxml");
    }
     /**
     * Naviga alla pagina di login.
     * @throws IOException Se si verifica un errore durante il cambio di scena.
     */
    @FXML
    private void goToLogin() throws IOException {  // Click btn login
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/loginPage.fxml");
    }
     /**
     * Naviga alla pagina di creazione libreria.
     * @throws IOException Se si verifica un errore durante il cambio di scena.
     */
    @FXML
    private void goToCreazioneLib() throws IOException {    // Click btn crea libreria
        new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/library/createLibreria.fxml");
    }
     /**
     * Naviga alla pagina delle mie librerie.
     * @throws IOException Se si verifica un errore durante il cambio di scena.
     */
    @FXML
    private void goToMieLibrerie() throws IOException { // Click btn le mie librerie
            new SceneSwitch(homeAnchorPane, "/org/BookRecommender/View/library/mieLibrerie.fxml");
    }
}
