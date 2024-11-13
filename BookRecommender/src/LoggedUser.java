import java.util.ArrayList;

public class LoggedUser extends User {
    // Campi
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String mail;
    private String password;

    // Metodi
    public LoggedUser(String nome, String cognome, String codiceFiscale, String mail, String password) {    // Costruttore
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.mail = mail;
        this.password = password;
    }

    private Libreria registraLibreria(String nomeLibreria, ArrayList<Libro> collectionLibri) {
        return new Libreria(
                new LoggedUser(this.nome, this.cognome, this.codiceFiscale, this.mail, this.password),  // Proprietario (utente che ha creato la libreria)
                nomeLibreria,
                collectionLibri );
    }
}
