package hu.alkfejl.hermanNote.model;

import java.util.List;

import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.Student;

/**
 * Az interf�sz a BookShop app adatel�r�si reteg�t reprezent�lja.
 */
public interface HermanNoteDAO {

	
	/**
     * Hozz�ad egy {@link Student}-t az adatt�rhoz.
     *
     * @param user A t�roland� {@link Student}.
     * @return Igaz, ha sikeresen t�rolva, hamis, egy�bk�nt.
     */
	public boolean addStudent(Student s);
	
	 /**
     * Visszaadja a t�rolt {@link Student} p�ld�nyokat.
     *
     * @return A t�rolt {@link Student}-ek list�ja.
     */
    public List<Student> getStudents();
    
    /**
     * Visszaadja a t�rolt {@link Student} p�ld�nyokat.
     *
     * @return A t�rolt {@link Student}-ek list�ja.
     */
    public List<Student> searchStudent(Student s);

    /**
     * Hozz�ad egy {@link Customer}-t az adatt�rhoz.
     *
     * @param customer A t�roland� {@link Customer}.
     * @return Igaz, ha sikeresen t�rolva, hamis, egy�bk�nt.
     */
    public boolean addCustomer(Customer customer);

    /**
     * Visszaadja a t�rolt {@link Customer} p�ld�nyokat.
     *
     * @return A t�rolt {@link Customer}-ek list�ja.
     */
    public List<Customer> getCustomers();

    /**
     * Hozz�ad egy {@link Book}-ot az adatt�rhoz.
     *
     * @param book A t�roland� {@link Book}.
     * @return Igaz, ha sikeresen t�rolva, hamis, egy�bk�nt.
     */
    public boolean addBook(Book book);

    /**
     * Visszaadja a t�rolt {@link Book} p�ld�nyokat.
     *
     * @return A t�rolt {@link Book}-ek list�ja.
     */
    public List<Book> getBooks();

    /**
     * Hozz�ad egy {@link Purchase}-et az adatt�rhoz.
     *
     * @param purchase A t�roland� {@link Purchase}.
     * @return Igaz, ha sikeresen t�rolva, hamis, egy�bk�nt.
     */
    public boolean addPurchase(Purchase purchase);

    /**
     * Visszaadja a t�rolt {@link Purchase} p�ld�nyokat.
     *
     * @return A t�rolt {@link Purchase}-ek list�ja.
     */
    public List<Purchase> getPurchases();

	

	
}
