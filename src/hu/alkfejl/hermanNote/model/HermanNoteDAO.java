package hu.alkfejl.hermanNote.model;

import java.util.List;

import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.Student;

/**
 * Az interfész a BookShop app adatelérési retegét reprezentálja.
 */
public interface HermanNoteDAO {

	
	/**
     * Hozzáad egy {@link Student}-t az adattárhoz.
     *
     * @param user A tárolandó {@link Student}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
	public boolean addStudent(Student s);
	
	 /**
     * Visszaadja a tárolt {@link Student} példányokat.
     *
     * @return A tárolt {@link Student}-ek listája.
     */
    public List<Student> getStudents();
    
    /**
     * Visszaadja a tárolt {@link Student} példányokat.
     *
     * @return A tárolt {@link Student}-ek listája.
     */
    public List<Student> searchStudent(Student s);

    /**
     * Hozzáad egy {@link Customer}-t az adattárhoz.
     *
     * @param customer A tárolandó {@link Customer}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    public boolean addCustomer(Customer customer);

    /**
     * Visszaadja a tárolt {@link Customer} példányokat.
     *
     * @return A tárolt {@link Customer}-ek listája.
     */
    public List<Customer> getCustomers();

    /**
     * Hozzáad egy {@link Book}-ot az adattárhoz.
     *
     * @param book A tárolandó {@link Book}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    public boolean addBook(Book book);

    /**
     * Visszaadja a tárolt {@link Book} példányokat.
     *
     * @return A tárolt {@link Book}-ek listája.
     */
    public List<Book> getBooks();

    /**
     * Hozzáad egy {@link Purchase}-et az adattárhoz.
     *
     * @param purchase A tárolandó {@link Purchase}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    public boolean addPurchase(Purchase purchase);

    /**
     * Visszaadja a tárolt {@link Purchase} példányokat.
     *
     * @return A tárolt {@link Purchase}-ek listája.
     */
    public List<Purchase> getPurchases();

	

	
}
