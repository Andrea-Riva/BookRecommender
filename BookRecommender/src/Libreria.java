import java.util.ArrayList;

public class Libreria {
    // Campi
    private LoggedUser proprietario;
    private String nome;
    private ArrayList<Libro> collectionLibri;

    // Metodi
    public Libreria(LoggedUser proprietario, String nome, ArrayList<Libro> collectionLibri) {
        this.proprietario = proprietario;
        this.nome = nome;
        this.collectionLibri = collectionLibri;
    }
}
