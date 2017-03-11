package hu.alkfejl.hermanNote;

import java.util.List;

import hu.alkfejl.hermanNote.controller.BookShopController;
import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.view.Labels;

/**
 * Az oszt�ly az alkalmaz�s bel�p�si pontja, az alkalmaz�s ind�t�s��rt felel.
 */
public class Main {

    /**
     * A parancssorban fogadott felhaszn�l�n�v.
     * <p>
     * Az egyszer�s�g v�gett itt t�roljuk, lehetne szebben is, mert �gy a view
     * r�teg hivatkozik a {@link Main}-re, a view-t nem lehet kiszedni az appb�l
     * �s m�shol felhaszn�lni.
     * Megold�s: k�l�n oszt�ly a feldolgozott argumentumoknak, �tadni a view-nak.
     * </p>
     */
    public static String username;

    /**
     * Az alkalmaz�s bel�p�si pontja.
     *
     * @param args A parancssori argumentumok list�ja.
     */
    public static void main(String[] args) {
        processCommandLine(args);
        startApp();
    }

    /**
     * Feldolgozza a parancssori argumentumokat.
     *
     * @param args
     */
    private static void processCommandLine(String[] args) {
        // "buta" feldolgoz�s: ha van param�ter, akkor az els� a usern�v
        if (args.length > 0) {
            username = args[0];
        }
    }

    /**
     * Ind�tja az alkalmaz�st.
     */
    private static void startApp() {
        BookShopController controller = new BookShopController();
       
        //p�ldaadatok felv�tel�t mell�zz�k
        //ensureSampleDataCreated(controller);

        controller.startDesktop();
    }

    /**
     * Biztos�tja a p�ldaadatok l�trej�tt�t, amennyiben sz�ks�ges.
     *
     * @param controller Az alkalmaz�s controllere.
     */
    private static void ensureSampleDataCreated(BookShopController controller) {
        // Elk�rj�k a m�r elt�rolt k�nyveket.
        List<Book> storedBooks = controller.getBooks();

        // Ellen�rizz�k, hogy van-e elt�rolt k�nyv.
        boolean isAnyBookStored = storedBooks.size() != 0;

        // Amennyiben nincs elt�rolt k�nyv, �gy l�trehozzuk a k�nyv
        // p�ldaadatokat.
        if (!isAnyBookStored) {
            prepareBookSampleData(controller);
        }

        // Elk�rj�k a m�r elt�rolt v�s�rl�kat.
        List<Customer> storedCustomers = controller.getCustomers();

        // Ellen�rizz�k, hogy van-e elt�rolt v�s�rl�.
        boolean isAnyCustomerStored = storedCustomers.size() != 0;

        // Amennyiben nincs elt�rolt v�s�rl�, �gy l�trehozzuk a v�s�rl�
        // p�ldaadatokat.
        if (!isAnyCustomerStored) {
            prepareCustomerSampleData(controller);
        }
    }

    /**
     * K�nyv p�ldaadatokat vesz fel az alkalmaz�sba, amennyiben nincsenek m�g
     * elt�rolt k�nyvek.
     * <p>
     * Fontos, a p�lda adatok felv�tele is az �zleti szab�lyok alkalmaz�s��rt
     * felel�s controlleren kereszt�l t�rt�nik!
     * </p>
     *
     * @param controller Az alkalmaz�s controllere.
     */
    private static void prepareBookSampleData(BookShopController controller) {
        for (int i = 0; i < 10; i++) {
            Book book = new Book();

            // Ez itt nem UI c�mke, hanem adat, mintha a user �rta volna be,
            // ez�rt nem tessz�k Labels-be.
            book.setTitle("C�m " + i);
            book.setAuthor("Szerz�" + i);
            book.setCategory(Labels.drama);
            book.setPiece(i);
            book.setPrice(i);
            book.setYear(i);

            controller.addBook(book);
        }
    }

    /**
     * V�s�rl� p�ldaadatokat vesz fel az alkalmaz�sba, amennyiben nincsenek m�g
     * elt�rolt v�s�rl�k.
     * <p>
     * Fontos, a p�lda adatok felv�tele is az �zleti szab�lyok alkalmaz�s��rt
     * felel�s controlleren kereszt�l t�rt�nik!
     * </p>
     *
     * @param controller Az alkalmaz�s controllere.
     */
    private static void prepareCustomerSampleData(BookShopController controller) {
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();

            // Ez itt nem UI c�mke, hanem adat, mintha a user �rta volna be,
            // ez�rt nem tessz�k Labels-be.
            customer.setName("N�v " + i);
            customer.setAge(i);
            customer.setFemale(true);
            customer.setGrantee(true);
            customer.setQualification(Labels.qualifications[0]);

            controller.addCustomer(customer);
        }
    }
}
