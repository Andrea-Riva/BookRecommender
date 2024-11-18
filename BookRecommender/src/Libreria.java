import java.util.ArrayList;
/**
* La classe permette di creare oggetti di tipo Libreria. 
* Ogni libreria è contraddistinta dai campi proprietario (loggedUser, cioè un oggetto che rappresenta un'utente registrato),
* un nome (String) arbitrario scelto alla creazione ed una collezione di libri (ArrayList<Libro> collectionLibri).
* Viene inoltre riscritto il metodo toString.
*/
public class Libreria {
    private LoggedUser proprietario;
    private String nome;
    private ArrayList<Libro> collectionLibri;

    public Libreria(LoggedUser proprietario, String nome, ArrayList<Libro> collectionLibri) {
        this.proprietario = proprietario;
        this.nome = nome;
        this.collectionLibri = collectionLibri;
    }

    public LoggedUser getProprietario() {
        return this.proprietario;
    }
    public void setProprietario(LoggedUser proprietario) {
        this.proprietario = proprietario;
    }

    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Libro> getCollectionLibri() {
        return this.collectionLibri;
    }
    public void setCollectionLibri(ArrayList<Libro> collectionLibri) {
        this.collectionLibri = collectionLibri;
    }
/**
* Metodo toString riscritto, viene inizializzata una stringa vuota libriToString.
* Viene scandita la lista collectionLibri e per ogni elemento viene concatenato alla stringa libriToString
* il metodo toString di ogni oggetto della lista (toString di oggetti di tipo Libro).
* Il metodo ritorna una stringa con formato: proprietario, nome libreria (che esegue il metodo), libri
* presenti all'interno della libreria.
*/
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
