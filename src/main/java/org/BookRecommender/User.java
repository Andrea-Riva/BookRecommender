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
     * Permette di cercare un libro nel dataset tramite il parametro del titolo.
     *
     * @param titolo Il titolo del libro.
     * @return Il libro trovato.
     * @throws IOException Se la deserializzazione non va a buon fine
     */
    public Libro searchLibroByTitolo(String titolo) throws Exception {
        List<Libro> dataset = new JsonUtils().getLibri();   
        for (Libro l : dataset) {    
            if (l.getTitolo().equalsIgnoreCase(titolo)) return l;  
        }
        return null;   
    }

    /**
     * Permette di cercare una serie di libri scritti dallo stesso autore.
     *
     * @param auth L'autore del libro.
     * @return Una collection di libri aventi lo stesso autore.
     * @throws IOException Se la deserializzazione non va a buon fine
     */
    public List<Libro> searchLibriByAuth(String auth) throws IOException {
        List<Libro> dataset = new JsonUtils().getLibri();
        List<Libro> libriFound = new ArrayList<Libro>();    
        for (Libro l : dataset) {
            if (l.getAutore().equalsIgnoreCase(auth))
                libriFound.add(l);   
        }
        return libriFound;
    }

    /**
     * Permette di cercare un libro scritto da un certo autore e pubblicato in una certa data.
     *
     * @param auth L'autore del libro.
     * @param data La data di pubblicazione del libro.
     * @return Il libro scritto da un certo autore e pubblicato in una certa data.
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
     * Scrive in formato JSON i dati dell'utente appena registrato.
     *
     * @param nome          Nome dell'utente
     * @param cognome       Cognome dell'utente
     * @param codiceFiscale Codice fiscale dell'utente
     * @param mail          Mail dell'utente
     * @param password      Password scelta dall'utente
     * @throws Exception
     */
    public void register(String nome, String cognome, String codiceFiscale, String mail, String password) throws Exception {
        JsonUtils utils = new JsonUtils(); 
        if (password.isBlank()) {   
            password = new SecurityUtils().genera();    
        }
        String encryptedPwd = new SecurityUtils().encrypt(password);    
        int uniqueId = utils.getUniqueId();   
        LoggedUser newUtente = new LoggedUser(uniqueId, nome, cognome, codiceFiscale, mail, encryptedPwd); 
        JsonNode rootUsers = utils.getUtentiAsJsonNode();   
        ArrayNode userArrayNode = (ArrayNode) rootUsers;    
        userArrayNode.add(utils.createUserNode(newUtente)); 
        utils.writeUtentiNodes(userArrayNode);
    }

    /**
     * Fa il login se mail e password sono corrette.
     *
     * @param mail     La mail dell'utente.
     * @param password La password dell'utente.
     * @return LoggedUser utente registrato.
     */
    public LoggedUser login(String mail, String password) throws Exception {
        SecurityUtils decypher = new SecurityUtils();   
        List<LoggedUser> utenti = new JsonUtils().getUtenti();  
        for (LoggedUser utente : utenti) {   
            String decryptedPwd = decypher.decrypt(utente.getPassword());  
            if (utente.getMail().equals(mail) && decryptedPwd.equals(password))
                return utente;   
        }
        return null;    
    }

    /**
     * Cerca tutte le librerie appartenenti ad un certo utente registrato.
     *
     * @param mail la mail dell'utente del quale si vogliono vedere le librerie.
     * @return una collection di tutte le librerie dell'utente cercato.
     * @throws IOException
     */
    public List<Libreria> visualizzaLibrerieByUser(String mail) throws IOException {  
        List<Libreria> userLibs = JsonUtils().getLibrerie();
        List<Libreria> librerieFound = new ArrayList<>();
        for (Libreria lib : userLibs) {
            if (lib.getProprietario().getMail().equals(mail)) {  
                librerieFound.add(lib); 
            }
        }
        return librerieFound;
    }

    /**
     * Cerca le recensioni da tutti gli utenti per un certo libro.
     *
     * @param titolo il titolo del libro che si vuole cercare.
     * @return una collection delle recensioni di tutti gli utenti relative al libro cercato.
     * @throws IOException
     */
    public List<Recensione> visualizzaRecensioneByLibro(String titolo) throws IOException {
        List<Recensione> userReviews = new JsonUtils().getRecensioni();
        List<Recensione> recensioniFound = new ArrayList<>();
        for (Recensione r : userReviews) {
            if (r.getReferredLibro().getTitolo().equals(titolo)) {   
                recensioniFound.add(r);
            }
        }
        return recensioniFound;
    }

    /**
     * Visualizza le recensioni del proprietario di tutti i libri di una certa libreria.
     *
     * @param nome il nome della libreria.
     * @return una collection delle recensioni di tutti i libri presenti pubblicate dal proprietario della libreria.
     */
    public List<Recensione> visualizzaRecensioniByLibreria(String nome) throws IOException {
        List<Libreria> userLibs = new JsonUtils().getLibrerie();   
        List<Recensione> recensioniLibreria = new ArrayList<>();
        Libreria lib = null;    
        for (Libreria l : userLibs) {
            if (l.getNome().equals(nome)) { 
                lib = l;
            }
        }
        for (Libro l : lib.getLibri()) {
            List<Recensione> recensioniLibro = visualizzaRecensioneByLibro(l.getTitolo());
            for (Recensione review : recensioniLibro) {  
                if (lib.getProprietario().getCodiceFiscale().equals(review.getPublisher().getCodiceFiscale())) { 
                    recensioniLibreria.add(review);
                }
            }
        }
        return recensioniLibreria;
    }

    /**
     * Cerca i consigli da tutti gli utenti per un certo libro.
     *
     * @param titolo il titolo del libro che si vuole cercare.
     * @return una collection di consigli da parte di tutti gli utenti relativi al libro cercato.
     * @throws IOException
     */
    public List<Consiglio> visualizzaConsigliByLibro(String titolo) throws IOException {
        List<Consiglio> userConsigli = new JsonUtils().getConsigli();
        List<Consiglio> consigliFound = new ArrayList<>();
        for (Consiglio c : userConsigli) {
            if (c.getReferredLibro().getTitolo().equals(titolo)) {   
                consigliFound.add(c);
            }
        }
        return consigliFound;
    }

    /**
    * Metodo per la visualizzazione dei consigli di una libreria.
    *
    * @param nome della libreria.
    * @return una lista di consigli.
    * @throws IOException in caso di errori nella ricerca/return.
    */
    public List<Consiglio> visualizzaConsigliByLibreria(String nome) throws IOException {
        List<Libreria> userLibs = new JsonUtils().getLibrerie();
        List<Consiglio> consigliLibreria = new ArrayList<>();
        Libreria lib = null;
        for(Libreria l : userLibs) {
            if(l.getNome().equals(nome)) {  
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
    
    /**
    * Metodo per cercare una libreria utente tramite nome della libreria.
    * 
    * @param il nome della libreria.
    * @return la libreria in caso di successo.
    * @throws IOException in caso di errori durante la ricerca / return dei risultati.
    */
    public Libreria searchLibByNome(String nomeLib) throws IOException {    
        List<Libreria> userLibs = new JsonUtils().getLibrerie();
        for(Libreria lib : userLibs) {
            if(lib.getNome().equalsIgnoreCase(nomeLib)) {   
                return lib;
            }
        }
        return null;    
    }
    
    /**
    * Metodo per la ricerca di recensioni tramite nome, cognome del pubblicatore
    * e titolo del libro.
    *
    * @param nome e cognome dell'utente che ha creato la libreria, titolo del libro.
    * @return la recensione (se esiste).
    * @throws IOException in caso di errori durante la ricerca / return dei risultati.
    */
    public Recensione searchRecensioneByUserTitolo(String nome, String cognome, String titoloLibro) throws IOException {   
        List<Recensione> allReviews = new JsonUtils().getRecensioni();  
        for(Recensione review : allReviews) {
            if(review.getPublisher().getNome().equals(nome)
            && review.getPublisher().getCognome().equals(cognome)
            && review.getReferredLibro().getTitolo().equalsIgnoreCase(titoloLibro)) {   
                return review;  
            }
        }
        return null;
    }
}
