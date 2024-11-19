import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
        boolean isLogged = false;   // Token per verificare se c'è un utente loggato
        User baseUser = new User(); // Utente non loggato
        Scanner s = new Scanner(System.in);
        // Debugging ricerca libri
        /*

        ArrayList<Libro> solution = baseUser.cercaLibroByDataAutore("1985-04-01");
        System.out.println("Libri trovati per la data 1985-04-01:");
        for(Libro l : solution) {
            System.out.println(l.toString());
            System.out.println("\n");
        }

        solution = baseUser.cercaLibroByAutore("J.K. Rowling");
        System.out.println("Tutti i libri trovati di J.K. Rowling:");
        for(Libro l : solution) {
            System.out.println(l.toString());
            System.out.println("\n");
        }

        System.out.println("Cerca libro per titolo ragione e sentimento: " + baseUser.cercaLibroByTitolo("Ragione e sentimento").toString());

         */

        // Register
        // baseUser.register();

        // Login
        System.out.print("Inserisci mail: ");
        String mail = s.nextLine();
        System.out.print("Inserisci password: ");
        String password = s.nextLine();
        LoggedUser loggedUser = baseUser.login(mail, password);
        System.out.println(loggedUser.toString());
        if (!Objects.isNull(loggedUser)) {  // Se l'utente è loggato
            isLogged = true;    // Il token di accesso viene messo a true
        }

//        Debugging registraLibreria()
//        ArrayList<String> libri = new ArrayList<>();
//        libri.add("Il ritratto di Dorian Gray"); libri.add("Il codice da Vinci");
//        Libreria debugLib = loggedUser.registraLibreria("Bestseller", libri);
//        System.out.println("\n\n" + debugLib.toString());

//        Debugging inserisciValutazioneLibro
//        System.out.println("\n\n\nRecensione: ");
//        loggedUser.inserisciValutazioneLibro("1984", "3", "2",
//                "1", "3", "4", "2");
    }
}