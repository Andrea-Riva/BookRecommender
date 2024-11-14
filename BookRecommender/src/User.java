import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;

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

    public ArrayList<Libro> cercaLibroByData(String data) {
        ArrayList<Libro> libri = new ArrayList<>(); // to return
        String filePath = "src/data/libri.dati.csv";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] lineClear = line.split("; ");
                if(lineClear[6].equals(data)) {
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
}