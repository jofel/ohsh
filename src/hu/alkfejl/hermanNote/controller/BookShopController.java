package hu.alkfejl.hermanNote.controller;

import java.util.List;

import hu.alkfejl.hermanNote.model.BookShopDAO;
import hu.alkfejl.hermanNote.model.BookShopDAOOracle;
import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.User;
import hu.alkfejl.hermanNote.view.BookShopGUI;

/**
 * Ez az osztály vezérli az egész programot, valamint a view és model csomagokat
 * köti össze. Itt található az üzleti logika (business logic) is.
 */
public class BookShopController {

    // Data Access Object - az adat elérését szolgáló objektum
    // FONTOS!!! A BookShopDAO az adatelérési réteg interfésze (absztraktciója)
    // a réteget mindig az interfészen keresztül érjük el.
    // A réteg implementációját egyszer használjuk, példányosításkor,
    // visszacastolni TILOS!!!
    private BookShopDAO dao = new BookShopDAOOracle();

    /**
     * Elindítja az alkalmazás desktopra specializált user interface-ét.
     */
    public void startDesktop() {
        BookShopGUI vc = new BookShopGUI(this);

        // GUI felület elindítása
        vc.startGUI();
    }
    public boolean addUser(User u) {
		// Controller, business logic-ra (üzleti logika, szabályok) példa
        // Szabály: valaki akkor hallgató ha 14-nél fiatalabb, valaki akkor
        // nyugdíjas ha 62-nel idősebb
        
        return dao.addUser(u);
	}
    
    public List<User> getUsers() {
        // A customer listázásnál nincs üzleti szabály, ezért csak visszaadjuk a
        // model-től kapott listát.
        return dao.getUsers();
    }
    
    public boolean addCustomer(Customer c) {
        // Controller, business logic-ra (üzleti logika, szabályok) példa
        // Szabály: valaki akkor hallgató ha 14-nél fiatalabb, valaki akkor
        // nyugdíjas ha 62-nel idősebb
        if (c.getAge() < 14) {
            c.setStudent(true);
        } else if(c.getAge() > 62) {
            c.setRented(true);
        }

        return dao.addCustomer(c);
    }

    public List<Customer> getCustomers() {
        // A customer listázásnál nincs üzleti szabály, ezért csak visszaadjuk a
        // model-től kapott listát.
        return dao.getCustomers();
    }

    public boolean addBook(Book book) {
        if (book.getYear() < 1900 ) {
            book.setAncient(true);
        }

        return dao.addBook(book);
    }

    public List<Book> getBooks(){
        return dao.getBooks();
    }

    public boolean addPurchase(Purchase p){
        return dao.addPurchase(p);
    }

    public List<Purchase> getPurchases(){
        return dao.getPurchases();
    }

}
