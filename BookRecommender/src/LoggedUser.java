/**
 * Classe per l'utente loggato al sistema tramite credenziali
 */
public class LoggedUser extends User {
    private int id;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String mail;

    public LoggedUser(int id, String nome, String cognome, String codiceFiscale, String mail) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.mail = mail;
    }

    public int getId() { return this.id; }
    public String getNome() { return this.nome; }
    public String getCognome() { return this.cognome; }
    public String getCodiceFiscale() { return this.codiceFiscale; }
    public String getMail() { return this.mail; }

    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }
    public void setMail(String mail) { this.mail = mail; }
}
