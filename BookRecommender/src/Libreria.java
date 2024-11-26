import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Libreria {
    private LoggedUser proprietario;
    private String nome;
    private ArrayList<Libro> libri;

    @JsonCreator
    public Libreria(
            @JsonProperty("proprietario") LoggedUser proprietario,
            @JsonProperty("nome") String nome,
            @JsonProperty("libri") ArrayList<Libro> libri) {
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

    @Override
    public String toString() {
        String libriPresentiToString = "";
        for(Libro libro : this.libri) {
            libriPresentiToString += libro.getTitolo() + "\n";
        }
        return "Proprietario: " + this.proprietario.getNome() + " " + this.proprietario.getCognome() +
                "\nNome libreria: " + this.nome + "\nLibri presenti:\n" + libriPresentiToString;
    }
}