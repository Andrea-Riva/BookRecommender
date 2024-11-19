import java.io.*;
import java.util.ArrayList;
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
* @param autore
* @return una lista di oggetti di tipo Libro.
*/
    public ArrayList<Libro> cercaLibroByAutore(String autore) { // Cerca libri by autore
        ArrayList<Libro> libri = new ArrayList<>(); // to return
        String filePath = "src/data/libri.dati.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if(lineClear[1].equals(autore)) {
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
* @param autore e data
* @return una lista di oggetti di tipo Libro.
*/
    public Libro cercaLibroByDataAutore(String data, String autore) {
        String filePath = "src/data/libri.dati.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if(lineClear[6].equals(data) && lineClear[1].equals(autore)) {
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
/** Permette ad un utente di registrarsi, viene selezionato prima un UniqueId differente da ogni altro utente.
* Richiede l'inserimento di nome, cognome, codice fiscale, mail e password (tutti in formato String).
*/
    public void register() throws Exception {
        // Genera id univoco
        Integer uniqueId = 0;
        String filePath = "src/data/utenti.dati.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if(Integer.valueOf(lineClear[0]) > uniqueId) {
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
        if(pass.equals("")) {
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
*Metodo per effettuare il login
*@param String mail, String password
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
                if(lineClear[4].equals(mail.toLowerCase()) && passDecrypted.equals(password)) {
                    System.out.println("Loggato");
                    return new LoggedUser(lineClear[0], lineClear[1], lineClear[2],
                            lineClear[3], lineClear[4], lineClear[5]);  // Utente loggato se mail e pass sono corretti
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
}
