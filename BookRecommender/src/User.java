import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    public User() {
    }

    /**
     * Permette di cercare un libro nel dataset tramite il parametro del titolo
     *
     * @param titolo Il titolo del libro
     * @return Il libro trovato
     * @throws IOException Se la deserializzazione non va a buon fine
     */
    public Libro searchLibroByTitolo(String titolo) throws Exception {
        List<Libro> dataset = new JsonUtils().getLibri();   // Deserializza dataset
        for (Libro l : dataset) {    // Ciclo su tutta la lista
            if (l.getTitolo().equals(titolo)) return l;  // Se i titoli corrispondono
        }
        return null;    // Libro non trovato
    }

    /**
     * Permette di cercare una serie di libri scritti dallo stesso autore
     *
     * @param auth L'autore del libro
     * @return Una collection di libri aventi lo stesso autore
     * @throws IOException Se la deserializzazione non va a buon fine
     */
    public List<Libro> searchLibroByAuth(String auth) throws IOException {
        List<Libro> dataset = new JsonUtils().getLibri();
        List<Libro> libriFound = new ArrayList<Libro>();    // To return
        for (Libro l : dataset) {
            if (l.getAutore().equals(auth))
                libriFound.add(l);   // Se i titoli corrispondono aggiunge il Libro alla lista
        }
        return libriFound;
    }

    /**
     * Permette di cercare un libro scritto da un certo autore e pubblicato in una certa data
     *
     * @param auth L'autore del libro
     * @param data La data di pubblicazione del libro
     * @return Il libro scritto da un certo autore e pubblicato in una certa data
     * @throws IOException Se la deserializzazione non va a buon fine
     */
    public Libro searchLibroByDataAuth(String auth, String data) throws IOException {
        List<Libro> dataset = new JsonUtils().getLibri();
        for (Libro l : dataset) {
            if (l.getAutore().equals(auth) && l.getData().equals(data)) return l;
        }
        return null;
    }

    /**
     * Scrive in formato JSON i dati dell'utente appena registrato
     *
     * @param nome          Nome dell'utente
     * @param cognome       Cognome dell'utente
     * @param codiceFiscale Codice fiscale dell'utente
     * @param mail          Mail dell'utente
     * @param password      Password scelta dall'utente
     * @throws Exception
     */
    public void register(String nome, String cognome, String codiceFiscale, String mail, String password) throws Exception {
        JsonUtils utils = new JsonUtils();  // Utils per gestire i nodi e l'Id
        if(password.isBlank()) {    // Se il parametro non presenta caratteri
            password = new SecurityUtils().genera();    // Genera una password casuale
        }
        String encryptedPwd = new SecurityUtils().encrypt(password);    // La password crittata
        int uniqueId = utils.getUniqueId();   // L'Id univoco
        LoggedUser newUtente = new LoggedUser(uniqueId, nome, cognome, codiceFiscale, mail, encryptedPwd);  // Crea un obj LoggedUser con i campi del register
        JsonNode rootUsers = utils.getUtentiAsJsonNode();   // Root node degli utenti
        ArrayNode userArrayNode = (ArrayNode) rootUsers;    // Parsing del root node in un array di nodi
        userArrayNode.add(utils.createUserNode(newUtente)); // Aggiunge il nuovo utente all'array di nodi
        utils.writeUtentiNodes(userArrayNode);
    }

    /**
     * Fa il login se mail e password sono corrette
     *
     * @param mail     La mail dell'utente
     * @param password La password dell'utente
     * @return LoggedUser utente registrato
     */
    public LoggedUser login(String mail, String password) throws Exception {
        SecurityUtils decypher = new SecurityUtils();   // Serve per decifrare la password
        List<LoggedUser> utenti = new JsonUtils().getUtenti();  // Lista di tutti gli utenti
        for (LoggedUser utente : utenti) {   // Scorre la lista degli utenti loggati
            String decryptedPwd = decypher.decrypt(utente.getPassword());   // Pass in chiaro
            if (utente.getMail().equals(mail) && decryptedPwd.equals(password))
                return utente;   // Se le credenziali sono corrette, restituisci quell'utente
        }
        return null;    // Nessun utente è stato trovato e le credenziali non sono corrette
    }

    public List<Libreria> visualizzaLibrerieByUser(String mail) throws IOException {   // Cerca le librerie del singolo utente
        List<Libreria> userLibs = new JsonUtils().getLibrerie();    // Tutte le librerie di tutti gli utenti
        List<Libreria> librerieFound = new ArrayList<>();
        for (Libreria lib : userLibs) {
            if (lib.getProprietario().getMail().equals(mail)) {  // Se le mail corrispondono
                librerieFound.add(lib); // Aggiunge la libreria trovata alla lista da restituire
            }
        }
        return librerieFound;
    }

    public List<Recensione> visualizzaRecensioneByLibro(String titolo) throws IOException {
        List<Recensione> userReviews = new JsonUtils().getRecensioni();
        List<Recensione> recensioniFound = new ArrayList<>();
        for (Recensione r : userReviews) {
            if (r.getReferredLibro().getTitolo().equals(titolo)) {   // Se i titoli corrispondono
                recensioniFound.add(r);
            }
        }

        return recensioniFound;
    }

    public List<Consiglio> visualizzaConsigliByLibro(String titolo) throws IOException {
        List<Consiglio> userConsigli = new JsonUtils().getConsigli();
        List<Consiglio> consigliFound = new ArrayList<>();
        for (Consiglio c : userConsigli) {
            if (c.getReferredLibro().getTitolo().equals(titolo)) {   // Se il titolo corrisponde
                consigliFound.add(c);
            }
        }

        return consigliFound;
    }
}