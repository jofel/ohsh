package hu.alkfejl.hermanNote.model;

import java.util.List;
import java.util.Random;

import hu.alkfejl.hermanNote.model.bean.Customer;

public class BookShopDAOTests {

    /**
     * A unit tesztek bel�p�si pontja. Ezt a nagy k�nyv szerint nem �gy kell
     * csin�lni, hanem felhaszn�lni egy unit test framework-ot, de az
     * egyszer�s�g v�gett most �gy csin�ljuk.
     *
     * @param args A parancssori argumentumok.
     */
    public static void main(String[] args) {
        TestAddCustomerWithSameName();
        TestAddAndListCustomers();
    }

    public static void TestAddCustomerWithSameName() {
    	HermanNoteDAO dao = new BookShopDAODBImpl();

        // az els� v�s�rl� hozz�ad�sa, elv�rjuk, hogy sikeres legyen
        Customer customer = new Customer();

        customer.setName("same");

        boolean succeeded = dao.addCustomer(customer);

        assertBoolean(succeeded, true, "Nem siker�lt hozz�adni az els� v�s�rl�t!");

        // azanos nev� v�s�rl� hozz�ad�sa, elv�rjuk, hogy !sikertelen! legyen
        Customer existingCustomer = new Customer();

        existingCustomer.setName("same");

        succeeded = dao.addCustomer(existingCustomer);

        assertBoolean(succeeded, false, "Siker�lt hozz�adni azonos nev� v�s�rl�t!");
    }

    public static void TestAddAndListCustomers() {
    	HermanNoteDAO dao = new BookShopDAODBImpl();

        // Tesztelj�k 10-szer az addCustomer-t �s ut�na a getCustomer-t
        // A neve mindig legyen TestCustomer + i
        // A kora legyen v�letlen, 0 �s 100 k�z�tti
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();

            customer.setName("TestCustomer_" + i);
            Random r = new Random();
            int randInt = r.nextInt(100);
            customer.setAge(randInt);

            assertBoolean(dao.addCustomer(customer), true, "Nem siker�lt �gyfelet hozz�adni!");
        }

        // A v�g�n k�rdezz�k le az adatb�zist, hogy milyen Customerek vannak
        // (valamint ellen�rizz�k SQLite shellben is)
        List<Customer> customers = dao.getCustomers();

        // lehet m�s is vett fel customer-eket, ez�rt nem teljesen korrekt ez a teszt
        assertBoolean(customers.size() >= 10, true, "Nem siker�lt lek�rdezni a felvett �gyfeleket!");

        System.out.println(customers.toString());
    }

    private static void assertBoolean(boolean actual, boolean expected, String message) {
        if (actual != expected) {
            System.out.println(String.format("TESZT HIBA: %1$s", message));
        }
    }
}
