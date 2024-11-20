import java.util.ArrayList;

public class Libreria {
    private LoggedUser proprietario;
    private String nome;
    private ArrayList<Libro> libri;

    public Libreria(LoggedUser proprietario, String nome, ArrayList<Libro> libri) {
        this.proprietario = proprietario;
        this.nome = nome;
        this.libri = libri;
    }

    public LoggedUser getProprietario() { return this.proprietario; }
    public String getNome() { return this.nome; }
    public ArrayList<Libro> getLibri() { return this.libri; }

    public void setProprietario(LoggedUser proprietario) { this.proprietario = proprietario; }
    public void setNome(String nome) { this.nome = nome; }
    public void setLibri(ArrayList<Libro> libri) { this.libri = libri; }
}
