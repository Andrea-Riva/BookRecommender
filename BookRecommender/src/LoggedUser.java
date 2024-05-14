public class LoggedUser extends User {
    // Params
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String email;
    private String username;
    private String password; // Da fare non in chiaro, crittata

    // Constructor
    public LoggedUser(String nome, String cognome, String codiceFiscale, String email, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getter e Setter
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getCodiceFiscale() { return codiceFiscale; }
    public void setCodiceFiscale(String codiceFiscale) { this.codiceFiscale = codiceFiscale; }
    public String getEmail() { return email; }
    public void setEmail(String email) {this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; } // Probabilmente da eliminare per la sicurezza della password
    public void setPassword(String password) { this.password = password; }  // Probabilmente da eliminare per la sicurezza della password

    // Methods
}
