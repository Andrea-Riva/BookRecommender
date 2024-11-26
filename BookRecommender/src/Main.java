import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        User baseUser = new User();
        // Debug searchLibroByTitolo
//        Libro libroFound = baseUser.searchLibroByTitolo("cazzo nero");
//        if(Objects.isNull(libroFound)) {
//            System.out.println("Libro non trovato");
//        } else {
//            System.out.println(libroFound.toString());
//        }

        // Debug searchLibroByAuth
//        List<Libro> libriFound = baseUser.searchLibroByAuth("J.K. Rowling");
//        for(Libro l : libriFound) System.out.println(l.toString());

        // Debug searchLibroByDataAuth
//        Libro libroFound = baseUser.searchLibroByDataAuth("J.K. Rowling", "1997-06-26");
//        System.out.println(libroFound.toString());
        // Debug addLibreria
//        System.out.print("Mail: ");
//        String mail = in.nextLine();
//        System.out.print("Password: ");
//        String password = in.nextLine();
//        LoggedUser log = baseUser.login(mail, password);
//        ArrayList<Libro> libri = new ArrayList<>();
//        libri.add(log.searchLibroByTitolo("I malavoglia")); libri.add(log.searchLibroByTitolo("Il giardino segreto"));
//        libri.add(log.searchLibroByTitolo("Dracula"));
//        Libreria lib = new Libreria(log, "Libri classici", libri);
//        log.addLibreria(lib);
        // Debug addRecensione
//        LoggedUser alexandro = baseUser.login("tavian@fake.it", "tavian");
//        Recensione added = alexandro.addRecensione("Il nome della rosa", 2, 2, 6, 0, 3);
//        System.out.println(added.toString());
    }
}