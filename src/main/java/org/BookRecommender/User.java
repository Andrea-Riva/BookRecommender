package org.BookRecommender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
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
            if (l.getTitolo().equalsIgnoreCase(titolo)) return l;  // Se i titoli corrispondono
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
    public List<Libro> searchLibriByAuth(String auth) throws IOException {
        List<Libro> dataset = new JsonUtils().getLibri();
        List<Libro> libriFound = new ArrayList<Libro>();    // To return
        for (Libro l : dataset) {
            if (l.getAutore().equalsIgnoreCase(auth))
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
            if (l.getAutore().equalsIgnoreCase(auth) && l.getData().equalsIgnoreCase(data)) return l;
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
        if (password.isBlank()) {    // Se il parametro non presenta caratteri
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

    /**
     * Cerca tutte le librerie appartenenti ad un certo utente registrato
     *
     * @param mail la mail dell'utente del quale si vogliono vedere le librerie
     * @return una collection di tutte le librerie dell'utente cercato
     * @throws IOException
     */
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

    /**
     * Cerca le recensioni da tutti gli utenti per un certo libro
     *
     * @param titolo il titolo del libro che si vuole cercare
     * @return una collection delle recensioni di tutti gli utenti relative al libro cercato
     * @throws IOException
     */
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

    /**
     * Visualizza le recensioni del proprietario di tutti i libri di una certa libreria
     *
     * @param nome il nome della libreria
     * @return una collection delle recensioni di tutti i libri presenti pubblicate dal proprietario della libreria
     */
    public List<Recensione> visualizzaRecensioniByLibreria(String nome) throws IOException {
        List<Libreria> userLibs = new JsonUtils().getLibrerie();    // Tutte le librerie di tutti gli utenti
        List<Recensione> recensioniLibreria = new ArrayList<>();
        Libreria lib = null;    // Libreria trovata
        // Trova la libreria in questione
        for (Libreria l : userLibs) {
            if (l.getNome().equals(nome)) {  // Se il nome della libreria corrisponde
                lib = l;
            }
        }
        // Trova per tutte le recensioni di tutti i libri presenti nella libreria quelle pubblicate dal proprietario
        for (Libro l : lib.getLibri()) {
            List<Recensione> recensioniLibro = visualizzaRecensioneByLibro(l.getTitolo());
            for (Recensione review : recensioniLibro) {  // Per ogni recensione presente nella collection
                if (lib.getProprietario().getCodiceFiscale().equals(review.getPublisher().getCodiceFiscale())) { // Verifica se chi ha pubblicato la recensione è anche il proprietario della libreria
                    recensioniLibreria.add(review);
                }
            }
        }
        return recensioniLibreria;
    }

    /**
     * Cerca i consigli da tutti gli utenti per un certo libro
     *
     * @param titolo il titolo del libro che si vuole cercare
     * @return una collection di consigli da parte di tutti gli utenti relativi al libro cercato
     * @throws IOException
     */
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

    public List<Consiglio> visualizzaConsigliByLibreria(String nome) throws IOException {
        List<Libreria> userLibs = new JsonUtils().getLibrerie();
        List<Consiglio> consigliLibreria = new ArrayList<>();
        Libreria lib = null;
        // Trova la libreria
        for(Libreria l : userLibs) {
            if(l.getNome().equals(nome)) {  // Se il nome della libreria corrisponde
                lib = l;
            }
        }
        for(Libro libro : lib.getLibri()) {
            List<Consiglio> consigliLibro = visualizzaConsigliByLibro(libro.getTitolo());
            for(Consiglio consiglio : consigliLibro) {
                if(consiglio.getPublisher().getCodiceFiscale().equals(lib.getProprietario().getCodiceFiscale())) {
                    consigliLibreria.add(consiglio);
                }
            }
        }
        return consigliLibreria;
    }

    public Libreria searchLibByNome(String nomeLib) throws IOException {    // Usata in AllUserLibsController btn setAction
        List<Libreria> userLibs = new JsonUtils().getLibrerie();
        for(Libreria lib : userLibs) {
            if(lib.getNome().equalsIgnoreCase(nomeLib)) {   // Lib trovata
                return lib;
            }
        }
        return null;    // Lib non trovata
    }

    public Recensione searchRecensioneByUserTitolo(String nome, String cognome, String titoloLibro) throws IOException {   // Usata in ReviewsFromHomeController user btn
        List<Recensione> allReviews = new JsonUtils().getRecensioni();  // Get recensioni
        for(Recensione review : allReviews) {
            if(review.getPublisher().getNome().equals(nome)
            && review.getPublisher().getCognome().equals(cognome)
            && review.getReferredLibro().getTitolo().equalsIgnoreCase(titoloLibro)) {   // Se tutti i parametri corrispondono
                return review;  // Restituisce la recensione
            }
        }
        return null;
    }
}
