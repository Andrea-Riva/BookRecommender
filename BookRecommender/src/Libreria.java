public class Libreria {
    // Params
    private LoggedUser proprietario; // Primary Key
    private String nome;
    private Libro[] collectionLibri; // External Keys

    // Constructor
    public Libreria(LoggedUser proprietario, String nome, Libro[] collectionLibri) {
        this.proprietario = proprietario;
        this.nome = nome;
        this.collectionLibri = collectionLibri;
    }

    // Getter e Setter
    public LoggedUser getProprietario() { return proprietario; }
    public void setProprietario(LoggedUser proprietario) { this.proprietario = proprietario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Libro[] getCollectionLibri() { return collectionLibri; }
    public void setCollectionLibri(Libro[] collectionLibri) { this.collectionLibri = collectionLibri; }

    // Methods
}
