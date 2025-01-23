package org.BookRecommender;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* Classe per oggetti LoggedUser, viene estesa la classe User aggiungendo metodi
*
* Questa classe utilizza le annotazioni di Jackson per la deserializzazione JSON.
* - {@code @JsonCreator} viene utilizzato per specificare il costruttore da usare nella deserializzazione. <br>
* - {@code @JsonProperty} viene utilizzato per mappare i nomi dei campi JSON agli attributi della classe.
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

    /**
    * Il metodo permette l'aggiunta di Librerie
    * La scrittura avviene sul file {@code src/main/java/org/BookRecommender/data/librerie.json} on override
    * L'oggetto creato viene convertito in Json e salvato sul file
    */
    public void addLibreria(Libreria libreria) throws IOException {
        String filePath = "src/main/java/org/BookRecommender/data/librerie.json"; 
        List<Libreria> userLibs = new JsonUtils().getLibrerie();    
        userLibs.add(libreria); 
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), userLibs);   // Override del file e scrittura libs
    }

    /**
    * Aggiungere una recensione con parametri stile, contenuto, gradevolezza, originalità e edizione
    * L'oggetto Libro su cui effettuare la recesìsione deve essere presente in una libreria dell'utente
    * Il controllo avviene con {@code  if(!(new JsonUtils().isPresente(this, titolo)))}
    *
    * La recensione viene scritta nel file {@code src/data/recensioni.json} con override dello stesso
    *
    * @param titolo del libro (String) e i 5 parametri di valutazione (int)
    */
    public void addRecensione(String titolo, int stile, int contenuto,
                              int gradevolezza, int originalità, int edizione) throws Exception {
        if(!(new JsonUtils().isPresente(this, titolo))) {   // Se il libro non è presente in nessuna libreria
            throw new Exception("Il libro " + titolo + " non risulta presente in nessuna tua libreria");
        }
        String filePath = "src/data/recensioni.json";
        ObjectMapper mapper = new ObjectMapper();
        Libro libroFound = new User().searchLibroByTitolo(titolo);
        Recensione recensioneToAdd = new Recensione(this, new User().searchLibroByTitolo(titolo), 1, 1, 1, 1, 1);
        List<Recensione> allRecensioni = new JsonUtils().getRecensioni();   
        allRecensioni.add(recensioneToAdd);
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), allRecensioni);
    }

    /**
    * Il metodo permette di visualizzare la collection di oggetti Recensione dell'utente che esegue i ìl metodo
    * Vengono caricate e filtrate tutte le recensioni utilizzando l'id utente
    */

    public List<Recensione> visualizzaLeMieRecensioni() throws IOException {  
        List<Recensione> usersReview = new JsonUtils().getRecensioni(); 
        List<Recensione> myRecensioni = new ArrayList<>();
        for (Recensione review : usersReview) {
            if (review.getPublisher().getId() == this.id) {  
                myRecensioni.add(review);
            }
        }

        return myRecensioni;
    }

    /**
    * Metodo per aggiungere un consiglio
    * L'oggetto Libro su cui eseguire il metodo deve essere presente almeno in una libreria dell'utente
    *
    * La scrittura avviene sul file {@code src/main/java/org/BookRecommender/data/consigli.json} con override
    */
    public void addConsiglio(String titolo, List<Libro> consigli) throws Exception {
        if(!(new JsonUtils().isPresente(this, titolo))) {
            throw new Exception("Il libro " + titolo + " non risulta presente in nessuna tua libreria");
        }
        String filePath = "src/main/java/org/BookRecommender/data/consigli.json";
        ObjectMapper mapper = new ObjectMapper();
        Consiglio newConsiglio = new Consiglio(this, new User().searchLibroByTitolo(titolo), consigli); 
        List<Consiglio> allConsigli = new JsonUtils().getConsigli();
        allConsigli.add(newConsiglio);  
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), allConsigli);
    }
}
