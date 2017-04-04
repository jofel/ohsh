package hu.alkfejl.hermanNote.model;

import java.util.ArrayList;
import java.util.List;

import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.Student;

/**
 * Ez az oszt�ly az adatel�r�st szolg�lja. Mivel nincs m�g�tte t�nyleges
 * perzisztens t�rol� (pl. adatb�zis), ez�rt csak mem�ri�ba t�rolja az
 * adatokat.
 */
public class BookShopDAOMemImpl implements BookShopDAO {

    private static int id = 1;

    List<Customer> customers = new ArrayList<Customer>();
    List<Book> books = new ArrayList<Book>();
    List<Purchase> purchases = new ArrayList<Purchase>();

    /**
     * Hozz�ad egy {@link Customer}-t az adatt�rhoz.
     *
     * @param customer A t�roland� {@link Customer}.
     * @return Igaz, ha sikeresen t�rolva, hamis, egy�bk�nt.
     */
    @Override
    public boolean addCustomer(Customer customer) {
        if (!checkCustomerNameUnique(customer)) {
            return false;
        }

        // l�ptetj�k az id-t az �j customer-hez (ezt az adatt�r kell biztos�tja)
        id++;

        // az adatt�rol� id-t oszt az objektumnak
        customer.setId(id);

        // elt�roljuk
        boolean isStored = customers.add(customer);

        System.out.println("�gyfelek:" + customers.toString());

        return isStored;
    }

    /**
     * Ellen�rzi a {@link #customers} integrit�s�t, a {@link Customer#getName()}
     * egyedi kell legyen.
     *
     * @param newCustomer Az �jonnan felveend� {@link Customer}.
     * @return True, ha a n�v egyedi, false egy�bk�nt.
     */
    private boolean checkCustomerNameUnique(Customer newCustomer) {
        boolean rvIsValid = true;

        for (Customer customer : customers) {
            if (customer.getName().equals(newCustomer.getName())) {
                rvIsValid = false;
                break;
            }
        }

        return rvIsValid;
    }

    /**
     * Visszaadja a t�rolt {@link Customer} p�ld�nyokat.
     *
     * @return A t�rolt {@link Customer}-ek list�ja.
     */
    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    /**
     * Hozz�ad egy {@link Book}-ot az adatt�rhoz.
     *
     * @param book A t�roland� {@link Book}.
     * @return Igaz, ha sikeresen t�rolva, hamis, egy�bk�nt.
     */
    public boolean addBook(Book book) {
        if (!checkBookTitleUnique(book)) {
            return false;
        }

        // l�ptetj�k az id-t az �j book-hoz (ezt az adatt�r kell biztos�tja)
        id++;

        // az adatt�rol� id-t oszt az objektumnak
        book.setId(id);

        boolean isStored = books.add(book);

        System.out.println("K�nyvek:" + books.toString());

        return isStored;
    }

    /**
     * Ellen�rzi a {@link #books} integrit�s�t, a {@link Book#getTitle()}
     * egyedi kell legyen.
     *
     * @param newBook Az �jonnan felveend� {@link Book}.
     * @return True, ha a c�m egyedi, false egy�bk�nt.
     */
    private boolean checkBookTitleUnique(Book newBook){
        boolean rvIsValid = true;

        for (Book book : books) {
            if (book.getTitle().equals(newBook.getTitle())) {
                rvIsValid = false;
                break;
            }
        }

        return rvIsValid;
    }

    /**
     * Visszaadja a t�rolt {@link Book} p�ld�nyokat.
     *
     * @return A t�rolt {@link Book}-ek list�ja.
     */
    public List<Book> getBooks() {
        return books;
    }

    public boolean addPurchase(Purchase purchase){
        if (!customers.contains(purchase.getCustomer()) ||
            !books.contains(purchase.getBook())) {
            return false;
        }

        // l�ptetj�k az id-t az �j book-hoz (ezt az adatt�r kell biztos�tja)
        id++;

        // az adatt�rol� id-t oszt az objektumnak
        purchase.setId(id);

        boolean isStored = purchases.add(purchase);

        System.out.println("V�s�rl�sok:" + purchases.toString());

        return isStored;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

	

	@Override
	public boolean addStudent(Student s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Student> getStudents() {
		// TODO Auto-generated method stub
		return null;
	}

}
