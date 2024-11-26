import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per l'utente loggato al sistema tramite credenziali
 */
public class LoggedUser extends User {
    private int id;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String mail;
    private String password;

    @JsonCreator
    public LoggedUser(
            @JsonProperty("id") int id,
            @JsonProperty("nome") String nome,
            @JsonProperty("cognome") String cognome,
            @JsonProperty("codiceFiscale") String codiceFiscale,
            @JsonProperty("mail") String mail,
            @JsonProperty("password") String password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.mail = mail;
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public String getCodiceFiscale() {
        return this.codiceFiscale;
    }

    public String getMail() {
        return this.mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\nNome, cognome e cod fiscale: " + nome + " " + cognome +
                " " + codiceFiscale + "\nMail: " + mail + "\nPassword crittata: " + password;
    }

    public void addLibreria(Libreria libreria) throws IOException {
        String filePath = "src/data/librerie.json"; //Path del file sul quale fare override
        // getLibrerie, aggiunge libreria, override del file
        List<Libreria> userLibs = new JsonUtils().getLibrerie();    // Lista di tutte le librerie presenti nel file
        userLibs.add(libreria); // Aggiunge la libreria mancante
        // Aggiorna il file JSON
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), userLibs);   // Override del file e scrittura libs
    }

    public void addRecensione(String titolo, int stile, int contenuto,
                              int gradevolezza, int originalità, int edizione) throws Exception {
        // Verifica se il libro è presente in una libreria dell'utente
        if(!(new JsonUtils().isPresente(this, titolo))) {   // Se il libro non è presente in nessuna libreria
            throw new Exception("Il libro " + titolo + " non risulta presente in nessuna tua libreria");
        }
        String filePath = "src/data/recensioni.json";
        ObjectMapper mapper = new ObjectMapper();
        // Costruzione della recensione
        Libro libroFound = new User().searchLibroByTitolo(titolo);
        Recensione recensioneToAdd = new Recensione(this, new User().searchLibroByTitolo(titolo), 1, 1, 1, 1, 1);
        List<Recensione> allRecensioni = new JsonUtils().getRecensioni();   // Tutte le vecchie recensioni
        allRecensioni.add(recensioneToAdd);
        // Override sul vecchio file
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), allRecensioni);
    }

    public List<Recensione> visualizzaLeMieRecensioni() throws IOException {    // Visualizza le recensioni pubblicate dall'utente loggato
        List<Recensione> usersReview = new JsonUtils().getRecensioni(); // Tutte le recensioni di tutti gli utenti
        List<Recensione> myRecensioni = new ArrayList<>();
        for (Recensione review : usersReview) {
            if (review.getPublisher().getId() == this.id) {  // Se gli ID combaciano
                myRecensioni.add(review);
            }
        }

        return myRecensioni;
    }

    public void addConsiglio(String titolo, List<Libro> consigli) throws Exception {
        // Verifica se il libro è presente in almeno una libreria dell'utente
        if(!(new JsonUtils().isPresente(this, titolo))) {
            throw new Exception("Il libro " + titolo + " non risulta presente in nessuna tua libreria");
        }
        String filePath = "src/data/consigli.json";
        ObjectMapper mapper = new ObjectMapper();
        // Costruzione del consiglio
        Consiglio newConsiglio = new Consiglio(this, new User().searchLibroByTitolo(titolo), consigli); // Tutti i consigli da tutti gli utenti
        List<Consiglio> allConsigli = new JsonUtils().getConsigli();
        allConsigli.add(newConsiglio);  // Aggiungi il nuovo consiglio a tutti i consigli
        // Override sul vecchio file
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), allConsigli);
    }
}