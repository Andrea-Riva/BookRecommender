import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Permette agli utenti di registrarsi ed effettuare ricerche di Libri mediante vari metodi.
 * La classe utilizza file csv esterni (libri.dati.csv, cioè il dataset dei libri),
 * (utenti.dati.csv, per salvare le informazioni relative ad un account registrato con la funzione register).
 */
public class User {
    public User() {
    }

    /**
     * Permette la ricerca di un oggetto di tipo Libro tramite titolo.
     *
     * @param titolo del libro da cercare
     * @return l'oggetto di tipo Libro cercato (se incluso nel DataSet)
     */
    public Libro cercaLibroByTitolo(String titolo) {    // Cerca libro by titolo
        String filePath = "src/data/libri.dati.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if (lineClear[0].equals(titolo)) {
                    return new Libro(lineClear[0], lineClear[1], lineClear[2], lineClear[3], lineClear[4],
                            lineClear[5], lineClear[6]);
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Permette di cercare tutti i Libri scritti da un autore.
     *
     * @param autore String che rappresenta l'autore i quali libri si vogliono cercare
     * @return una lista di oggetti di tipo Libro.
     */
    public ArrayList<Libro> cercaLibroByAutore(String autore) { // Cerca libri by autore
        ArrayList<Libro> libri = new ArrayList<>(); // to return
        String filePath = "src/data/libri.dati.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if (lineClear[1].equals(autore)) {
                    Libro temp = new Libro(lineClear[0], lineClear[1], lineClear[2], lineClear[3], lineClear[4],
                            lineClear[5], lineClear[6]);
                    libri.add(temp);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }

    /**
     * Permette la ricerca di libri in base all'autore e alla data di pubblicazione.
     *
     * @param autore String che rappresenta l'autore del libro
     * @param data   String che rappresenta la specifica data di uscita del libro
     * @return una lista di oggetti di tipo Libro.
     */
    public Libro cercaLibroByDataAutore(String data, String autore) {
        String filePath = "src/data/libri.dati.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if (lineClear[6].equals(data) && lineClear[1].equals(autore)) {
                    return new Libro(lineClear[0], lineClear[1], lineClear[2], lineClear[3], lineClear[4],
                            lineClear[5], lineClear[6]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Permette ad un utente di registrarsi, viene selezionato prima un UniqueId differente da ogni altro utente.
     * Richiede l'inserimento di nome, cognome, codice fiscale, mail e password (tutti in formato String).
     */
    public void register() throws Exception {
        // Genera id univoco
        Integer uniqueId = 0;
        String filePath = "src/data/utenti.dati.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if (Integer.valueOf(lineClear[0]) > uniqueId) {
                    uniqueId = Integer.valueOf(lineClear[0]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        uniqueId++; // Id univoco
        // Costruzione campi registrazione
        Scanner in = new Scanner(System.in);
        System.out.println("Nome: ");
        String nome = in.nextLine();
        System.out.println("Cognome: ");
        String cognome = in.nextLine();
        System.out.println("Codice fiscale: ");
        String codFiscale = in.nextLine().toUpperCase();
        System.out.println("Mail: ");
        String mail = in.nextLine().toLowerCase();
/** Prima di inserire una password decisa dall'utente viene fornita una password casuale creata dal programma.
 *L'utente può decidere di usare la password prodotta automaticamente premendo invio senza inserire caratteri.
 */
        String randomPass = new PassSecurityUtils().genera();
        System.out.println("Crea una nuova Password\n[Password consigliata: ] " + randomPass +
                "\nPremi invio senza scrivere nulla per usare la password consigliata.");
        String pass = in.nextLine();
        if (pass.equals("")) {
            pass = randomPass;
        }
/**
 * La password viene crittata prima della deposizione su file esterno (String encryptedPass = newPassSecurityUtils().encrypt(pass);)
 * Scrittura della riga di testo contente i dati dell'utente su file esterno user.dati
 */
        String encryptedPass = new PassSecurityUtils().encrypt(pass);
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
        String toWrite = uniqueId.toString() + "; " + nome + "; " + cognome + "; " +
                codFiscale + "; " + mail + "; " + encryptedPass + "\n";

        bw.write(toWrite);
        bw.close();
    }

    /**
     * Metodo per effettuare il login
     *
     * @param mail     String che rappresenta la mail dell'utente
     * @param password String che rappresenta la password in chiaro dell'utente
     */
    public LoggedUser login(String mail, String password) {
        // Init del BufferedReader per leggere i dati dal file degli utenti registrati
        String filePath = "src/data/utenti.dati.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
/**
 * Decrittazione della password e verifica correttezza coppia mail/password.
 */
                String passDecrypted = new PassSecurityUtils().decrypt(lineClear[5]);
                if (lineClear[4].equals(mail.toLowerCase()) && passDecrypted.equals(password)) {
                    System.out.println("Loggato");
                    return new LoggedUser(lineClear[0], lineClear[1], lineClear[2],
                            lineClear[3], lineClear[4]);  // Utente loggato se mail e pass sono corretti
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Permette di visualizzare tutti i consigli di tutti gli utenti relativi ad un libro il cui titolo viene preso come parametro
     *
     * @param titoloLibro String che rappresenta il titolo del libro del quale vogliamo visualizzare i consigli
     * @return String che contiene tutte le informazioni sugli utenti consigliatori e su tutti i libro consigliati da essi
     * @throws Exception Eccezione sollevata se il titolo del libro non è presente nel dataset
     */
    public String visualizzaConsigli(String titoloLibro) throws Exception {
        if (Objects.isNull(new User().cercaLibroByTitolo(titoloLibro))) {   // Se il titolo non è presente nel dataset
            throw new Exception("Il libro non è presente nel dataset");
        }
        String filePath = "src/data/ConsigliLibri.dati.csv";
        String line;
        String relatedConsigli = "";    // Stringa da restituire
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {    // Legge il file dei consigli
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if (lineClear[1].equals(titoloLibro)) { // Se il libro principale corrisponde con il parametro
                    LoggedUser publisher = new GetLoggedUser().getLoggedUserFromId(lineClear[0]);     // Va a cercare il pubblicatore del consiglio
                    String libriConsigliati = "";
                    // Crea la stringa per i consigli legati al libro principale
                    for (int i = 2; i < lineClear.length; i++) { // I suggerimenti iniziano da lineClear[2] perchè [0] è uId e [1] è il libro principale
                        libriConsigliati += lineClear[i] + "\n";    // Concatena il libro consigliato in posizione i alla stringa
                    }
                    relatedConsigli += "Suggerimenti dell'utente " + publisher.getNome() + " " + publisher.getCognome() + ": \n"
                            + libriConsigliati + "\n";  // Formatta la stringa da restituire
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return relatedConsigli;
    }

    /**
     *
     * @param titoloLibro Il titolo del libro del quale si vogliono vedere le valutazioni
     * @return Una String che contiene tutte le valutazioni di tutti gli utenti
     * @throws Exception Eccezione sollevata se il libro non è presente nel dataset
     */
    public String visualizzaValutazioni(String titoloLibro) throws Exception {
        if (Objects.isNull(new User().cercaLibroByTitolo(titoloLibro))) {   // Se il titolo non è presente nel dataset
            throw new Exception("Il libro non è presente nel dataset");
        }
        String filePath = "src/data/ValutazioniLibri.dati.csv";
        String line;
        String review = ""; // Stringa da restituire
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {    // Legge il file dei consigli
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if (lineClear[1].equals(titoloLibro)) {  // Se la recensione è inerente al libro
                    LoggedUser reviewer = new GetLoggedUser().getLoggedUserFromId(lineClear[0]);    // L'utente che ha emesso la recensione
                    review += "Recensione di " + reviewer.getNome() + " " + reviewer.getCognome()
                            + ": \nStile: " + lineClear[2] + ", Contenuto: " + lineClear[3] + ", Gradevolezza: " + lineClear[4]
                            + ", Originalità: " + lineClear[5] + ", Edizione: " + lineClear[6] + "\nValutazione complessiva: " + lineClear[7]
                            + "\nNote aggiuntive: " + lineClear[8] + "\n";
                }
            }
        }
        return review;
    }
}