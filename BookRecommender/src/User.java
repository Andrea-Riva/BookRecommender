import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    // Metodi
    public User() {
    }

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

    // Funzione di registrazione
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
        String codFiscale = in.nextLine();
        System.out.println("Mail: ");
        String mail = in.nextLine();
        // Password generata randomicamente:
        String randomPass = new PassSecurityUtils().genera();
        System.out.println("Crea una nuova Password\n[Password consigliata: ] " + randomPass +
                "\nPremi invio senza scrivere nulla per usare la password consigliata.");
        String pass = in.nextLine();
        if(pass.equals("")) {
            pass = randomPass;
        }
        String encryptedPass = new PassSecurityUtils().encrypt(pass);
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
        String toWrite = uniqueId.toString() + "; " + nome + "; " + cognome + "; " +
                codFiscale + "; " + mail + "; " + encryptedPass + "\n";

        // Passa la stringa a user.dati
        bw.write(toWrite);
        bw.close();
    }
}