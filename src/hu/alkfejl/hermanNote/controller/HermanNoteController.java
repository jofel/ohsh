package hu.alkfejl.hermanNote.controller;

import java.util.List;

import hu.alkfejl.hermanNote.model.HermanNoteDAO;
import hu.alkfejl.hermanNote.model.HermanNoteDAOOracle;
import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.HermanNoteGUI;

/**
 * Ez az osztály vezérli az egész programot, valamint a view és model csomagokat
 * köti össze. Itt található az üzleti logika (business logic) is.
 */
public class HermanNoteController {

    // Data Access Object - az adat elérését szolgáló objektum
    // FONTOS!!! A BookShopDAO az adatelérési réteg interfésze (absztraktciója)
    // a réteget mindig az interfészen keresztül érjük el.
    // A réteg implementációját egyszer használjuk, példányosításkor,
    // visszacastolni TILOS!!!
    private HermanNoteDAO dao = new HermanNoteDAOOracle();

    /**
     * Elindítja az alkalmazás desktopra specializált user interface-ét.
     */
    public void startDesktop() {
        HermanNoteGUI vc = new HermanNoteGUI(this);

        // GUI felület elindítása
        vc.startGUI();
    }
    public boolean addStudent(Student s) {
		// Controller, business logic-ra (üzleti logika, szabályok) példa
        // Szabály: valaki akkor hallgató ha 14-nél fiatalabb, valaki akkor
        // nyugdíjas ha 62-nel idõsebb
        
        return dao.addStudent(s);
	}
    
    public List<Student> getStudents() {
        // A customer listázásnál nincs üzleti szabály, ezért csak visszaadjuk a
        // model-tõl kapott listát.
        return dao.getStudents();
    }
    
    public boolean addCustomer(Customer c) {
        // Controller, business logic-ra (üzleti logika, szabályok) példa
        // Szabály: valaki akkor hallgató ha 14-nél fiatalabb, valaki akkor
        // nyugdíjas ha 62-nel idõsebb
        if (c.getAge() < 14) {
            c.setStudent(true);
        } else if(c.getAge() > 62) {
            c.setRented(true);
        }

        return dao.addCustomer(c);
    }

    public List<Customer> getCustomers() {
        // A customer listázásnál nincs üzleti szabály, ezért csak visszaadjuk a
        // model-tõl kapott listát.
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
