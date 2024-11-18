import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class LoggedUser extends User {
    // Campi
    private String id;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String mail;
    private String password;

    // Metodi
    public LoggedUser(String id, String nome, String cognome, String codiceFiscale, String mail, String password) {    // Costruttore
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.mail = mail;
        this.password = password;
    }

    // Getter e Setter per id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter e Setter per nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter per cognome
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    // Getter e Setter per codiceFiscale
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    // Getter e Setter per mail
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Libreria registraLibreria(String nomeLibreria, ArrayList<String> collectionTitoliLibri) throws IOException {
        ArrayList<Libro> constructCollectionLibri = new ArrayList<>();
        ArrayList<String> existingLibri = new ArrayList<>();
        // Estrapolazione nomi dei libri dalla collection
        for(String titoloLibro : collectionTitoliLibri) {
            Libro libroTemp = new User().cercaLibroByTitolo(titoloLibro);
            if(Objects.isNull(libroTemp)) { // Se il libro non è stato trovato
                System.out.println("Il libro " + titoloLibro + " non è stato trovato e non sarà aggiunto alla libreria");   //non viene aggiunto alla libreria e viene comunicato l'errore
            } else {    // Se il libro è stato trovato
                existingLibri.add(titoloLibro); // Aggiunge il titolo ai libri esistenti
                constructCollectionLibri.add(new User().cercaLibroByTitolo(titoloLibro));   // Aggiunge il libro alla collection
            }
        }
        // Write sul file librerie.dati
        String filePath = "src/data/librerie.dati.csv";
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
        String titoloLibri = "";
        for(String titolo : existingLibri) {    // Memorizza in una stringa tutti i nomi dei libri esistenti inseriti dall'utente
            System.out.println(titolo);
            titoloLibri += titolo;
            titoloLibri += "; ";
        }
        String toWrite = this.getId() + "; " + nomeLibreria + "; " + constructCollectionLibri.size() + "; " + titoloLibri + "\n";

        // Passa la stringa a librerie.dati
        bw.write(toWrite);
        bw.close();

        // Costruzione Libreria
        return new Libreria(this, nomeLibreria, constructCollectionLibri);
    }

    // Metodo toString
    @Override
    public String toString() {
        return "ID: " + this.id + "\nNome e cognome: " + this.nome + " " + this.cognome +
                "\nMail: " + this.mail + "\nCodice Fiscale: " + this.codiceFiscale;  // Non eseguiamo il toString della password per ovvi motivi di sicurezza
    }
}
