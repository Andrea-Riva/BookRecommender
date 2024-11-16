import java.util.ArrayList;

public class Libreria {
    // Campi
    private LoggedUser proprietario;
    private String nome;
    private ArrayList<Libro> collectionLibri;

    // Costruttore
    public Libreria(LoggedUser proprietario, String nome, ArrayList<Libro> collectionLibri) {
        this.proprietario = proprietario;
        this.nome = nome;
        this.collectionLibri = collectionLibri;
    }

    // Getter e Setter per proprietario
    public LoggedUser getProprietario() {
        return this.proprietario;
    }
    public void setProprietario(LoggedUser proprietario) {
        this.proprietario = proprietario;
    }

    // Getter e Setter per nome
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter per collectionLibri
    public ArrayList<Libro> getCollectionLibri() {
        return this.collectionLibri;
    }
    public void setCollectionLibri(ArrayList<Libro> collectionLibri) {
        this.collectionLibri = collectionLibri;
    }

    // Metodo toString
    @Override
    public String toString() {
        String libriToString = "";   // Tutti i libri toString()
        for(Libro l : collectionLibri) {
            libriToString += "\n" + l.toString();
        }

        return "Proprietario: " + this.proprietario.toString() + "\nNome libreria: " + this.nome +
                "\nLibri presenti: " + libriToString;
    }
}
